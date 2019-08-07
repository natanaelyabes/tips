import Vuex from 'vuex';
import { VuexModule, Module, MutationAction, Mutation } from 'vuex-module-decorators';

interface StoreType {
  editorState: EditorState;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, name: 'EditorState', namespaced: true })
export default class EditorState extends VuexModule {
  public dragging: boolean = false;

  @Mutation
  public setDragging(dragging: boolean) {
    this.dragging = dragging;
  }
}
