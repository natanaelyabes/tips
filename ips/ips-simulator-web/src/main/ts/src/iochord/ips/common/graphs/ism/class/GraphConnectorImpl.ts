import { GraphStopEventNodeImpl } from './components/GraphStopEventNodeImpl';
import { GraphBranchNodeImpl } from './components/GraphBranchNodeImpl';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';
import { GraphActivityNodeImpl } from './components/GraphActivityNodeImpl';
import { GraphStartEventNodeImpl } from './components/GraphStartEventNodeImpl';
import { TSMap } from 'typescript-map';
import { GraphNodeImpl } from './GraphNodeImpl';

/**
 * Implementation of GraphConnector interface.
 *
 * @export
 * @class GraphConnectorImpl
 * @extends {GraphElementImpl}
 * @implements {GraphConnector}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphConnectorImpl extends GraphElementImpl implements GraphConnector {

  /**
   * Instances of GraphConnector
   *
   * @static
   * @type {TSMap<string, GraphConnector>}
   * @memberof GraphConnectorImpl
   */
  public static instance: TSMap<string, GraphConnector> = new TSMap<string, GraphConnector>();

  /**
   * Deserialize JSON object as GraphConnectorImpl class
   *
   * @static
   * @param {*} object
   * @returns {(TSMap<string, GraphConnector> | null)}
   * @memberof GraphConnectorImpl
   */
  public static deserialize(object: any): TSMap<string, GraphConnector> | null {
    const graphArcMap: TSMap<string, GraphConnector> = new TSMap<string, GraphConnector>();
    const graphNodeInstance: Array<TSMap<string, GraphNode>> = [
      GraphActivityNodeImpl.instance,
      GraphBranchNodeImpl.instance,
      GraphStartEventNodeImpl.instance,
      GraphStopEventNodeImpl.instance,
    ];

    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key];
        const graphArc: GraphConnector = new GraphConnectorImpl();
        graphArc.setId(element.id);
        graphArc.setLabel(element.label);
        graphArc.setType(element.elementType);
        graphArc.setAttributes(element.attributes);

        graphNodeInstance.forEach((value: TSMap<string, GraphNode>) => {
          if (value.get(element.sourceRef)) {
            graphArc.setSourceRef(element.sourceRef);
          }
        });

        graphNodeInstance.forEach((value: TSMap<string, GraphNode>) => {
          if (value.get(element.targetRef)) {
            graphArc.setTargetRef(element.targetRef);
          }
        });
        GraphConnectorImpl.instance.set(key, graphArc);
        graphArcMap.set(key, graphArc);
      }
    }
    return graphArcMap;
  }

  /**
   * Source node of the current connector.
   *
   * @private
   * @type {(GraphElement | null)}
   * @memberof GraphConnectorImpl
   */

  private source?: GraphElement | null;

  /**
   * Source node of the current conenctor as string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphConnectorImpl
   */
  private sourceRef?: string | null;

  /**
   * The index of source node.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphConnectorImpl
   */
  private sourceIndex?: number | null = 0;

  /**
   * Target node of the current connector.
   *
   * @private
   * @type {(GraphElement | null)}
   * @memberof GraphConnectorImpl
   */
  private target?: GraphElement | null;

  /**
   * Target node of the current connector as string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphConnectorImpl
   */
  private targetRef?: string | null;

  /**
   * Target index of the current connector.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphConnectorImpl
   */
  private targetIndex?: number | null = 0;

  /**
   * Creates an instance of GraphConnectorImpl.
   *
   * @memberof GraphConnectorImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the source node of the current connector.
   *
   * @returns {(GraphElement | null)}
   * @memberof GraphConnectorImpl
   */
  public getSource(): GraphElement | null {
    return this.source as GraphElement | null;
  }

  /**
   * Assigns a source node to the current connector.
   *
   * @param {GraphElement} source
   * @memberof GraphConnectorImpl
   */
  public setSource(source: GraphElement): void {
    this.source = source as GraphElement | null;
  }

  /**
   * Returns source index to the current connector.
   *
   * @returns {(number | null)}
   * @memberof GraphConnectorImpl
   */
  public getSourceIndex(): number | null {
    return this.sourceIndex as number | null;
  }

  /**
   * Assigns source index to the current connector.
   *
   * @param {number} index
   * @memberof GraphConnectorImpl
   */
  public setSourceIndex(index: number): void {
    this.sourceIndex = index;
  }

  /**
   * Returns the source node as string reference.
   *
   * @returns {(string | null)}
   * @memberof GraphConnectorImpl
   */
  public getSourceRef(): string | null {
    return this.sourceRef as string;
  }

  /**
   * Assigns the source node as string reference to the current connector.
   *
   * @param {string} source
   * @memberof GraphConnectorImpl
   */
  public setSourceRef(source: string): void {
    this.sourceRef = source;
    const node = GraphNodeImpl.instance.get(this.getSourceRef() as string);

    if (node.getType() === 'branch') {

      // Get all connectors
      const connectors = GraphConnectorImpl.instance;

      // Get its output nodes
      const outputNodes = connectors.values()
        .filter((connector: GraphConnector) => connector.getSourceRef() === node.getId())
        .map((connector: GraphConnector) => connector.getTargetRef());

      this.setSourceIndex(outputNodes.length);
    }
  }

  /**
   * Returns the target node of current connector.
   *
   * @returns {(GraphElement | null)}
   * @memberof GraphConnectorImpl
   */
  public getTarget(): GraphElement | null {
    return this.target as GraphElement | null;
  }

  /**
   * Assigns target node of current connector.
   *
   * @param {GraphElement} target
   * @memberof GraphConnectorImpl
   */
  public setTarget(target: GraphElement): void {
    this.target = target || this.target;
  }

  /**
   * Returns target index of the current connector.
   *
   * @returns {(number | null)}
   * @memberof GraphConnectorImpl
   */
  public getTargetIndex(): number | null {
    return this.targetIndex as number | null;
  }

  /**
   * Assigns target index of the current connector.
   *
   * @param {number} index
   * @memberof GraphConnectorImpl
   */
  public setTargetIndex(index: number): void {
    this.targetIndex = index;
  }

  /**
   * Returns the target node of the current connector as string reference.
   *
   * @returns {(string | null)}
   * @memberof GraphConnectorImpl
   */
  public getTargetRef(): string | null {
    return this.targetRef as string;
  }

  /**
   * Assigns target node of the current connector as string reference.
   *
   * @param {string} target
   * @memberof GraphConnectorImpl
   */
  public setTargetRef(target: string): void {
    this.targetRef = target;
    const node = GraphNodeImpl.instance.get(this.getTargetRef() as string);

    if (node.getType() === 'branch') {

      // Get all connectors
      const connectors = GraphConnectorImpl.instance;

      // Get its input nodes
      const inputNodes = connectors.values()
        .filter((connector: GraphConnector) => connector.getTargetRef() === node.getId())
        .map((connector: GraphConnector) => connector.getTargetRef());

      this.setTargetIndex(inputNodes.length);
    }
  }

  /**
   * Serialize GraphConnectorImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphConnectorImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
