import { GraphElement } from '../interfaces/GraphElement';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphElementImpl implements GraphElement {
  private id?: string | null;
  private label?: string | null;
  private elementType?: string | null;
  private attributes?: TSMap<string, string> | null = new TSMap<string, string>();

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

  public getAttributes(): TSMap<string, string> | null {
    return this.attributes as TSMap<string, string> | null;
  }

  public setAttributes(attributes: TSMap<string, string>) {
    this.attributes = attributes || this.attributes;
  }

  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
