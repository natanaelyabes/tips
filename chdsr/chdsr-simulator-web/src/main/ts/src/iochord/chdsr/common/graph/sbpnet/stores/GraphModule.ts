import { DATA_TYPE, DATA_TYPE_ENUM } from './../enums/DATA';
import { GraphConnector } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphConnector';
import { GraphData } from './../interfaces/GraphData';
import { GraphConfiguration } from './../interfaces/GraphConfiguration';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { VuexModule, Module, MutationAction, Mutation, Action } from 'vuex-module-decorators';
import { Graph } from '../interfaces/Graph';
import { GraphControl } from '../interfaces/components/GraphControl';
import { SbpnetModelService } from '../../../service/model/SbpnetModelService';

import Vuex from 'vuex';
import { GraphNode } from '../interfaces/GraphNode';

interface StoreType {
  graphModule: GraphModule;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, name: 'GraphModule', namespaced: true })
export default class GraphModule extends VuexModule {
  public graph: Graph = {} as Graph;

  // Mutations
  @MutationAction({ mutate: ['graph'] })
  public async fetchGraph(url?: string) {
    const graph: Graph = await SbpnetModelService.getInstance().getExampleModel();
    return { graph };
  }

  @Mutation
  public setPages(pages: Map<string, GraphPage>): void {
    this.graph.setPages(pages);
  }

  @Mutation
  public setDefaultPage(page: GraphPage): void {
    this.graph.setDefaultPage(page);
  }

