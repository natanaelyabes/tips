import { GraphElement } from '../interfaces/GraphElement';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphElement interface.
 *
 * @export
 * @class GraphElementImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphElement}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphElementImpl implements GraphElement {

  /**
   * The id of current element.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphElementImpl
   */
  private id?: string | null;

  /**
   * Label for current element.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphElementImpl
   */
  private label?: string | null;

  /**
   * The type of current element.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphElementImpl
   */
  private elementType?: string | null;

  /**
   * The attributes of current element.
   *
   * @private
   * @type {(TSMap<string, string> | null)}
   * @memberof GraphElementImpl
   */
  private attributes?: TSMap<string, string> | null = new TSMap<string, string>();

  /**
   * The URI location of current element image icon.
   *
   * @private
   * @type {string}
   * @memberof GraphElementImpl
   */
  private image?: string;

  /**
   * Returns the id of current element.
   *
   * @returns {(string | null)}
   * @memberof GraphElementImpl
   */
  public getId(): string | null {
    return this.id as string | null;
  }

  /**
   * Assigns an id to the current element.
   *
   * @param {string} id
   * @memberof GraphElementImpl
   */
  public setId(id: string) {
    this.id = id || this.id;
  }

  /**
   * Returns the label of current element.
   *
   * @returns {(string | null)}
   * @memberof GraphElementImpl
   */
  public getLabel(): string | null {
    return this.label as string | null;
  }

  /**
   * Assigns label to the current element.
   *
   * @param {string} label
   * @memberof GraphElementImpl
   */
  public setLabel(label: string) {
    this.label = label || this.label;
  }

  /**
   * Returns the type of current element.
   *
   * @returns {(string | null)}
   * @memberof GraphElementImpl
   */
  public getType(): string | null {
    return this.elementType as string | null;
  }

  /**
   * Assigns the type for current element.
   *
   * @param {string} type
   * @memberof GraphElementImpl
   */
  public setType(type: string) {
    this.elementType = type || this.elementType;
  }

  /**
   * Returns the attribute assigned to the current element.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphElementImpl
   */
  public getAttributes(): TSMap<string, string> | null {
    return this.attributes as TSMap<string, string> | null;
  }

  /**
   * Assigns attributes to the current element.
   *
   * @param {TSMap<string, string>} attributes
   * @memberof GraphElementImpl
   */
  public setAttributes(attributes: TSMap<string, string>) {
    this.attributes = attributes || this.attributes;
  }

  /**
   * Returns the image icon assigned to the current element.
   *
   * @returns {string}
   * @memberof GraphElementImpl
   */
  public getImageIcon(): string {
    return this.image as string;
  }

  /**
   * Returns the image icon of current element.
   *
   * @param {string} image
   * @memberof GraphElementImpl
   */
  public setImageIcon(image: string): void {
    this.image = image;
  }

  /**
   * Serialize GraphElement as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphElementImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
