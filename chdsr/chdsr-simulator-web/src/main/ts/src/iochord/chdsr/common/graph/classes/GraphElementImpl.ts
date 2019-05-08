import { GraphElement } from '../interfaces/GraphElement';

export class GraphElementImpl implements GraphElement {
  public readonly TYPE: string | 'element' = 'element';

  private id: string | null;
  private label: string | null;
  private elementType: string = this.TYPE;
  private attributes: Map<string, string> = new Map<string, string>();

  constructor();
  constructor(id: string, label: string);
  constructor(id?: string, label?: string) {
    this.id = id || null;
    this.label = label || null;
  }

  public fn_graph_element_get_id(): string | null {
    return this.id;
  }

  public fn_graph_element_set_id(id: string) {
    this.id = id;
  }

  public fn_graph_element_get_label(): string | null {
    return this.label;
  }

  public fn_graph_element_set_label(label: string) {
    this.label = label;
  }

  public fn_graph_element_get_type(): string | null {
    return this.elementType;
  }

  public fn_graph_element_set_type(type: string) {
    this.elementType = type;
  }

  public fn_graph_element_get_attributes(): Map<string, string> {
    return this.attributes;
  }

  public fn_graph_element_set_attributes(attributes: Map<string, string>) {
    this.attributes = attributes;
  }

  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
