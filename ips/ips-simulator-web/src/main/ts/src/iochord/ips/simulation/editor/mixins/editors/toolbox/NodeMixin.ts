// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphNodeImpl';
import { GraphNodeImpl } from '@/iochord/ips/common/graphs/ism/class/GraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';

// Interfaces
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';


// Vuex & Rxjs

/** Graph */
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';


// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/enums/NODE';
import * as NODE_ENUMS from '@/iochord/ips/common/graphs/ism/enums/NODE';
import * as NODE_FACTORY from '@/iochord/ips/common/graphs/ism/enums/NODE';
import { BRANCH_GATE, BRANCH_TYPE } from '@/iochord/ips/common/graphs/ism/enums/BRANCH';
import { GraphBranchNodeImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphBranchNodeImpl';

/**
 * Enumerations of types of the node.
 *
 * @enum {number}
 */
enum NODE {
  activity = 'activity',
  start = 'start',
  stop = 'stop',
  branch = 'branch',
  monitor = 'monitor',
}

const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

@Component<NodeMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})

/**
 * The node mixin class.
 *
 * @export
 * @class NodeMixin
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class NodeMixin extends BaseComponent {

  /**
   * The new node object.
   *
   * @type {JointGraphNodeImpl}
   * @memberof NodeMixin
   */
  public newNode?: JointGraphNodeImpl;

  /**
   * Create new node object.
   *
   * @param {NODE} type
   * @param {MouseEvent} e
   * @memberof NodeMixin
   */
  public createNode(type: NODE, e: MouseEvent) {

    /** Local variable initialization */
    const keys: any = {
      elementType: 'elementType',
    };

    /** Create new item */
    this.newNode = new JointGraphNodeImpl();

    /** Set properties for the newly created item */
    this.newNode.setId(`0-${type}-${GraphNodeImpl.instance.size()}`);
    this.newNode.setType(type.toString());
    this.newNode.setSize((NODE_TYPE as any)[type].size);
    this.newNode.setMarkup((NODE_TYPE as any)[type].markup);
    this.newNode.setAttr((NODE_TYPE as any)[type].attr);
    this.newNode.setImageIcon((NODE_TYPE as any)[type].image);

    if (this.newNode.getType() === 'branch') {
      this.newNode.setImageIcon(NODE_TYPE.xor_split.image);
    }

    /** No need to set label for start and stop node */
    if (!(type.toString() === 'start' || type.toString() === 'stop')) {
      (this.newNode as JointGraphNodeImpl).setLabel(`New Node ${GraphNodeImpl.instance.size()}`);
    }

    /** Put new item in vuex store */
    graphModule.setNewItem(this.newNode as JointGraphNodeImpl);

    /** Set dragging state to true */
    editorState.setDragging(true);
  }

  /**
   * Draw the node to canvas.
   *
   * @param {MouseEvent} e
   * @param {JointGraphPageImpl} activePage
   * @memberof NodeMixin
   */
  public moveNode(e: MouseEvent, activePage: JointGraphPageImpl) {
    if (editorState.dragging && graphModule.newItem !== null) {

      /** Capture svgPoint from MouseEvent */
      const svgPoint = (activePage.getPaper().svg as SVGSVGElement).createSVGPoint();
      svgPoint.x = e.offsetX;
      svgPoint.y = e.offsetY;

      /** Transform svgPoint to joint.js paper matrix */
      const pointTransformed = svgPoint.matrixTransform(activePage.getPaper().viewport.getCTM()!.inverse());

      /** Set position according to the transformed point captured from MouseEvent */
      (graphModule.newItem as JointGraphNodeImpl).setPosition({
        x: pointTransformed.x,
        y: pointTransformed.y,
      });

      /** Render newItem */
      (graphModule.newItem as JointGraphNodeImpl).render(activePage.getGraph());

      /** Listen to keydown event to check esc button */
      window.addEventListener('keydown', this.cancelCreateNode);
    }
  }

  /**
   * Cancel the creation of node object.
   *
   * @param {KeyboardEvent} e
   * @memberof NodeMixin
   */
  public cancelCreateNode(e: KeyboardEvent) {
    if (e.key === 'Escape') {
      if (editorState.dragging && (graphModule.newItem)) {

        // Set dragging state to false
        editorState.setDragging(false);

        // Remove node from joint.js canvas
        (graphModule.newItem as JointGraphNodeImpl).getNode().remove();

        // Remove node from GraphModule temporary container
        graphModule.setNewItem(null);

        // Enable the toolbar again
        $('.sidebar.component .ui.basic.button.item').removeClass('disabled');

        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'info',
          className: {
            toast: 'ui message',
          },
          message: `Canceling node creation.`,
          newestOnTop: true,
        });

        // Remove event listener for cancelCreateItem
        window.removeEventListener('keydown', this.cancelCreateNode);
      }
    }
  }

  /**
   * Delete node object from the canvas.
   *
   * @param {JointGraphPageImpl} activePage
   * @param {joint.dia.Element} cell
   * @memberof NodeMixin
   */
  public deleteNode(activePage: JointGraphPageImpl, cell: joint.dia.Element) {

    // Check if cell is a node object
    if (cell.isElement()) {
      const nodeId = cell.attributes.nodeId;
      const node = graphModule.pageNode(activePage as JointGraphPageImpl, nodeId) as GraphNode;

      // If Node exists
      if (node) {
        graphModule.deletePageNode({
          page: activePage as JointGraphPageImpl,
          node,
        });

        // Update local instance
        GraphNodeImpl.instance.delete(nodeId as string);

        // Update the rxjs observable
        GraphSubject.update(graphModule.graph);

        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'info',
          className: {
            toast: 'ui message',
          },
          message: `Successfully remove a node`,
          newestOnTop: true,
        });
      }
    }
  }

  /**
   * Draw node object to screen, then store and commit the new node object to vuex store.
   *
   * @param {MouseEvent} e
   * @param {JointGraphPageImpl} activePage
   * @memberof NodeMixin
   */
  public saveNode(e: MouseEvent, activePage: JointGraphPageImpl) {
    editorState.setDragging(false);
    if (graphModule.newItem !== null) {

      /** Render newItem */
      (graphModule.newItem as JointGraphNodeImpl).render(activePage.getGraph());

      /** Construct newItem according to its type */
      const type = graphModule.newItem.getType() as string;
      const newItem = new (NODE_FACTORY.NODE_TYPE as any)[type]();
      newItem.setId(`0-${type}-${GraphNodeImpl.instance.size()}`);
      newItem.setType(type);

      if (type === 'activity') {
        newItem.setImageIcon((NODE_TYPE as any)[type].image);
      }

      /** No need to set label for start and stop node */
      if (!(type.toString() === 'start' || type.toString() === 'stop')) {
        (newItem).setLabel(`New Node ${GraphNodeImpl.instance.size()}`);
      }

      // Add node to Vuex GraphModule
      graphModule.addPageNode(
        {
          page: activePage,
          node: newItem as GraphNode,
        },
      );

      // Update local instance
      GraphNodeImpl.instance.set(newItem.getId() as string, (NODE_ENUMS.NODE_TYPE as any)[type].deserialize(newItem));

      // Update the rxjs observable
      GraphSubject.update(graphModule.graph);

      // Set container to null
      graphModule.setNewItem(null);
      this.newNode = undefined;

      // Pop up toast
      const icon = {
        start: 'green play',
        stop: 'red circle',
        activity: 'blue square',
        branch: 'blue random',
      };

      ($('body') as any).toast({
        position: 'bottom right',
        class: 'success',
        className: {
          toast: 'ui message',
        },
        showIcon: (icon as any)[type],
        message: `${(newItem as GraphNode).getId()} has been created.`,
        newestOnTop: true,
      });

      // Enable the toolbar again
      $('.sidebar.component .ui.basic.button.item').removeClass('disabled');

      // Remove event listener for cancelCreateItem
      window.removeEventListener('keydown', this.cancelCreateNode);
    }
  }
}