  @Mutation
  public addPage(page: GraphPage): void {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        throw new Error(`Page ${page.getId()} had existed in Graph ${this.graph.getId()}`);
      }
      pages.set(page.getId() as string, page);
    }
  }

  @Mutation
  public overridePage(page: GraphPage): void {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        pages.set(page.getId() as string, page);
      } else {
        this.addPage(page);
      }
    }
  }

  @Mutation
  public deletePage(page: GraphPage): void {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        pages.delete(page.getId() as string);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public setPageArcs(page: GraphPage, arcs: Map<string, GraphConnector>): void {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        exists.setArcs(arcs);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public addPageArc(page: GraphPage, arc: GraphConnector) {
    const arcs = page.getArcs();

    if (arcs !== null) {
      const exists = arcs.get(arc.getId() as string);
      if (exists) {
        throw new Error(`Arc ${arc.getId()} had existed in Page ${page.getId()}`);
      }
      arcs.set(arc.getId() as string, arc);
    }
  }

  @Mutation
  public overridePageArc(page: GraphPage, arc: GraphConnector) {
    const arcs = page.getArcs();

    if (arcs !== null) {
      const exists = arcs.get(arc.getId() as string);
      if (exists) {
        arcs.set(arc.getId() as string, arc);
      } else {
        this.addPageArc(page, arc);
      }
    }
  }

  @Mutation
  public deletePageArc(page: GraphPage, arc: GraphConnector) {
    const arcs = page.getArcs();

    if (arcs !== null) {
      const exists = arcs.get(arc.getId() as string);
      if (exists) {
        arcs.delete(arc.getId() as string);
      } else {
        throw new Error(`Arc ${arc.getId()} had existed in Page ${page.getId()}`);
      }
    }
  }

  @Mutation
  public setPageData(page: GraphPage, data: Map<string, GraphData>): void {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        exists.setData(data);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public addPageDatum(page: GraphPage, datum: GraphData): void {
    const data = page.getData();

    if (data !== null) {
      const exists = data.get(datum.getId() as string);
      if (exists) {
        throw new Error(`Datum ${datum.getId()} had existed in Page ${page.getId()}`);
      }
      data.set(datum.getId() as string, datum);
    }
  }

  @Mutation
  public overridePageDatum(page: GraphPage, datum: GraphData) {
    const data = page.getData();

    if (data !== null) {
      const exists = data.get(datum.getId() as string);
      if (exists) {
        data.set(datum.getId() as string, datum);
      } else {
        this.addPageDatum(page, datum);
      }
    }
  }

  @Mutation
  public deletePageDatum(page: GraphPage, datum: GraphData) {
    const data = page.getData();

    if (data !== null) {
      const exists = data.get(datum.getId() as string);
      if (exists) {
        data.delete(datum.getId() as string);
      } else {
        throw new Error(`Datum ${datum.getId()} had existed in Page ${page.getId()}`);
      }
    }
  }

  @Mutation
  public setPageElementType(page: GraphPage, elementType: string) {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        exists.setType(elementType);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public setPageId(page: GraphPage, id: string) {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        exists.setId(id);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public setPageLabel(page: GraphPage, label: string) {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        exists.setLabel(label);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public setPageNodes(page: GraphPage, nodes: Map<string, GraphNode>) {
    const pages = this.graph.getPages();

    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        exists.setNodes(nodes);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public addPageNode({page, node}: {page: GraphPage, node: GraphNode}): void {
    const nodes = page.getNodes();

    if (nodes !== null) {
      const exists = nodes.get(node.getId() as string);
      if (exists) {
        throw new Error(`Node ${node.getId()} had existed in Page ${page.getId()}`);
      }
      nodes.set(node.getId() as string, node);
    }
  }

  @Mutation
  public overridePageNode(page: GraphPage, node: GraphNode) {
    const nodes = page.getNodes();

    if (nodes !== null) {
      const exists = nodes.get(node.getId() as string);
      if (exists) {
        nodes.set(node.getId() as string, node);
      } else {
        this.addPageNode({page, node});
      }
    }
  }

  @Mutation
  public deletePageNode(page: GraphPage, node: GraphNode) {
    const nodes = page.getNodes();

    if (nodes !== null) {
      const exists = nodes.get(node.getId() as string);
      if (exists) {
        nodes.delete(node.getId() as string);
      } else {
        throw new Error(`Datum ${node.getId()} had existed in Page ${page.getId()}`);
      }
    }
  }

  @Mutation
  public setConfigurations(configurations: Map<string, GraphConfiguration>): void {
    this.graph.setConfigurations(configurations);
  }

  @Mutation
  public setControl(control: GraphControl): void {
    this.graph.setControl(control);
  }

  @Mutation
  public setData(data: Map<string, GraphData>): void {
    this.graph.setData(data);
  }

  // Getters
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

          const id = `${page.getId()}-${elType}-${dId}`;

          if (elType === elementType) {
            result.set(id, data!.get(id) as GraphData);
          }

          res = keys ? keys.next() : null;
        }

      } else {
        result = data !== null ? data : null;
      }
      return result;
    };
  }

  public get pageDatum(): (page: GraphPage, datumId: string) => GraphData | null {
    return (page: GraphPage, datumId: string) => {
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

      if (keyId) {
        const datum = (data as Map<string, GraphData>).get(`${page.getId()}-${keyId[0]}-${keyId[1]}`);
        return datum !== null || datum !== undefined ? datum as GraphData : null;
      } else {
        return null;
      }
    };
  }

  public get pageElementType(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as Map<string, GraphPage>).get(page.getId() as string) as GraphPage).getType() as string;
    };
  }

  public get pageId(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as Map<string, GraphPage>).get(page.getId() as string) as GraphPage).getId() as string;
    };
  }

  public get pageLabel(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as Map<string, GraphPage>).get(page.getId() as string) as GraphPage).getLabel() as string;
    };
  }

  public get pageNodes(): (page: GraphPage, elementType?: string) => Map<string, GraphData> | null {
    return (page: GraphPage, elementType?: string) => {
      let result: Map<string, GraphNode> | null = new Map<string, GraphNode>();
      const nodes = page.getNodes() ? page.getNodes() : null;

      if (elementType) {
        const keys = nodes ? nodes.keys() : null;
        let res = keys ? keys.next() : null;

        while (res && !res.done) {
          const elType = res.value.split('-')[1];
          const nId = res.value.split('-')[2];

          const id = `${page.getId()}-${elType}-${nId}`;

          if (elType === elementType) {
            result.set(id, nodes!.get(id) as GraphNode);
          }

          res = keys ? keys.next() : null;
        }

      } else {
        result = nodes !== null ? nodes : null;
      }
      return result;
    };
  }

  public get pageNode(): (page: GraphPage, nodeId: string) => GraphNode | null {
    return (page: GraphPage, nodeId: string) => {
      const nodes = this.pageNodes(page) !== null ? this.pageNodes(page) : null;
      const keys = (nodes as Map<string, GraphNode>).keys();

      const findKeyId: (keys: IterableIterator<string>) => string[] | undefined = () => {
        let result = keys.next();

        while (!result.done) {
          const elType = result.value.split('-')[1];
          const nId = result.value.split('-')[2];

          if (nId === nodeId) {
            return [elType, nId];
          }

          result = keys.next();
        }
      };

      const keyId = findKeyId(keys);

      if (keyId) {
        const node = (nodes as Map<string, GraphNode>).get(`${page.getId()}-${keyId[0]}-${keyId[1]}`);
        return node !== null || node !== undefined ? node as GraphNode : null;
      } else {
        return null;
      }
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
