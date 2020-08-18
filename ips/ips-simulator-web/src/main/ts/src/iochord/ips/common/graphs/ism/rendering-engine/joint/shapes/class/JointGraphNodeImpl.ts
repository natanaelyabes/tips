import { JointGraphElementHasSize } from '../interfaces/JointGraphElementHasSize';
import { JointGraphElementHasMarkup } from '../interfaces/JointGraphElementHasMarkup';
import { GraphNodeImpl } from '@/iochord/ips/common/graphs/ism/class/GraphNodeImpl';
import * as joint from 'jointjs';
import { JointGraphElementHasPosition } from '../interfaces/JointGraphElementHasPosition';
import { JointGraphElementIsPaintable } from '../interfaces/JointGraphElementIsPaintable';

/**
 * Implementation of graph connector for Joint.js renderer.
 *
 * @export
 * @class JointGraphNodeImpl
 * @extends {GraphNodeImpl}
 * @implements {JointGraphElementHasMarkup}
 * @implements {JointGraphElementHasPosition}
 * @implements {JointGraphElementHasSize}
 * @implements {JointGraphElementIsPaintable}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export class JointGraphNodeImpl extends GraphNodeImpl implements JointGraphElementHasMarkup, JointGraphElementHasPosition, JointGraphElementHasSize, JointGraphElementIsPaintable {

  /**
   * The markup for current graph node.
   *
   * @private
   * @type {(string | null)}
   * @memberof JointGraphNodeImpl
   */
  private markup: string | null;

  /**
   * Attribute for current graph node.
   *
   * @private
   * @type {(joint.dia.Cell.Selectors | null)}
   * @memberof JointGraphNodeImpl
   */
  private attr: joint.dia.Cell.Selectors | null;

  /**
   * Position of the graph node within canvas.
   *
   * @private
   * @type {({ x: number, y: number } | null)}
   * @memberof JointGraphNodeImpl
   */
  private position: { x: number, y: number } | null;

  /**
   * Size of the graph node within the canvas.
   *
   * @private
   * @type {({ width: number, height: number } | null)}
   * @memberof JointGraphNodeImpl
   */
  private size: { width: number, height: number } | null;

  /**
   * The shape of graph node within the canvas.
   *
   * @private
   * @type {joint.shapes.basic.Generic}
   * @memberof JointGraphNodeImpl
   */
  private node: joint.shapes.basic.Generic = new joint.shapes.basic.Generic();

  /**
   * The URI location for the image icon of graph data node.
   *
   * @private
   * @type {string}
   * @memberof JointGraphNodeImpl
   */
  private imageIcon: string = '';

  /**
   * Creates an instance of JointGraphNodeImpl.
   *
   * @memberof JointGraphNodeImpl
   */
  constructor();
  constructor(markup: string, attr: joint.dia.Cell.Selectors, position: { x: number, y: number }, size: { width: number, height: number, direction?: 'left' | 'top' | 'right' | 'bottom' | 'top-left' | 'top-right' | 'bottom-right' | 'bottom-left' | undefined });
  constructor(markup?: string, attr?: joint.dia.Cell.Selectors, position?: { x: number, y: number }, size?: { width: number, height: number, direction?: 'left' | 'top' | 'right' | 'bottom' | 'top-left' | 'top-right' | 'bottom-right' | 'bottom-left' | undefined }) {
    super();
    this.markup = markup || null;
    this.attr = attr || null;
    this.position = position || null;
    this.size = size || null;
  }

  /**
   * Returns the markup of current graph node.
   *
   * @returns {(string | null)}
   * @memberof JointGraphNodeImpl
   */
  public getMarkup(): string | null {
    return this.markup;
  }

  /**
   * Assigns markup to current graph node.
   *
   * @param {string} markup
   * @memberof JointGraphNodeImpl
   */
  public setMarkup(markup: string): void {
    this.markup = markup;
  }

  /**
   * Returns the attribute of current graph node.
   *
   * @returns {(joint.dia.Cell.Selectors | null)}
   * @memberof JointGraphNodeImpl
   */
  public getAttr(): joint.dia.Cell.Selectors | null {
    return this.attr;
  }

  /**
   * Assigns attribute to current graph node.
   *
   * @param {joint.dia.Cell.Selectors} attr
   * @memberof JointGraphNodeImpl
   */
  public setAttr(attr: joint.dia.Cell.Selectors): void {
    this.attr = attr;
  }

  /**
   * Returns the position of current graph node within the canvas.
   *
   * @returns {({ x: number; y: number; } | null)}
   * @memberof JointGraphNodeImpl
   */
  public getPosition(): { x: number; y: number; } | null {
    return this.position;
  }

  /**
   * Assigns the position of current graph within the canvas.
   *
   * @param {{ x: number, y: number }} position
   * @memberof JointGraphNodeImpl
   */
  public setPosition(position: { x: number, y: number }): void {
    this.position = position;
  }

  /**
   * Returns the size of current graph node.
   *
   * @returns {({ width: number, height: number } | null)}
   * @memberof JointGraphNodeImpl
   */
  public getSize(): { width: number, height: number } | null {
    return this.size;
  }

  /**
   * Assigns the size of current graph node.
   *
   * @param {({ width: number, height: number, direction?: 'left' | 'top' | 'right' | 'bottom' | 'top-left' | 'top-right' | 'bottom-right' | 'bottom-left' | undefined })} size
   * @memberof JointGraphNodeImpl
   */
  public setSize(size: { width: number, height: number, direction?: 'left' | 'top' | 'right' | 'bottom' | 'top-left' | 'top-right' | 'bottom-right' | 'bottom-left' | undefined }): void {
    this.size = size;
  }

  /**
   * Returns the image icon for current graph node.
   *
   * @returns {string}
   * @memberof JointGraphNodeImpl
   */
  public getImageIcon(): string {
    return this.imageIcon;
  }

  /**
   * Assigns the image icon URI for the current graph node.
   *
   * @param {string} imageIcon
   * @memberof JointGraphNodeImpl
   */
  public setImageIcon(imageIcon: string): void {
    this.imageIcon = imageIcon;
  }

  /**
   * Returns the node for current graph node.
   *
   * @returns {joint.shapes.standard.BorderedImage}
   * @memberof JointGraphNodeImpl
   */
  public getNode(): joint.shapes.standard.BorderedImage {
    return this.node;
  }

  /**
   * Assigns the node of current graph node.
   *
   * @param {joint.shapes.standard.BorderedImage} node
   * @memberof JointGraphNodeImpl
   */
  public setNode(node: joint.shapes.standard.BorderedImage) {
    this.node = node;
  }

  /**
   * Renders the graph node to the canvas.
   *
   * @param {joint.dia.Graph} graph
   * @memberof JointGraphNodeImpl
   */
  public render(graph: joint.dia.Graph): void {

    if (!graph.getCell(this.node.id)) {
      this.node = new joint.shapes.standard.BorderedImage();
      this.node.attr({
        label: {
          text: this.getLabel() !== undefined || null ? this.getLabel() as string : '',
        },
        image: {
          xlinkHref: this.imageIcon,
        },
        border: {
          strokeWidth: 0,
          rx: 5,
        },
      });

      this.node.attr({...this.getAttr()});
      this.node.attributes.nodeId = this.getId();
      this.node.attributes.type = this.getType();
      this.node.attributes.category = 'node';
      this.node.addTo(graph);
    }

    this.node.resize(this.size!.width, this.size!.height);
    this.node.position(this.position!.x, this.position!.y);
  }
}
