import { GraphElement } from '../interfaces/GraphElement';

export class GraphElementImpl implements GraphElement {
  public readonly TYPE: string | 'element' = 'element';

  private id?: string | undefined;
  private label?: string | undefined;
  private elementType: string = this.TYPE;
  private attributes: Map<string, string> = new Map<string, string>();

  constructor();
  constructor(id: string, label: string);
  constructor(id?: string, label?: string) {
    this.id = id;
    this.label = label;
  }

  public fn_graph_element_get_id(): string | undefined {
    return this.id;
  }
  public fn_graph_element_get_label(): string | undefined {
    return this.label;
  }
  public fn_graph_element_get_type(): string | undefined {
    return this.elementType;
  }
  public fn_graph_element_get_attributes(): Map<string, string> | undefined {
    return this.attributes;
  }
}
