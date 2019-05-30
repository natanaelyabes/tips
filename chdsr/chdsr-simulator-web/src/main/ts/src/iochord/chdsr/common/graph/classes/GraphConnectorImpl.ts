import { GraphStopEventNodeImpl } from './components/GraphEndEventNodeImpl';
import { GraphMonitorNodeImpl } from './components/GraphMonitorNodeImpl';
import { GraphConfigurationImpl } from './GraphConfigurationImpl';
import { GraphBranchNodeImpl } from './components/GraphBranchNodeImpl';
import { GraphNode } from './../interfaces/GraphNode';
import { GraphUtil } from './GraphUtil';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';
import { NODE_TYPE } from '../enums/NODE';
import { GraphActivityNodeImpl } from './components/GraphActivityNodeImpl';
import { GraphStartEventNodeImpl } from './components/GraphStartEventNodeImpl';

export class GraphConnectorImpl extends GraphElementImpl implements GraphConnector {
  public static readonly TYPE: string | null = 'connector';

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
        graphArcMap.set(key, graphArc);
      }
    }
    console.log(graphArcMap);
    return graphArcMap;
  }

  private source: GraphElement | null;
  private sourceIndex: number | null;
  private target: GraphElement | null;
  private targetIndex: number | null;

  constructor();
  constructor(source: GraphElement, sourceIndex: number, target: GraphElement, targetIndex: number);
  constructor(source?: GraphElement, sourceIndex?: number, target?: GraphElement, targetIndex?: number) {
    super();
    this.source = source || null;
    this.sourceIndex = sourceIndex || 0 || null;
    this.target = target || null;
    this.targetIndex = targetIndex || 0 || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getSource(): GraphElement | null {
    return this.source;
  }

  public setSource(source: GraphElement): void {
    this.source = source;
  }

  public getSourceIndex(): number | null {
    return this.sourceIndex;
  }

  public setSourceIndex(index: number): void {
    this.sourceIndex = index;
  }

  public getSourceRef(): string | null {
    return GraphUtil.generateRef(this.getSource());
  }

  public getTarget(): GraphElement | null {
    return this.target;
  }

  public setTarget(target: GraphElement): void {
    this.target = target;
  }

  public getTargetIndex(): number | null {
    return this.targetIndex;
  }

  public setTargetIndex(index: number): void {
    this.targetIndex = index;
  }

  public getTargetRef(): string | null {
    return GraphUtil.generateRef(this.getTarget());
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
