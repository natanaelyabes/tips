// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Classes
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { JointGraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphNodeImpl';
import { GraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphPageImpl';

// Interfaces
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';


// Vuex & Rxjs

/** Graph */
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';


// Enums
import { NODE_TYPE } from '@/iochord/chdsr/common/graph/sbpnet/enums/NODE';
import * as NODE_ENUMS from '@/iochord/chdsr/common/graph/sbpnet/enums/NODE';
import * as NODE_FACTORY from '@/iochord/chdsr/common/graph/sbpnet/enums/NODE';


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
  public createNode(type: NODE, e: MouseEvent) {

    /** Local variable initialization */
    const keys: any = {
      elementType: 'elementType',
    };

    /** Create new item */
    const newItem = new JointGraphNodeImpl();

    /** Set properties for the newly created item */
    newItem.setId(`0-${type}-${GraphNodeImpl.instance.size}`);
    newItem.setType(type.toString());
    newItem.setSize((NODE_TYPE as any)[type].size);
    newItem.setMarkup((NODE_TYPE as any)[type].markup);
    newItem.setAttr((NODE_TYPE as any)[type].attr);
    newItem.setImageIcon((NODE_TYPE as any)[type].image);

    /** No need to set label for start and stop node */
    if (!(type.toString() === 'start' || type.toString() === 'stop')) {
      (newItem as JointGraphNodeImpl).setLabel(`New Node ${GraphNodeImpl.instance.size}`);
    }

    /** Put new item in vuex store */
    graphModule.setNewItem(newItem as JointGraphNodeImpl);

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
        editorState.setDragging(false);
        (graphModule.newItem as JointGraphNodeImpl).getNode().remove();
        graphModule.setNewItem(null);
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

      graphModule.addPageNode(
        {
          page: activePage,
          node: newItem as GraphNode,
        },
      );
      GraphNodeImpl.instance.set(newItem.getId() as string, (NODE_ENUMS.NODE_TYPE as any)[type].deserialize(newItem));

      // Update the rxjs observable
      GraphSubject.update(graphModule.graph);

      // Set container to null
      graphModule.setNewItem(null);

      const icon = {
        start: 'green play',
        stop: 'red circle',
        activity: 'blue square',
        branch: 'blue random',
      };

      ($('body') as any).toast({
        position: 'top center',
        class: 'black',
        showIcon: (icon as any)[type],
        message: `${(newItem as GraphNode).getId()} has been created.`,
      });

      // Remove event listener for cancelCreateItem
      window.removeEventListener('keydown', this.cancelCreateNode);
    }
  }
}
