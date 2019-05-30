import { JointGraphNodeImpl } from './JointGraphNodeImpl';
import { JointGraphElementIsPaintable } from './../interfaces/JointGraphElementIsPaintable';
import { GraphConnectorImpl } from '@/iochord/chdsr/common/graph/classes/GraphConnectorImpl';
import { JointGraphElementHasMarkup } from '../interfaces/JointGraphElementHasMarkup';
import * as joint from 'jointjs';

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

  public render(graph: joint.dia.Graph): void {
    const elementTypeKey = 'elementType';
    const link = joint.dia.Link.define('chdsr.' + this[elementTypeKey], {
      attrs: this.attr,
    });

    this.connector = new link();

    const source = graph.getElements().find((value) => {
      return value.attributes.nodeId === (this.getSource() as JointGraphNodeImpl).getId();
    }) as joint.dia.Element;
    const target = graph.getElements().find((value) => {
      return value.attributes.nodeId === (this.getTarget() as JointGraphNodeImpl).getId();
    }) as joint.dia.Element;

    this.connector.source(source);
    this.connector.target(target);
    this.connector.addTo(graph);
  }
}
