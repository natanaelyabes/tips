import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';
import { GraphNodeImpl } from './GraphNodeImpl';
import { NODE_TYPE } from '../enums/NODE';

export class GraphConnectorImpl extends GraphElementImpl implements GraphConnector {
  public static fn_object_deserialize(object: any): Map<string, GraphConnector> {
    const graphArcMap: Map<string, GraphConnector> = new Map<string, GraphConnector>();
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key];
        const graphArc: GraphConnector = new GraphConnectorImpl();
        graphArc.fn_graph_element_set_id(element.id);
        graphArc.fn_graph_element_set_label(element.label);
        graphArc.fn_graph_element_set_type(element.elementType);
        graphArc.fn_graph_element_set_attributes(element.attributes);
        graphArc.fn_graph_connector_set_source((NODE_TYPE as any)[element.source.elementType].fn_object_deserialize(element.source));
        graphArc.fn_graph_connector_set_target((NODE_TYPE as any)[element.target.elementType].fn_object_deserialize(element.target));
        graphArcMap.set(key, graphArc);
      }
    }
    return graphArcMap;
  }

  public readonly TYPE: string | 'connector' = 'connector';

  private source: GraphElement | null;
  private target: GraphElement | null;

  constructor();
  constructor(source: GraphElement, target: GraphElement);
  constructor(source?: GraphElement, target?: GraphElement) {
    super();
    this.source = source || null;
    this.target = target || null;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_connector_get_source(): GraphElement | null {
    return this.source;
  }

  public fn_graph_connector_set_source(source: GraphElement): void {
    this.source = source;
  }

  public fn_graph_connector_get_target(): GraphElement | null {
    return this.target;
  }

  public fn_graph_connector_set_target(target: GraphElement): void {
    this.target = target;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
