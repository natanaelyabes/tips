import { GraphElement } from '../interfaces/GraphElement';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphElementImpl implements GraphElement {
  private id?: string | null;
  private label?: string | null;
  private elementType?: string | null;
  private attributes?: Map<string, string> | null = new Map<string, string>();

  constructor() {
    //
  }

  public getId(): string | null {
    return this.id as string | null;
  }

  public setId(id: string) {
    this.id = id || this.id;
  }

  public getLabel(): string | null {
    return this.label as string | null;
  }

  public setLabel(label: string) {
    this.label = label || this.label;
  }

  public getType(): string | null {
    return this.elementType as string | null;
  }

  public setType(type: string) {
    this.elementType = type || this.elementType;
  }

  public getAttributes(): Map<string, string> | null {
    return this.attributes as Map<string, string> | null;
  }

  public setAttributes(attributes: Map<string, string>) {
    this.attributes = attributes || this.attributes;
  }

  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
