import { ObjectSerializable } from '../../object/ObjectSerializable';

export interface GraphElement extends ObjectSerializable {
  // readonly TYPE: string | 'element';
  fn_graph_element_get_id(): string | null;
  fn_graph_element_set_id(id: string): void;
  fn_graph_element_get_label(): string | null;
  fn_graph_element_set_label(label: string): void;
  fn_graph_element_get_type(): string | null;
  fn_graph_element_set_type(type: string): void;
  fn_graph_element_get_attributes(): Map<string, string>;
  fn_graph_element_set_attributes(attributes: Map<string, string>): void;
}
