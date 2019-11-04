// Vuex & Libraries
import Vuex from 'vuex';
import { VuexModule, Module, MutationAction, Mutation } from 'vuex-module-decorators';
import { TSMap } from 'typescript-map';

// Interfaces
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphConfiguration } from '@/iochord/ips/common/graph/ism/interfaces/GraphConfiguration';
import { GraphConnector } from '@/iochord/ips/common/graph/ism/interfaces/GraphConnector';
import { GraphConnectorImpl } from '@/iochord/ips/common/graph/ism/class/GraphConnectorImpl';
import { GraphControl } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphControl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';

// Services
import { IsmModelService } from '@/iochord/ips/common/service/model/IsmModelService';
import { GraphDataImpl } from '../class/GraphDataImpl';

// Store type
interface StoreType {
  graphModule: GraphModule;
}

// Dynamic store
const store = new Vuex.Store<StoreType>({});

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Module({ dynamic: true, store, name: 'GraphModule', namespaced: true })
export default class GraphModule extends VuexModule {

  // States
  public graph: Graph = {} as Graph;
  public newItem: GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null = null;

  // Mutations
  @MutationAction({ mutate: ['graph'] })
  public async newGraph(url?: string) {
    const graph: Graph = await IsmModelService.getInstance().getCreate('1');
    return { graph };
  }

  // Mutations
  @MutationAction({ mutate: ['graph'] })
  public async loadExampleGraph() {
    const graph: Graph = await IsmModelService.getInstance().getExampleModel();
    return { graph };
  }

  // Mutations
  @MutationAction({ mutate: ['graph'] })
  public async loadGraph(url?: string) {
    const graph: Graph = await IsmModelService.getInstance().getExampleModel();
    return { graph };
  }

  @Mutation
  public setNewItem(newItem: GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null) {
    this.newItem = newItem;
  }

  @Mutation
  public setGraph(graph: Graph): void {
    this.graph = graph;
  }

