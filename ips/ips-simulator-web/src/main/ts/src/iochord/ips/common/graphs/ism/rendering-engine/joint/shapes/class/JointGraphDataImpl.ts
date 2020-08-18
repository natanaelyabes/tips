import { JointGraphElementIsPaintable } from '../interfaces/JointGraphElementIsPaintable';
import { JointGraphElementHasSize } from '../interfaces/JointGraphElementHasSize';
import { JointGraphElementHasMarkup } from '../interfaces/JointGraphElementHasMarkup';
import { GraphDataImpl } from '@/iochord/ips/common/graphs/ism/class/GraphDataImpl';
import { JointGraphElementHasPosition } from '../interfaces/JointGraphElementHasPosition';
import * as joint from 'jointjs';

/**
 * Implementation of graph connector for Joint.js renderer.
 *
 * @export
 * @class JointGraphConnectorImpl
 * @extends {GraphConnectorImpl}
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
export class JointGraphDataImpl extends GraphDataImpl implements JointGraphElementHasMarkup, JointGraphElementHasSize, JointGraphElementHasPosition, JointGraphElementIsPaintable {

  /**
   * The markup for current graph data node.
   *
   * @private
   * @type {(string | null)}
   * @memberof JointGraphDataImpl
   */
  private markup: string | null;


  /**
   * The attribute for graph data node object.
   *
   * @private
   * @type {(joint.dia.Cell.Selectors | null)}
   * @memberof JointGraphDataImpl
   */
  private attr: joint.dia.Cell.Selectors | null;

  /**
   * The position of graph data node placed within canvas.
   *
   * @private
   * @type {({ x: number, y: number } | null)}
   * @memberof JointGraphDataImpl
   */
  private position: { x: number, y: number } | null;

  /**
   * The size of graph data node placed within canvas.
   *
   * @private
   * @type {({ width: number, height: number } | null)}
   * @memberof JointGraphDataImpl
   */
  private size: { width: number, height: number } | null;

  /**
   * The shape for graph data node.
   *
   * @private
   * @type {joint.shapes.basic.Generic}
   * @memberof JointGraphDataImpl
   */
  private data: joint.shapes.basic.Generic = new joint.shapes.basic.Generic();

  /**
   * The URI location for the image icon of graph data node.
   *
   * @private
   * @type {string}
   * @memberof JointGraphDataImpl
   */
  private imageIcon: string = '';

  /**
   * Creates an instance of JointGraphDataImpl.
   *
   * @memberof JointGraphDataImpl
   */
  constructor();
  constructor(markup: string, attr: joint.dia.Cell.Selectors, position: { x: number, y: number }, size: { width: number, height: number });
  constructor(markup?: string, attr?: joint.dia.Cell.Selectors, position?: { x: number, y: number }, size?: { width: number, height: number }) {
    super();
    this.markup = markup || null;
    this.attr = attr || null;
    this.position = position || null;
    this.size = size || null;
  }

  /**
   * Returns markup of current graph data node.
   *
   * @returns {(string | null)}
   * @memberof JointGraphDataImpl
   */
  public getMarkup(): string | null {
    return this.markup;
  }

  /**
   * Assigns markup to the current graph data node.
   *
   * @param {string} markup
   * @memberof JointGraphDataImpl
   */
  public setMarkup(markup: string): void {
    this.markup = markup;
  }

  /**
   * Returns the attribute of current graph data node.
   *
   * @returns {(joint.dia.Cell.Selectors | null)}
   * @memberof JointGraphDataImpl
   */
  public getAttr(): joint.dia.Cell.Selectors | null {
    return this.attr;
  }

  /**
   * Assigns attribute of the current graph data node.
   *
   * @param {joint.dia.Cell.Selectors} attr
   * @memberof JointGraphDataImpl
   */
  public setAttr(attr: joint.dia.Cell.Selectors): void {
    this.attr = attr;
  }

  /**
   * Returns the position of current graph data node.
   *
   * @returns {({ x: number; y: number; } | null)}
   * @memberof JointGraphDataImpl
   */
  public getPosition(): { x: number; y: number; } | null {
    return this.position;
  }

  /**
   * Assigns the position of current graph data node.
   *
   * @param {{ x: number; y: number; }} position
   * @memberof JointGraphDataImpl
   */
  public setPosition(position: { x: number; y: number; }): void {
    this.position = position;
  }

  /**
   * Returns the size of current graph data node.
   *
   * @returns {({ width: number; height: number; } | null)}
   * @memberof JointGraphDataImpl
   */
  public getSize(): { width: number; height: number; } | null {
    return this.size;
  }

  /**
   * Assigns the size of current graph data node.
   *
   * @param {{ width: number; height: number; }} size
   * @memberof JointGraphDataImpl
   */
  public setSize(size: { width: number; height: number; }): void {
    this.size = size;
  }

  /**
   * Returns the shape for current graph data node.
   *
   * @returns {joint.shapes.standard.BorderedImage}
   * @memberof JointGraphDataImpl
   */
  public getData(): joint.shapes.standard.BorderedImage {
    return this.data;
  }

  /**
   * Assigns the shape for current graph data node.
   *
   * @param {joint.shapes.standard.BorderedImage} data
   * @memberof JointGraphDataImpl
   */
  public setData(data: joint.shapes.standard.BorderedImage) {
    this.data = data;
  }

  /**
   * Returns the image icon for current graph data node.
   *
   * @returns {string}
   * @memberof JointGraphDataImpl
   */
  public getImageIcon(): string {
    return this.imageIcon;
  }

  /**
   * Assigns the image icon for current graph data node.
   *
   * @param {string} imageIcon
   * @memberof JointGraphDataImpl
   */
  public setImageIcon(imageIcon: string): void {
    this.imageIcon = imageIcon;
  }

  /**
   * Renders the current graph data node to the canvas.
   *
   * @param {joint.dia.Graph} graph
   * @memberof JointGraphDataImpl
   */
  public render(graph: joint.dia.Graph): void {
    if (!graph.getCell(this.data.id)) {
      this.data = new joint.shapes.standard.BorderedImage();
      this.data.attr({
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

      this.data.attr({...this.getAttr()});
      this.data.attributes.dataId = this.getId();
      this.data.attributes.type = this.getType();
      this.data.attributes.category = 'data';
      this.data.addTo(graph);
    }

    this.data.resize(this.size!.width, this.size!.height);
    this.data.position(this.position!.x, this.position!.y);
  }
}
