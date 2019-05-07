import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';

export class GraphDataResourceImpl extends GraphDataImpl implements GraphDataResource {
  public static readonly TYPE: 'resource' = 'resource';

  /** @Override */
  public static fn_object_deserialize(object: any): GraphDataResource {
    const graphDataResource: GraphDataResource = new GraphDataResourceImpl();
    graphDataResource.fn_graph_element_set_id(object.id);
    graphDataResource.fn_graph_element_set_label(object.label);
    graphDataResource.fn_graph_element_set_type(object.elementType);
    graphDataResource.fn_graph_element_set_attributes(object.attributes as Map<string, string>);
    graphDataResource.fn_graph_data_resource_set_group_id(object.groupId);
    graphDataResource.fn_graph_data_resource_set_data(object.data);
    return graphDataResource;
  }

  private groupId?: string;
  private data?: GraphDataTable;

  constructor();
  constructor(groupId: string, data: GraphDataTable);
  constructor(groupId?: string, data?: GraphDataTable) {
    super();
    this.groupId = groupId;
    this.data = data;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_data_resource_get_group_id(): string | undefined {
    return this.groupId;
  }

  public fn_graph_data_resource_set_group_id(groupId: string): void {
    this.groupId = groupId;
  }

  public fn_graph_data_resource_get_data(): GraphDataTable | undefined {
    return this.data;
  }

  public fn_graph_data_resource_set_data(data: GraphDataTable): void {
    this.data = data;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
