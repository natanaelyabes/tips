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
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
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
   *
   *
   * @private
   * @type {(GraphElement | null)}
   * @memberof GraphConnectorImpl
   */

  private source?: GraphElement | null;

  /**
   *
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphConnectorImpl
   */
  private sourceRef?: string | null;

  /**
   *
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphConnectorImpl
   */
  private sourceIndex?: number | null = 0;
  private target?: GraphElement | null;
  private targetRef?: string | null;
  private targetIndex?: number | null = 0;

  constructor() {
    super();
  }

  public getSource(): GraphElement | null {
    return this.source as GraphElement | null;
  }

  public setSource(source: GraphElement): void {
    this.source = source || this.source;
  }

  public getSourceIndex(): number | null {
    return this.sourceIndex as number | null;
  }

  public setSourceIndex(index: number): void {
    this.sourceIndex = index;
  }

  public getSourceRef(): string | null {
    return this.sourceRef as string;
  }

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

  public getTarget(): GraphElement | null {
    return this.target as GraphElement | null;
  }

  public setTarget(target: GraphElement): void {
    this.target = target || this.target;
  }

  public getTargetIndex(): number | null {
    return this.targetIndex as number | null;
  }

  public setTargetIndex(index: number): void {
    this.targetIndex = index;
  }

  public getTargetRef(): string | null {
    return this.targetRef as string;
  }

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

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
