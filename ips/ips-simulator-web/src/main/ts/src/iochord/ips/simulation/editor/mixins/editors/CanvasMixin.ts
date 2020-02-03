// Vue & Libraries
import { Component, Mixins } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Components
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

// Interfaces
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';

// Classes
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';

// Mixins
import PaletteMixin from './PaletteMixin';

// Vuex
import EditorState from '../../stores/editors/EditorState';


// Vuex module
const editorState = getModule(EditorState);

@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class CanvasMixin extends Mixins(BaseComponent, PaletteMixin) {

  // To store graph
  public graph?: Graph;

  // To determine current active page
  public activePage?: JointGraphPageImpl | GraphPage;

  // Container to put current selected element
  public currentSelectedElement?: GraphNode;

  // Types of node identified within loaded graph
  public nodeTypes: Set<string> = new Set<string>();
  public dataTypes: Set<string> = new Set<string>();

  public handleCanvasMouseDown(e: MouseEvent) {
    if (!editorState.dragging && !editorState.drawing) {
      editorState.setDragging(true);
    }
  }

  public handleCanvasMouseMove(e: MouseEvent) {
    if (editorState.dragging && !editorState.drawing && this.activePage) {
      if (editorState.drawingMode === 'node') {
        this.moveNode(e, this.activePage as JointGraphPageImpl);
      }

      if (editorState.drawingMode === 'data') {
        this.moveData(e, this.activePage as JointGraphPageImpl);
      }
    }

    if (!editorState.dragging && editorState.drawing && this.activePage && this.source) {
      this.moveToTargetNode(e, this.activePage as JointGraphPageImpl);
    }
  }

  public handleCanvasMouseUp(e: MouseEvent) {
    if (editorState.dragging && !editorState.drawing && this.activePage) {
      if (editorState.drawingMode === 'node') {
        this.saveNode(e, this.activePage as JointGraphPageImpl);
      }

      if (editorState.drawingMode === 'data') {
        this.saveData(e, this.activePage as JointGraphPageImpl);
      }

      editorState.setDragging(false);
    }
  }

  // TODO: Probably useless
  public handleEscapeButton(e: KeyboardEvent) {
    if (editorState.dragging && !editorState.drawing) {
      if (editorState.drawingMode === 'node') {
        this.cancelCreateNode(e);
      }

      if (editorState.drawingMode === 'data') {
        this.cancelCreateData(e);
      }
    }

    if (editorState.drawing && !editorState.dragging) {
      this.cancelCreateConnector(e);
    }
  }
}
