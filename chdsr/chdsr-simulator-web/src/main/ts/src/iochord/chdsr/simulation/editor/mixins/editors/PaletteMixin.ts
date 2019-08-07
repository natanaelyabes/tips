// Vue & libraries
import { Component, Mixins } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Classes
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { JointGraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphNodeImpl';

// Vuex modules
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import EditorState from '../../stores/editors/EditorState';
import { GraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphNodeImpl';

// Enums of NODE
enum NODE {
  activity,
  start,
  stop,
  branch,
  monitor,
}

import { NODE_TYPE } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/enums/NODE';
import { JointGraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphPageImpl';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';
import * as NODE_ENUMS from '@/iochord/chdsr/common/graph/sbpnet/enums/NODE';


const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

@Component<PaletteMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class PaletteMixin extends BaseComponent {

  public createItem(type: NODE, e: MouseEvent) {

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

  public moveItem(e: MouseEvent, activePage: JointGraphPageImpl) {
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
      window.addEventListener('keydown', this.cancelCreateItem);
    }
  }

  public cancelCreateItem(e: KeyboardEvent) {
    if (e.key === 'Escape') {
      if (editorState.dragging && (graphModule.newItem)) {
        editorState.setDragging(false);
        (graphModule.newItem as JointGraphNodeImpl).getNode().remove();
        graphModule.setNewItem(null);
      }
    }
  }

  public saveItem(e: MouseEvent, activePage: JointGraphPageImpl) {
    editorState.setDragging(false);
    if (graphModule.newItem !== null) {
      graphModule.addPageNode(
        {
          page: activePage,
          node: graphModule.newItem as GraphNode,
        },
      );

      GraphSubject.update(graphModule.graph);
      GraphNodeImpl.instance.set(graphModule.newItem!.getId() as string, (NODE_ENUMS.NODE_TYPE as any)[graphModule.newItem!.getType() as string].deserialize(graphModule.newItem));
      graphModule.setNewItem(null);

      window.removeEventListener('keydown', this.cancelCreateItem);
    }
  }
}
