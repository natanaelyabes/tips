import { GraphStopEventNodeImpl } from './components/GraphStopEventNodeImpl';
import { GraphMonitorNodeImpl } from './components/GraphMonitorNodeImpl';
import { GraphConfigurationImpl } from './GraphConfigurationImpl';
import { GraphBranchNodeImpl } from './components/GraphBranchNodeImpl';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphUtil } from './GraphUtil';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';
import { NODE_TYPE } from '../enums/NODE';
import { GraphActivityNodeImpl } from './components/GraphActivityNodeImpl';
import { GraphStartEventNodeImpl } from './components/GraphStartEventNodeImpl';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphConnectorImpl extends GraphElementImpl implements GraphConnector {
  public static instance: Map<string, GraphConnector> = new Map<string, GraphConnector>();

  public static deserialize(object: any): Map<string, GraphConnector> | null {
    const graphArcMap: Map<string, GraphConnector> = new Map<string, GraphConnector>();
    const graphNodeInstance: Array<Map<string, GraphNode>> = [
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
        graphNodeInstance.forEach((value: Map<string, GraphNode>) => {
          if (value.get(element.sourceRef)) {
            graphArc.setSource(value.get(element.sourceRef) as GraphNode);
          }
        });
        graphNodeInstance.forEach((value: Map<string, GraphNode>) => {
          if (value.get(element.targetRef)) {
            graphArc.setTarget(value.get(element.targetRef) as GraphNode);
          }
        });
        GraphConnectorImpl.instance.set(key, graphArc);
        graphArcMap.set(key, graphArc);
      }
    }
    return graphArcMap;
  }

  private source?: GraphElement | null;
  private sourceIndex?: number | null = 0;
  private target?: GraphElement | null;
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
    this.sourceIndex = index || this.sourceIndex;
  }

  public getSourceRef(): string | null {
    return GraphUtil.generateRef(this.getSource());
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
    this.targetIndex = index || this.targetIndex;
  }

  public getTargetRef(): string | null {
    return GraphUtil.generateRef(this.getTarget());
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
