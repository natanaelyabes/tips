// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphNodeImpl';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';

// Interfaces
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';


// Vuex & Rxjs

/** Graph */
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';


// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/NODE';
import * as NODE_ENUMS from '@/iochord/ips/common/graph/ism/enums/NODE';
import * as NODE_FACTORY from '@/iochord/ips/common/graph/ism/enums/NODE';


// Enums of NODE
enum NODE {
  activity,
  start,
  stop,
  branch,
  monitor,
}

// Fetch module from stores
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
export default class NodeMixin extends BaseComponent {

  public newNode?: JointGraphNodeImpl;

  public createNode(type: NODE, e: MouseEvent) {

    /** Local variable initialization */
    const keys: any = {
      elementType: 'elementType',
    };

    /** Create new item */
    this.newNode = new JointGraphNodeImpl();

    /** Set properties for the newly created item */
    this.newNode.setId(`0-${type}-${GraphNodeImpl.instance.size}`);
    this.newNode.setType(type.toString());
    this.newNode.setSize((NODE_TYPE as any)[type].size);
    this.newNode.setMarkup((NODE_TYPE as any)[type].markup);
    this.newNode.setAttr((NODE_TYPE as any)[type].attr);
    this.newNode.setImageIcon((NODE_TYPE as any)[type].image);

    /** No need to set label for start and stop node */
    if (!(type.toString() === 'start' || type.toString() === 'stop')) {
      (this.newNode as JointGraphNodeImpl).setLabel(`New Node ${GraphNodeImpl.instance.size}`);
    }

    /** Put new item in vuex store */
    graphModule.setNewItem(this.newNode as JointGraphNodeImpl);

    /** Set dragging state to true */
    editorState.setDragging(true);
  }

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


  public saveNode(e: MouseEvent, activePage: JointGraphPageImpl) {
    editorState.setDragging(false);
    if (graphModule.newItem !== null) {

      /** Render newItem */
      (graphModule.newItem as JointGraphNodeImpl).render(activePage.getGraph());

      /** Construct newItem according to its type */
      const type = graphModule.newItem.getType() as string;
      const newItem = new (NODE_FACTORY.NODE_TYPE as any)[type]();
      newItem.setId(`0-${type}-${GraphNodeImpl.instance.size}`);
      newItem.setType(type);

      /** No need to set label for start and stop node */
      if (!(type.toString() === 'start' || type.toString() === 'stop')) {
        (newItem).setLabel(`New Node ${GraphNodeImpl.instance.size}`);
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
