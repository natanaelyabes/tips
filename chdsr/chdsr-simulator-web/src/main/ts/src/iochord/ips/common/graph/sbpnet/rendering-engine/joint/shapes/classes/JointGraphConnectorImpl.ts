import { JointGraphNodeImpl } from './JointGraphNodeImpl';
import { JointGraphElementIsPaintable } from '@/iochord/ips/common/graph/sbpnet/rendering-engine/joint/shapes/interfaces/JointGraphElementIsPaintable';
import { GraphConnectorImpl } from '@/iochord/ips/common/graph/sbpnet/classes/GraphConnectorImpl';
import { JointGraphElementHasMarkup } from '@/iochord/ips/common/graph/sbpnet/rendering-engine/joint/shapes/interfaces/JointGraphElementHasMarkup';
import * as joint from 'jointjs';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class JointGraphConnectorImpl extends GraphConnectorImpl implements JointGraphElementHasMarkup, JointGraphElementIsPaintable {
  private markup: string | null;
  private attr: joint.dia.Cell.Selectors | null;
  private connector: joint.dia.Link = new joint.dia.Link();

  constructor();
  constructor(markup: string, attr: joint.dia.Cell.Selectors);
  constructor(markup?: string, attr?: joint.dia.Cell.Selectors) {
    super();
    this.markup = markup || null;
    this.attr = attr || null;
  }

  public getMarkup(): string | null {
    return this.markup;
  }

  public setMarkup(markup: string): void {
    this.markup = markup;
  }

  public getAttr(): joint.dia.Cell.Selectors | null {
    return this.attr;
  }

  public setAttr(attr: joint.dia.Cell.Selectors): void {
    this.attr = attr;
  }

  public getConnector(): joint.dia.Link {
    return this.connector;
  }

  public setConnector(connector: joint.dia.Link) {
    this.connector = connector;
  }

  // TODO: Need to be fixed (20190815)
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
        return value.attributes.nodeId === (this.getSource() as JointGraphNodeImpl).getId();
      }) as joint.dia.Element;

      this.connector.source(source);
    }

    let target: joint.dia.Element | joint.g.Point;

    if (this.getTarget()) {
      target = graph.getElements().find((value) => {
        return value.attributes.nodeId === (this.getTarget() as JointGraphNodeImpl).getId();
      }) as joint.dia.Element;
      this.connector.target(target);
    }

    if (!this.getTarget() && position) {
      this.connector.target(position as joint.g.Point);
    }

    this.connector.attributes.arcId = this.getId();
    this.connector.addTo(graph);
  }
}
