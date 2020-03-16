import Vuex from 'vuex';
import { VuexModule, Module, MutationAction, Mutation } from 'vuex-module-decorators';
import MappingResource from '../models/MappingResource';
import MappingService from '../services/MappingService';
import IMappingResource from '../interfaces/IMappingResource';

interface StoreType {
  mappingModule: MappingModule;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, name: 'MappingModule', namespaced: true })
export default class MappingModule extends VuexModule {
  public mapResource: IMappingResource = {} as IMappingResource;

  @MutationAction({ mutate: ['mapResource'] })
  public async retreiveMapResource(datasetId: string) {
    const mapResource: IMappingResource = await MappingService.getInstance().getMappingSettings(datasetId);
    return { mapResource };
  }

  @Mutation
  public setMapResource(mapResource: IMappingResource): void {
    this.mapResource = mapResource;
  }
}
