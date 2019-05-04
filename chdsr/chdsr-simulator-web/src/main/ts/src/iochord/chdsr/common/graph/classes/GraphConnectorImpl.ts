import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';

export class GraphConnectorImpl extends GraphElementImpl implements GraphConnector {
  public readonly TYPE: string | 'connector' = 'connector';
  private source?: GraphElement;
  private target?: GraphElement;

  constructor();
  constructor(source: GraphElement, target: GraphElement);
  constructor(source?: GraphElement, target?: GraphElement) {
    super();
    this.source = source;
    this.target = target;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_connector_get_source(): GraphElement | undefined {
    return this.source;
  }

  public fn_graph_connector_set_source(source: GraphElement): void {
    this.source = source;
  }

  public fn_graph_connector_get_target(): GraphElement | undefined {
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
