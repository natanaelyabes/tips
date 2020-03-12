import { VuexModule, Module, Mutation, MutationAction } from 'vuex-module-decorators';
import ResourceMiningResult from '../../models/ResourceMiningResult';
import Vuex from 'vuex';

interface StoreType {
  resmResult: ResourceMiningResultModule;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, namespaced: true, name: 'ResourceMiningResultModule' })

class ResourceMiningResultModule extends VuexModule {
  public mapResmResult: any = {};

  @Mutation
  public addResminresult(newResmResult: any): void {
    const mergedObj = { ...this.mapResmResult, ...newResmResult };
    this.mapResmResult = mergedObj;
  }

  @MutationAction({ mutate: ['mapResmResult'] })
  public async updateResminresult(newResmResult: any) {
    const mergedObj = { ...this.mapResmResult, ...newResmResult };
    return { mapResmResult: mergedObj };
  }
}
export default ResourceMiningResultModule;

