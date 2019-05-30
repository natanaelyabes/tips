import { GraphElement } from '../interfaces/GraphElement';

export class GraphElementImpl implements GraphElement {
  public readonly TYPE: string | null = 'element';

  private id: string | null;
  private label: string | null;
  private elementType: string | null = this.TYPE;
  private attributes: Map<string, string> | null = new Map<string, string>();

  constructor();
  constructor(id: string, label: string);
  constructor(id?: string, label?: string) {
    this.id = id || null;
    this.label = label || null;
  }

  public getId(): string | null {
    return this.id;
  }

  public setId(id: string) {
    this.id = id;
  }

  public getLabel(): string | null {
    return this.label;
  }

  public setLabel(label: string) {
    this.label = label;
  }

  public getType(): string | null {
    return this.elementType;
  }

  public setType(type: string) {
    this.elementType = type;
  }

  public getAttributes(): Map<string, string> | null {
    return this.attributes;
  }

  public setAttributes(attributes: Map<string, string>) {
    this.attributes = attributes;
  }

  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
