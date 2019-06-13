import { ObjectSerializable } from '../../object/ObjectSerializable';

export interface GraphElement extends ObjectSerializable {
  getId(): string | null;
  setId(id: string): void;
  getLabel(): string | null;
  setLabel(label: string): void;
  getType(): string | null;
  setType(type: string): void;
  getAttributes(): Map<string, string> | null;
  setAttributes(attributes: Map<string, string>): void;
}