  @Mutation
  public setPages(pages: TSMap<string, GraphPage>): void {
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
  public setPageConnectors({ page, connectors }: { page: GraphPage, connectors: TSMap<string, GraphConnector> }): void {
    const pages = this.graph.getPages();
    if (pages !== null) {
      const exists = pages.get(page.getId() as string);
      if (exists) {
        exists.setConnectors(connectors);
      } else {
        throw new Error(`Page ${page.getId()} does not exists in Graph ${this.graph.getId()}`);
      }
    }
  }

  @Mutation
  public addPageArc({ page, arc }: { page: GraphPage, arc: GraphConnector }) {
    const connectors = page.getConnectors();
    if (connectors !== null) {
      const exists = connectors.get(arc.getId() as string);
      if (exists) {
        throw new Error(`Arc ${arc.getId()} had existed in Page ${page.getId()}`);
      }
      connectors.set(arc.getId() as string, arc);
    }
  }

  @Mutation
  public overridePageArc({ page, arc }: { page: GraphPage, arc: GraphConnector }) {
    const connectors = page.getConnectors();
    if (connectors !== null) {
      const exists = connectors.get(arc.getId() as string);
      if (exists) {
        connectors.set(arc.getId() as string, arc);
      } else {
        this.addPageArc({ page, arc });
      }
    }
  }

  @Mutation
  public deletePageArc({ page, arc }: { page: GraphPage, arc: GraphConnector }) {
    const connectors = page.getConnectors();
    if (connectors !== null) {
      const exists = connectors.get(arc.getId() as string);
      if (exists) {
        connectors.delete(arc.getId() as string);
      } else {
        throw new Error(`Arc ${arc.getId()} had existed in Page ${page.getId()}`);
      }
    }
  }

  @Mutation
  public setPageData({ page, data }: { page: GraphPage, data: TSMap<string, GraphData> }): void {
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
  public addPageDatum({ page, datum }: { page: GraphPage, datum: GraphData }): void {
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
  public overridePageDatum({ page, datum }: { page: GraphPage, datum: GraphData }) {
    const data = page.getData();
    if (data !== null) {
      const exists = data.get(datum.getId() as string);
      if (exists) {
        data.set(datum.getId() as string, datum);
      } else {
        this.addPageDatum({page, datum});
      }
    }
  }

  @Mutation
  public deletePageDatum({ page, datum }: { page: GraphPage, datum: GraphData }) {
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
  public setPageElementType({ page, elementType }: { page: GraphPage, elementType: string }) {
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
  public setPageId({ page, id }: { page: GraphPage, id: string }) {
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
  public setPageLabel({ page, label }: { page: GraphPage, label: string }) {
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
  public setPageNodes({ page, nodes }: { page: GraphPage, nodes: TSMap<string, GraphNode> }) {
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
  public addPageNode({ page, node }: { page: GraphPage, node: GraphNode }): void {
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
  public overridePageNode({ page, node }: { page: GraphPage, node: GraphNode }) {
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
  public deletePageNode({ page, node }: { page: GraphPage, node: GraphNode }) {
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
  public setConfigurations(configurations: TSMap<string, GraphConfiguration>): void {
    this.graph.setConfigurations(configurations);
  }

  @Mutation
  public setControl(control: GraphControl): void {
    this.graph.setControl(control);
  }

  @Mutation
  public setData(data: TSMap<string, GraphData>): void {
    this.graph.setData(data);
  }

  public get getNewItem(): GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null {
    return this.newItem ? this.newItem : null;
  }

  public get version(): string | null {
    return this.graph ? this.graph.getVersion() : null;
  }

  public get pages(): TSMap<string, GraphPage> | null {
    return this.graph ? this.graph.getPages() : null;
  }

  public get defaultPage(): GraphPage | null {
    return this.graph ? (this.graph.getPages() as TSMap<string, GraphPage>).get('0') as GraphPage : null;
  }

  public get page(): (pageId: string) => GraphPage | null {
    const pages = this.graph ? this.graph.getPages() as TSMap<string, GraphPage> : null;
    return (pageId: string) => pages !== null ? pages.get(pageId) as GraphPage : null;
  }

  public get pageConnectors(): (page: GraphPage) => TSMap<string, GraphConnector> | null {
    return (page: GraphPage) => {
      const connectors = page.getConnectors();
      return connectors !== null ? connectors : null;
    };
  }

  public get pageArc(): (page: GraphPage, arcId: string) => GraphConnector | null {
    return (page: GraphPage, arcId: string) => {
      const connectors = this.pageConnectors(page) !== null ? this.pageConnectors(page) : null;
      const arc = (connectors as TSMap<string, GraphConnector>).get(arcId);
      return arc !== null || arc !== undefined ? arc as GraphConnector : null;
    };
  }

  public get pageData(): (page: GraphPage, elementType?: string) => TSMap<string, GraphData> | null {
    return (page: GraphPage, elementType?: string) => {
      let result: TSMap<string, GraphData> | null = new TSMap<string, GraphData>();
      const data = page.getData() ? page.getData() : null;

      if (elementType) {
        const keys = data ? data.keys() : null;

        (keys as string[]).forEach((key) => {
          const elType = key.split('-')[1];
          const dId = key.split('-')[2];
          const id = `${page.getId()}-${elType}-${dId}`;

          if (elType === elementType) {
            (result as TSMap<string, GraphData>).set(id, data!.get(id) as GraphData);
          }
        });
      } else {
        result = data !== null ? data : null;
      }
      return result;
    };
  }

  public get pageDatum(): (page: GraphPage, datumId: string) => GraphData | null {
    return (page: GraphPage, datumId: string) => {
      const data = this.pageData(page) !== null ? this.pageData(page) : null;
      const keys = (data as TSMap<string, GraphData>).keys();

      const findKeyId: (keys: string[]) => string[] | undefined = () => {
        let results = keys;

        results.forEach((result) => {
          const elType = result.split('-')[1];
          const dId = result.split('-')[2];

          if (dId === datumId) {
            results = [elType, dId];
          }
        });

        return results;
      };

      const keyId = findKeyId(keys);

      if (keyId) {
        const datum = (data as TSMap<string, GraphData>).get(`${page.getId()}-${keyId[0]}-${keyId[1]}`);
        return datum !== null || datum !== undefined ? datum as GraphData : null;
      } else {
        return null;
      }
    };
  }

  public get pageElementType(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as TSMap<string, GraphPage>).get(page.getId() as string) as GraphPage).getType() as string;
    };
  }

  public get pageId(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as TSMap<string, GraphPage>).get(page.getId() as string) as GraphPage).getId() as string;
    };
  }

  public get pageLabel(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as TSMap<string, GraphPage>).get(page.getId() as string) as GraphPage).getLabel() as string;
    };
  }

  public get pageNodes(): (page: GraphPage, elementType?: string) => TSMap<string, GraphData> | null {
    return (page: GraphPage, elementType?: string) => {
      let result: TSMap<string, GraphNode> | null = new TSMap<string, GraphNode>();
      const nodes = page.getNodes() ? page.getNodes() : null;

      if (elementType) {
        const keys = nodes ? nodes.keys() : null;

        (keys as string[]).forEach((key) => {
          const elType = key.split('-')[1];
          const nId = key.split('-')[2];

          const id = `${page.getId()}-${elType}-${nId}`;

          if (elType === elementType) {
            (result as TSMap<string, GraphNode>).set(id, nodes!.get(id) as GraphNode);
          }
        });
      } else {
        result = nodes !== null ? nodes : null;
      }
      return result;
    };
  }

  public get pageNode(): (page: GraphPage, nodeId: string) => GraphNode | null {
    return (page: GraphPage, nodeId: string) => {
      const nodes = this.pageNodes(page) !== null ? this.pageNodes(page) : null;
      const keys = (nodes as TSMap<string, GraphNode>).keys();

      const findKeyId: (keys: string[]) => string[] | undefined = () => {
        let results = keys;

        results.forEach((result) => {
          const elType = result.split('-')[1];
          const nId = result.split('-')[2];

          if (nId === nodeId) {
            results = [elType, nId];
          }
        });

        return results;
      };

      const keyId = findKeyId(keys);

      if (keyId) {
        const node = (nodes as TSMap<string, GraphNode>).get(`${page.getId()}-${keyId[0]}-${keyId[1]}`);
        return node !== null || node !== undefined ? node as GraphNode : null;
      } else {
        return null;
      }
    };
  }

  public get configurations(): TSMap<string, GraphConfiguration> | null {
    return this.graph ? this.graph.getConfigurations() : null;
  }

  public get control(): GraphControl | null {
    return this.graph ? this.graph.getControl() : null;
  }

  public get data(): TSMap<string, GraphData> | null {
    return this.graph ? this.graph.getData() : null;
  }
}
