import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';

export class GraphConnectorImpl extends GraphElementImpl implements GraphConnector {
  public readonly TYPE: string | 'connector' = 'connector';
  private source?: GraphElement | undefined;
  private target?: GraphElement | undefined;

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

  public fn_graph_connector_get_target(): GraphElement | undefined {
    return this.target;
  }
}
