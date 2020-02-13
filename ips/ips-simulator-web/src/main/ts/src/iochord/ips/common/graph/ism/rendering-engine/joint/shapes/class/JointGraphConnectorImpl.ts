import { JointGraphElementIsPaintable } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/interfaces/JointGraphElementIsPaintable';
import { GraphConnectorImpl } from '@/iochord/ips/common/graph/ism/class/GraphConnectorImpl';
import { JointGraphElementHasMarkup } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/interfaces/JointGraphElementHasMarkup';
import * as joint from 'jointjs';

/**
 * Implementation of graph connector for Joint.js renderer.
 *
 * @export
 * @class JointGraphConnectorImpl
 * @extends {GraphConnectorImpl}
 * @implements {JointGraphElementHasMarkup}
 * @implements {JointGraphElementIsPaintable}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class JointGraphConnectorImpl extends GraphConnectorImpl implements JointGraphElementHasMarkup, JointGraphElementIsPaintable {

  /**
   * The markup for current graph connector element.
   *
   * @private
   * @type {(string | null)}
   * @memberof JointGraphConnectorImpl
   */
  private markup: string | null;

  /**
   * The attribute for graph connector element.
   *
   * @private
   * @type {(joint.dia.Cell.Selectors | null)}
   * @memberof JointGraphConnectorImpl
   */
  private attr: joint.dia.Cell.Selectors | null;

  /**
   * The current connector object.
   *
   * @private
   * @type {joint.dia.Link}
   * @memberof JointGraphConnectorImpl
   */
  private connector: joint.dia.Link = new joint.dia.Link();


  /**
   * Creates an instance of JointGraphConnectorImpl.
   *
   * @memberof JointGraphConnectorImpl
   */
  constructor();
  constructor(markup: string, attr: joint.dia.Cell.Selectors);
  constructor(markup?: string, attr?: joint.dia.Cell.Selectors) {
    super();
    this.markup = markup || null;
    this.attr = attr || null;
  }

  /**
   * Returns markup of graph connector.
   *
   * @returns {(string | null)}
   * @memberof JointGraphConnectorImpl
   */
  public getMarkup(): string | null {
    return this.markup;
  }

  /**
   * Assigns markup to graph connector.
   *
   * @param {string} markup
   * @memberof JointGraphConnectorImpl
   */
  public setMarkup(markup: string): void {
    this.markup = markup;
  }

  /**
   * Returns the attribute of graph connector.
   *
   * @returns {(joint.dia.Cell.Selectors | null)}
   * @memberof JointGraphConnectorImpl
   */
  public getAttr(): joint.dia.Cell.Selectors | null {
    return this.attr;
  }

  /**
   * Assigns the attribute for graph connector.
   *
   * @param {joint.dia.Cell.Selectors} attr
   * @memberof JointGraphConnectorImpl
   */
  public setAttr(attr: joint.dia.Cell.Selectors): void {
    this.attr = attr;
  }

  /**
   * Returns the current connector object.
   *
   * @returns {joint.dia.Link}
   * @memberof JointGraphConnectorImpl
   */
  public getConnector(): joint.dia.Link {
    return this.connector;
  }

  /**
   * Assigns the connector of current object.
   *
   * @param {joint.dia.Link} connector
   * @memberof JointGraphConnectorImpl
   */
  public setConnector(connector: joint.dia.Link) {
    this.connector = connector;
  }

  /**
   * Render connector to canvas.
   *
   * @param {joint.dia.Graph} graph
   * @param {joint.g.Point} [position]
   * @memberof JointGraphConnectorImpl
   */
  public render(graph: joint.dia.Graph, position?: joint.g.Point): void {
    const elementTypeKey = 'elementType';

    const exists = graph.getLinks().filter((value: joint.dia.Link) => {
      if (value.id === this.connector.id) { return true; }
    });

    if (exists.length === 0) {
      const link = joint.dia.Link.define('ips.' + this[elementTypeKey], {
        attrs: this.attr,
      });

      this.connector = new link();

      const source = graph.getElements().find((value) => {
        return value.attributes.nodeId === this.getSourceRef();
      }) as joint.dia.Element;

      this.connector.source(source);
    }

    let target: joint.dia.Element | joint.g.Point;

    if (this.getTargetRef()) {
      target = graph.getElements().find((value) => {
        return value.attributes.nodeId === this.getTargetRef();
      }) as joint.dia.Element;
      this.connector.target(target);
    }

    if (!this.getTargetRef() && position) {
      this.connector.target(position as joint.g.Point);
    }

    this.connector.attributes.connectorId = this.getId();
    this.connector.addTo(graph);
  }
}
