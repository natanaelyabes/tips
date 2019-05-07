import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';

export class GraphDataTableImpl extends GraphDataImpl implements GraphDataTable {
  public static readonly TYPE: 'datatable' = 'datatable';

  /** @Override */
  public static fn_object_deserialize(object: any): Map<string, GraphDataTable> {
    const graphDataTableMap: Map<string, GraphDataTable> = new Map<string, GraphDataTable>();
    return graphDataTableMap;
  }

  private fields?: string[];
  private data?: Map<string, Map<string, object>>;

  constructor();
  constructor(fields: string[], data: Map<string, Map<string, object>>);
  constructor(fields?: string[], data?: Map<string, Map<string, object>>) {
    super();
    this.fields = fields;
    this.data = data;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_data_table_get_fields(): string[] | undefined {
    return this.fields;
  }

  public fn_graph_data_table_set_fields(fields: string[]): void {
    this.fields = fields;
  }

  public fn_graph_data_table_get_data(): Map<string, Map<string, object>> | undefined {
    return this.data;
  }

  public fn_graph_data_table_set_data(data: Map<string, Map<string, object>>): void {
    this.data = data;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
