import { DATA_TYPE, DATA_TYPE_ENUM } from './../enums/DATA';
import { GraphConnector } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphConnector';
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
  public async fetchGraph(url?: string) {
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

  public get page(): (pageId: string) => GraphPage | null {
    const pages = this.graph ? this.graph.getPages() as Map<string, GraphPage> : null;
    return (pageId: string) => pages !== null ? pages.get(pageId) as GraphPage : null;
  }

  public get pageArcs(): (page: GraphPage) => Map<string, GraphConnector> | null {
    return (page: GraphPage) => {
      const arcs = page.getArcs();
      return arcs !== null ? arcs : null;
    };
  }

  public get pageArc(): (page: GraphPage, arcId: string) => GraphConnector | null {
    return (page: GraphPage, arcId: string) => {
      const arcs = this.pageArcs(page) !== null ? this.pageArcs(page) : null;
      const arc = (arcs as Map<string, GraphConnector>).get(`${page.getId()}-${arcId}`);
      return arc !== null || arc !== undefined ? arc as GraphConnector : null;
    };
  }

  public get pageData(): (page: GraphPage, elementType?: string) => Map<string, GraphData> | null {
    return (page: GraphPage, elementType?: string) => {
      let result: Map<string, GraphData> | null = new Map<string, GraphData>();
      const data = page.getData() ? page.getData() : null;

      if (elementType) {
        const keys = data ? data.keys() : null;
        let res = keys ? keys.next() : null;

        while (res && !res.done) {
          const elType = res.value.split('-')[1];
          const dId = res.value.split('-')[2];

          if (elType === elementType) {
            result.set(dId, data!.get(dId) as GraphData);
          }

          res = keys ? keys.next() : null;
        }

      } else {
        result = data !== null ? data : null;
      }
      return result;
    };
  }

  public get pageDatum(): (page: GraphPage, elementType: string, datumId: string) => GraphData | null {
    return (page: GraphPage, elementType: string, datumId: string) => {
      const data = this.pageData(page) !== null ? this.pageData(page) : null;
      const keys = (data as Map<string, GraphData>).keys();

      const findKeyId: (keys: IterableIterator<string>) => string[] | undefined = () => {
        let result = keys.next();
        while (!result.done) {
          const elType = result.value.split('-')[1];
          const dId = result.value.split('-')[2];

          if (dId === datumId) {
            return [elType, dId];
          }

          result = keys.next();
        }
      };

      const keyId = findKeyId(keys);

      const datum = (data as Map<string, GraphData>).get(`${page.getId()}-${keyId![0]}-${keyId![1]}`);
      return datum !== null || datum !== undefined ? datum as GraphData : null;
    };
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
