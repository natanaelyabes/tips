import { VuexModule, Module, Mutation, MutationAction } from 'vuex-module-decorators';
import ResourceMiningResult from '../../models/ResourceMiningResult';
import Vuex from 'vuex';

interface StoreType {
  ResourceMiningResultModule: ResourceMiningResultModule;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, namespaced: true, name: 'ResourceMiningResultModule' })

class ResourceMiningResultModule extends VuexModule {
  public resminresult: ResourceMiningResult = {} as ResourceMiningResult;

  @Mutation
  public setResminresult(newResminresult: ResourceMiningResult): void {
    this.resminresult = newResminresult;
  }

  @MutationAction({ mutate: ['resminresult'] })
  public async updateResminresult(newResminresult: ResourceMiningResult) {
    return { resminresult: newResminresult };
  }
}
export default ResourceMiningResultModule;

