// Vue & Libraries
import { Component, Mixins } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Components
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

// Interfaces
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';

// Vuex
import EditorState from '@/iochord/chdsr/simulation/editor/stores/editors/EditorState';
import PaletteMixin from './PaletteMixin';
import { JointGraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphPageImpl';

// Vuex module
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
    if (!editorState.dragging) {
      editorState.setDragging(true);
    }
  }

  public handleCanvasMouseMove(e: MouseEvent) {
    if (editorState.dragging && this.activePage) {
      this.moveNode(e, this.activePage as JointGraphPageImpl);
    }
  }

  public handleCanvasMouseUp(e: MouseEvent) {
    if (editorState.dragging && this.activePage) {
      this.saveNode(e, this.activePage as JointGraphPageImpl);
      editorState.setDragging(false);
    }
  }
}
