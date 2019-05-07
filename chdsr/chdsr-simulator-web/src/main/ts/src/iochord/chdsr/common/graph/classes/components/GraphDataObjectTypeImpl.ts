import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { GraphDataTableImpl } from './GraphDataTableImpl';

export class GraphDataObjectTypeImpl extends GraphDataImpl implements GraphDataObjectType {
  public static readonly TYPE: 'objecttype' = 'objecttype';

  /** @Override */
  public static fn_object_deserialize(object: any): GraphDataObjectType {
    const graphDataObjectType: GraphDataObjectType = new GraphDataObjectTypeImpl();
    graphDataObjectType.fn_graph_element_set_id(object.id);
    graphDataObjectType.fn_graph_element_set_label(object.label);
    graphDataObjectType.fn_graph_element_set_type(object.type);
    graphDataObjectType.fn_graph_element_set_attributes(object.attributes);
    graphDataObjectType.fn_graph_data_object_type_set_fields(GraphDataTableImpl.fn_object_deserialize(object.fields));
    return graphDataObjectType;
  }

  private fields?: Map<string, GraphDataTable>;

  constructor(fields?: Map<string, GraphDataTable>) {
    super();
    this.fields = fields;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_data_object_type_get_fields(): Map<string, GraphDataTable> | undefined {
    return this.fields;
  }

  public fn_graph_data_object_type_set_fields(fields: Map<string, GraphDataTable>): void {
    this.fields = fields;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
