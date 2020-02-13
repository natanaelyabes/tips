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

@Module({ dynamic: true, store, name: 'GraphModule', namespaced: true })

/**
 * Graph vuex module.
 *
 * @export
 * @Module
 * @class GraphModule
 * @extends {VuexModule}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class GraphModule extends VuexModule {

  // 1. States

  /**
   * The graph state.
   *
   * @State
   *
   * @type {Graph}
   * @memberof GraphModule
   */
  public graph: Graph = {} as Graph;


  /**
   * New item state, to store graph element temporarily.
   *
   * @type {(GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null)}
   * @memberof GraphModule
   */
  public newItem: GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null = null;

  // 2. Mutations

  /**
   * To create new graph from the service and store it within graph vuex store.
   *
   * @MutationAction
   *
   * @param {string} [url]
   * @returns { Graph }
   * @memberof GraphModule
   */
  @MutationAction({ mutate: ['graph'] })
  public async newGraph(url?: string) {
    const graph: Graph = await IsmModelService.getInstance().getCreate('1');
    return { graph };
  }

  /**
   * Load example graph from the service and store it within graph vuex store.
   *
   * @MutationAction
   *
   * @returns { Graph }
   * @memberof GraphModule
   */
  @MutationAction({ mutate: ['graph'] })
  public async loadExampleGraph() {
    const graph: Graph = await IsmModelService.getInstance().getExampleModel();
    return { graph };
  }

  /**
   * Load graph from the service and store it within graph vuex store.
   *
   * @MutationAction
   *
   * @returns { Graph }
   * @memberof GraphModule
   */
  @MutationAction({ mutate: ['graph'] })
  public async loadGraph(url?: string) {
    const graph: Graph = await IsmModelService.getInstance().getExampleModel();
    return { graph };
  }

  /**
   * Assigns new item to vuex store.
   *
   * @Mutation
   *
   * @param {(GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null)} newItem
   * @memberof GraphModule
   */
  @Mutation
  public setNewItem(newItem: GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null) {
    this.newItem = newItem;
  }

  /**
   * Assigns graph to vuex store.
   *
   * @Mutation
   *
   * @param {Graph} graph
   * @memberof GraphModule
   */
  @Mutation
  public setGraph(graph: Graph): void {
    this.graph = graph;
  }

  /**
   * Assigns pages to vuex store.
   *
   * @Mutation
   *
   * @param {TSMap<string, GraphPage>} pages
   * @memberof GraphModule
   */
  @Mutation
  public setPages(pages: TSMap<string, GraphPage>): void {
    this.graph.setPages(pages);
  }

  /**
   * Assigns default page and amends vuex store.
   *
   * @Mutation
   *
   * @param {GraphPage} page
   * @memberof GraphModule
   */
  @Mutation
  public setDefaultPage(page: GraphPage): void {
    this.graph.setDefaultPage(page);
  }

  /**
   * Add page to current graph and amends vuex store.
   *
   * @Mutation
   *
   * @param {GraphPage} page
   * @memberof GraphModule
   */
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

  /**
   * Overrides page to current graph and amends vuex store.
   *
   * @Mutation
   *
   * @param {GraphPage} page
   * @memberof GraphModule
   */
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

  /**
   * Delete page from current graph and amends vuex store.
   *
   * @Mutation
   *
   * @param {GraphPage} page
   * @memberof GraphModule
   */
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

  /**
   * Specify node connectors to current page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, connectors: TSMap<string, GraphConnector> }} { page, connectors }
   * @memberof GraphModule
   */
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

  /**
   * Add connector to page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, arc: GraphConnector }} { page, arc }
   * @memberof GraphModule
   */
  @Mutation
  public addPageConnector({ page, arc }: { page: GraphPage, arc: GraphConnector }) {
    const connectors = page.getConnectors();
    if (connectors !== null) {
      const exists = connectors.get(arc.getId() as string);
      if (exists) {
        throw new Error(`Arc ${arc.getId()} had existed in Page ${page.getId()}`);
      }
      connectors.set(arc.getId() as string, arc);
    }
  }

  /**
   * Overrides page connector and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, arc: GraphConnector }} { page, arc }
   * @memberof GraphModule
   */
  @Mutation
  public overridePageConnector({ page, arc }: { page: GraphPage, arc: GraphConnector }) {
    const connectors = page.getConnectors();
    if (connectors !== null) {
      const exists = connectors.get(arc.getId() as string);
      if (exists) {
        connectors.set(arc.getId() as string, arc);
      } else {
        this.addPageConnector({ page, arc });
      }
    }
  }

  /**
   * Removes page connector and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, arc: GraphConnector }} { page, arc }
   * @memberof GraphModule
   */
  @Mutation
  public deletePageConnector({ page, arc }: { page: GraphPage, arc: GraphConnector }) {
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

  /**
   * Assigns data node to the specified graph page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, data: TSMap<string, GraphData> }} { page, data }
   * @memberof GraphModule
   */
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

  /**
   * Add new data to specified graph page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, datum: GraphData }} { page, datum }
   * @memberof GraphModule
   */
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

  /**
   * Overrides data node at specified graph page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, datum: GraphData }} { page, datum }
   * @memberof GraphModule
   */
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

  /**
   * Removes data node at specified graph page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, datum: GraphData }} { page, datum }
   * @memberof GraphModule
   */
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

  /**
   * Assigns element type to specified page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, elementType: string }} { page, elementType }
   * @memberof GraphModule
   */
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

  /**
   * Assigns page id to the specified page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, id: string }} { page, id }
   * @memberof GraphModule
   */
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

  /**
   * Assigns page label to the specified page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, label: string }} { page, label }
   * @memberof GraphModule
   */
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

  /**
   * Assigns nodes to the specified page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, nodes: TSMap<string, GraphNode> }} { page, nodes }
   * @memberof GraphModule
   */
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

  /**
   * Add node to the specified page and amends vuex store
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, node: GraphNode }} { page, node }
   * @memberof GraphModule
   */
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

  /**
   * Overrides node of the specified page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, node: GraphNode }} { page, node }
   * @memberof GraphModule
   */
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

  /**
   * Removes node from specified page and amends vuex store.
   *
   * @Mutation
   *
   * @param {{ page: GraphPage, node: GraphNode }} { page, node }
   * @memberof GraphModule
   */
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

  /**
   * Assigns configurations to the specified page and amends vuex store.
   *
   * @Mutation
   *
   * @param {TSMap<string, GraphConfiguration>} configurations
   * @memberof GraphModule
   */
  @Mutation
  public setConfigurations(configurations: TSMap<string, GraphConfiguration>): void {
    this.graph.setConfigurations(configurations);
  }

  /**
   * Assigns control to vuex store.
   *
   * @Mutation
   *
   * @param {GraphControl} control
   * @memberof GraphModule
   */
  @Mutation
  public setControl(control: GraphControl): void {
    this.graph.setControl(control);
  }

  /**
   * Returns new item state from vuex store.
   *
   * @Getter
   *
   * @readonly
   * @type {(GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null)}
   * @memberof GraphModule
   */
  public get getNewItem(): GraphNodeImpl | GraphDataImpl | GraphConnectorImpl | null {
    return this.newItem ? this.newItem : null;
  }

  /**
   * Returns version state from vuex store.
   *
   * @Getter
   *
   * @readonly
   * @type {(string | null)}
   * @memberof GraphModule
   */
  public get version(): string | null {
    return this.graph ? this.graph.getVersion() : null;
  }

  /**
   * Returns pages from vuex store.
   *
   * @Getter
   *
   * @readonly
   * @type {(TSMap<string, GraphPage> | null)}
   * @memberof GraphModule
   */
  public get pages(): TSMap<string, GraphPage> | null {
    return this.graph ? this.graph.getPages() : null;
  }

  /**
   * Returns the default page from vuex store.
   *
   * @Getter
   *
   * @readonly
   * @type {(GraphPage | null)}
   * @memberof GraphModule
   */
  public get defaultPage(): GraphPage | null {
    return this.graph ? (this.graph.getPages() as TSMap<string, GraphPage>).get('0') as GraphPage : null;
  }

  /**
   * Returns the page from vuex store given pageId.
   *
   * @Getter
   *
   * @readonly
   * @param {string} pageId
   * @type {(GraphPage | null)}
   * @memberof GraphModule
   */
  public get page(): (pageId: string) => GraphPage | null {
    const pages = this.graph ? this.graph.getPages() as TSMap<string, GraphPage> : null;
    return (pageId: string) => pages !== null ? pages.get(pageId) as GraphPage : null;
  }

  /**
   * Returns page connectors of specified page from vuex store.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @type {(TSMap<string, GraphConnector> | null)}
   * @memberof GraphModule
   */
  public get pageConnectors(): (page: GraphPage) => TSMap<string, GraphConnector> | null {
    return (page: GraphPage) => {
      const connectors = page.getConnectors();
      return connectors !== null ? connectors : null;
    };
  }

  /**
   * Returns the connector of the specified page from vuex store given the connectorId.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @param {string} connectorId
   * @type {(GraphConnector | null)}
   * @memberof GraphModule
   */
  public get pageConnector(): (page: GraphPage, connectorId: string) => GraphConnector | null {
    return (page: GraphPage, connectorId: string) => {
      const connectors = this.pageConnectors(page) !== null ? this.pageConnectors(page) : null;
      const arc = (connectors as TSMap<string, GraphConnector>).get(connectorId);
      return arc !== null || arc !== undefined ? arc as GraphConnector : null;
    };
  }

  /**
   * Returns data nodes from specified graph page and optionally based on their element type.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @param {string} [elementType]
   * @type {TSMap<string, GraphData> | null}
   * @memberof GraphModule
   */
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

  /**
   * Returns data node from specified graph page and identified by its id.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @param {string} datumId
   * @type {(GraphData | null)}
   * @memberof GraphModule
   */
  public get pageDatum(): (page: GraphPage, datumId: string) => GraphData | null {
    return (page: GraphPage, datumId: string) => {
      const data = this.pageData(page) !== null ? this.pageData(page) : null;
      const keys = (data as TSMap<string, GraphData>).keys();

      const findKeyId: (keys: string[]) => string[] | undefined = () => {
        let results = keys;

        results.forEach((result) => {
          const elType = result.split('-')[1];
          const dId = result.split('-')[2];

          if (result === datumId) {
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

  /**
   * Returns element type of specified page
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @type {(string | null)}
   * @memberof GraphModule
   */
  public get pageElementType(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as TSMap<string, GraphPage>).get(page.getId() as string) as GraphPage).getType() as string;
    };
  }

  /**
   * Returns the id of current specified page.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @type {(string | null)}
   * @memberof GraphModule
   */
  public get pageId(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as TSMap<string, GraphPage>).get(page.getId() as string) as GraphPage).getId() as string;
    };
  }

  /**
   * Returns the label of specified page.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @type {(string | null)}
   * @memberof GraphModule
   */
  public get pageLabel(): (page: GraphPage) => string | null {
    return (page: GraphPage) => {
      return ((this.graph.getPages() as TSMap<string, GraphPage>).get(page.getId() as string) as GraphPage).getLabel() as string;
    };
  }

  /**
   * Returns nodes of current specified page and optionally based on their element types.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @param {string} [elementType]
   * @type {(TSMap<string, GraphData> | null)}
   * @memberof GraphModule
   */
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

  /**
   * Returns node of current specified page based on its id.
   *
   * @Getter
   *
   * @readonly
   * @param {GraphPage} page
   * @param {string} [nodeId]
   * @type {(GraphNode | null)}
   * @memberof GraphModule
   */
  public get pageNode(): (page: GraphPage, nodeId: string) => GraphNode | null {
    return (page: GraphPage, nodeId: string) => {
      const nodes = this.pageNodes(page) !== null ? this.pageNodes(page) : null;
      const keys = (nodes as TSMap<string, GraphNode>).keys();

      const findKeyId: (keys: string[]) => string[] | undefined = () => {
        let results = keys;

        results.forEach((result) => {
          const elType = result.split('-')[1];
          const nId = result.split('-')[2];

          if (result === nodeId) {
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

  /**
   * Returns the configuration settings of current graph.
   *
   * @Getter
   *
   * @readonly
   * @type {(TSMap<string, GraphConfiguration> | null)}
   * @memberof GraphModule
   */
  public get configurations(): TSMap<string, GraphConfiguration> | null {
    return this.graph ? this.graph.getConfigurations() : null;
  }

  /**
   * Returns the control settings of current graph.
   *
   * @Getter
   *
   * @readonly
   * @type {(GraphControl | null)}
   * @memberof GraphModule
   */
  public get control(): GraphControl | null {
    return this.graph ? this.graph.getControl() : null;
  }
}
