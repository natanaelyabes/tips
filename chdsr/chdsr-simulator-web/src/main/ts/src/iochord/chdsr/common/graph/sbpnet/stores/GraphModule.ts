import { GraphData } from './../interfaces/GraphData';
import { GraphConfiguration } from './../interfaces/GraphConfiguration';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { VuexModule, Module, MutationAction } from 'vuex-module-decorators';
import { Graph } from '../interfaces/Graph';
import { GraphControl } from '../interfaces/components/GraphControl';
import { SbpnetModelService } from '../../../service/model/SbpnetModelService';

import Vuex from 'vuex';

interface StoreType {
  graphModule: GraphModule;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, name: 'GraphModule', namespaced: true })
export default class GraphModule extends VuexModule {
  public graph: Graph = {} as Graph;

  @MutationAction({ mutate: ['graph'] })
  public async fetchGraph() {
    const graph: Graph = await SbpnetModelService.getInstance().getExampleModel();
    return { graph };
  }

  public get version(): string | null {
    return this.graph ? this.graph.getVersion() : null;
  }

  public get pages(): Map<string, GraphPage> | null {
    return this.graph ? this.graph.getPages() : null;
  }

  public get defaultPage(): GraphPage | null {
    return this.graph ? (this.graph.getPages() as Map<string, GraphPage>).get('0') as GraphPage : null;
  }

  public get configurations(): Map<string, GraphConfiguration> | null {
    return this.graph ? this.graph.getConfigurations() : null;
  }

  public get control(): GraphControl | null {
    return this.graph ? this.graph.getControl() : null;
  }

  public get data(): Map<string, GraphData> | null {
    return this.graph ? this.graph.getData() : null;
  }
}
