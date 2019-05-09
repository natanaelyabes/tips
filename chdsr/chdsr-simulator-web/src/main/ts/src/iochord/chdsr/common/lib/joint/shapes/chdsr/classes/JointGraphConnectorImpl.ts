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

  public fn_joint_graph_element_get_markup(): string | null {
    return this.markup;
  }

  public fn_joint_graph_element_set_markup(markup: string): void {
    this.markup = markup;
  }

  public fn_joint_graph_element_get_attr(): joint.dia.Cell.Selectors | null {
    return this.attr;
  }

  public fn_joint_graph_element_set_attr(attr: joint.dia.Cell.Selectors): void {
    this.attr = attr;
  }

  public fn_joint_graph_element_render(graph: joint.dia.Graph): void {
    const link = joint.dia.Link.define('chdsr.' + this['elementType'], {
      attrs: this.attr,
    });

    this.connector = new link();
    const source = graph.getElements().find((value) => {
      return value.attributes.nodeId === (this.fn_graph_connector_get_source() as JointGraphNodeImpl).fn_graph_element_get_id();
    }) as joint.dia.Element;
    const target = graph.getElements().find((value) => {
      return value.attributes.nodeId === (this.fn_graph_connector_get_target() as JointGraphNodeImpl).fn_graph_element_get_id();
    }) as joint.dia.Element;

    this.connector.source(source);
    this.connector.target(target);
    this.connector.addTo(graph);
  }
}
