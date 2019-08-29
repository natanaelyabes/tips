import Vuex from 'vuex';
import { VuexModule, Module, MutationAction, Mutation } from 'vuex-module-decorators';

interface StoreType {
  editorState: EditorState;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, name: 'EditorState', namespaced: true })
export default class EditorState extends VuexModule {
  public dragging: boolean = false;
  public drawing: boolean = false;

  @Mutation
  public setDragging(dragging: boolean) {
    this.dragging = dragging;
  }

  @Mutation
  public setDrawing(drawing: boolean) {
    this.drawing = drawing;
  }

  public get isDragging(): boolean {
    return this.dragging;
  }

  public get isDrawing(): boolean {
    return this.drawing;
  }
}
