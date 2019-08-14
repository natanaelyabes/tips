// Vue & Libraries
import { Component, Mixins } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Components
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

// Interfaces
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';

// Classes
import { JointGraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphConnectorImpl';

// Mixins
import PaletteMixin from './PaletteMixin';

// Vuex
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import EditorState from '@/iochord/chdsr/simulation/editor/stores/editors/EditorState';


// Vuex module
const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

@Component
export default class CanvasMixin extends Mixins(BaseComponent, PaletteMixin) {

  // To store graph
  public graph?: Graph;

  // To determine current active page
  public activePage?: JointGraphPageImpl | GraphPage;

  // Container to put current selected element
  public currentSelectedElement?: GraphNode;

  // Types of node identified within loaded graph
  public nodeTypes: Set<string> = new Set<string>();

  public handleCanvasMouseDown(e: MouseEvent) {
    if (!editorState.dragging && !editorState.drawing) {
      editorState.setDragging(true);
    }
  }

  public handleCanvasMouseMove(e: MouseEvent) {
    if (editorState.dragging && !editorState.drawing && this.activePage) {
      this.moveNode(e, this.activePage as JointGraphPageImpl);
    }

    if (editorState.drawing && this.activePage) {
      if (graphModule.newItem !== null && this.source) {
        // TODO: begin from here
        // (graphModule.newItem as JointGraphConnectorImpl).setTarget();
        // (graphModule.newItem as JointGraphConnectorImpl).render((this.activePage as JointGraphPageImpl).getGraph());
      }
    }
  }

  public handleCanvasMouseUp(e: MouseEvent) {
    if (editorState.dragging && !editorState.drawing && this.activePage) {
      this.saveNode(e, this.activePage as JointGraphPageImpl);
      editorState.setDragging(false);
    }
  }

  // TODO: Probably useless
  public handleEscapeButton(e: KeyboardEvent) {
    if (editorState.dragging && !editorState.drawing) {
      this.cancelCreateNode(e);
    }

    if (editorState.drawing && !editorState.dragging) {
      this.cancelCreateConnector(e);
    }
  }
}
