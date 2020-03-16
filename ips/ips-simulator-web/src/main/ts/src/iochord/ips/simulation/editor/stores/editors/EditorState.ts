import Vuex from 'vuex';
import { VuexModule, Module, MutationAction, Mutation } from 'vuex-module-decorators';

interface StoreType {
  editorState: EditorState;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, name: 'EditorState', namespaced: true })

/**
 * The editor state module.
 *
 * @export
 * @class EditorState
 * @extends {VuexModule}
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class EditorState extends VuexModule {

  /**
   * Indicates whether an editor is being in the dragging state.
   *
   * @State
   * @type {boolean}
   * @memberof EditorState
   */
  public dragging: boolean = false;

  /**
   * Indicates whether an editor is being in the drawing state.
   *
   * @State
   * @type {boolean}
   * @memberof EditorState
   */
  public drawing: boolean = false;

  /**
   * Current active mode for the editor.
   *
   * @State
   * @type {('node' | 'data' | 'connector')}
   * @memberof EditorState
   */
  public mode: 'node' | 'data' | 'connector' = 'node';

  /**
   * Assigns state as dragging mode.
   *
   * @Mutation
   * @param {boolean} dragging
   * @memberof EditorState
   */
  @Mutation
  public setDragging(dragging: boolean) {
    this.dragging = dragging;
  }

  /**
   * Assigns state as drawing mode.
   *
   * @Mutation
   * @param {boolean} drawing
   * @memberof EditorState
   */
  @Mutation
  public setDrawing(drawing: boolean) {
    this.drawing = drawing;
  }

  /**
   * Assigns the mode of the editor.
   *
   * @Mutation
   * @param {('node' | 'data' | 'connector')} mode
   * @memberof EditorState
   */
  @Mutation
  public setMode(mode: 'node' | 'data' | 'connector') {
    this.mode = mode;
  }

  /**
   * Returns the dragging state.
   *
   * @Getter
   * @readonly
   * @type {boolean}
   * @memberof EditorState
   */
  public get isDragging(): boolean {
    return this.dragging;
  }

  /**
   * Returns the drawing state.
   *
   * @Getter
   * @readonly
   * @type {boolean}
   * @memberof EditorState
   */
  public get isDrawing(): boolean {
    return this.drawing;
  }

  /**
   * Returns the drawing mode state.
   *
   * @Getter
   * @readonly
   * @type {('node' | 'data' | 'connector')}
   * @memberof EditorState
   */
  public get drawingMode(): 'node' | 'data' | 'connector' {
    return this.mode;
  }
}
