// Vue & Libraries
import { Component, Mixins } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Components
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

// Interfaces
import { Graph } from '@/iochord/ips/common/graphs/ism/interfaces/Graph';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';

// Classes
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';

// Mixins
import PaletteMixin from './PaletteMixin';

// Vuex
import EditorState from '../../stores/editors/EditorState';


// Vuex module
const editorState = getModule(EditorState);

@Component

/**
 * The canvas mixin object.
 *
 * @export
 * @class CanvasMixin
 * @extends {Mixins(BaseComponent, PaletteMixin)}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class CanvasMixin extends Mixins(BaseComponent, PaletteMixin) {

  /**
   * The graph object.
   *
   * @type {Graph}
   * @memberof CanvasMixin
   */
  public graph?: Graph;

  /**
   * The active page object.
   *
   * @type {(JointGraphPageImpl | GraphPage)}
   * @memberof CanvasMixin
   */
  public activePage?: JointGraphPageImpl | GraphPage;

  /**
   * Current selected element object.
   *
   * @type {GraphNode}
   * @memberof CanvasMixin
   */
  public currentSelectedElement?: GraphNode;

  /**
   * The types of node available during the load time.
   *
   * @type {Set<string>}
   * @memberof CanvasMixin
   */
  public nodeTypes: Set<string> = new Set<string>();

  /**
   * The types of data available during the load time.
   *
   * @type {Set<string>}
   * @memberof CanvasMixin
   */
  public dataTypes: Set<string> = new Set<string>();

  /**
   * The canvas mouse down event handler.
   *
   * @param {MouseEvent} e
   * @memberof CanvasMixin
   */
  public handleCanvasMouseDown(e: MouseEvent) {
    if (!editorState.dragging && !editorState.drawing) {
      editorState.setDragging(true);
    }
  }

  /**
   * The canvas mouse move event handler.
   *
   * @param {MouseEvent} e
   * @memberof CanvasMixin
   */
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

  /**
   * The canvas mouse up event handler.
   *
   * @param {MouseEvent} e
   * @memberof CanvasMixin
   */
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

  /**
   * The canvas escape button handler.
   *
   * @param {KeyboardEvent} e
   * @memberof CanvasMixin
   */
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
