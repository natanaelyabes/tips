import { JointGraphElementHasSize } from './../interfaces/JointGraphElementHasSize';
import { JointGraphElementHasMarkup } from '../interfaces/JointGraphElementHasMarkup';
import { GraphNodeImpl } from '@/iochord/chdsr/common/graph/classes/GraphNodeImpl';
import * as joint from 'jointjs';
import { JointGraphElementHasPosition } from '../interfaces/JointGraphElementHasPosition';
import { JointGraphElementIsPaintable } from '../interfaces/JointGraphElementIsPaintable';

export class JointGraphNodeImpl extends GraphNodeImpl implements JointGraphElementHasMarkup, JointGraphElementHasPosition, JointGraphElementHasSize, JointGraphElementIsPaintable {
  private markup: string | null;
  private attr: joint.dia.Cell.Selectors | null;
  private position: { x: number, y: number } | null;
  private size: { width: number, height: number } | null;
  private node: joint.shapes.basic.Generic = new joint.shapes.basic.Generic();

  constructor();
  constructor(markup: string, attr: joint.dia.Cell.Selectors, position: { x: number, y: number }, size: { width: number, height: number });
  constructor(markup?: string, attr?: joint.dia.Cell.Selectors, position?: { x: number, y: number }, size?: { width: number, height: number }) {
    super();
    this.markup = markup || null;
    this.attr = attr || null;
    this.position = position || null;
    this.size = size || null;
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

  public fn_joint_graph_element_get_position(): { x: number; y: number; } | null {
    return this.position;
  }

  public fn_joint_graph_element_set_position(position: { x: number, y: number }): void {
    this.position = position;
  }

  public fn_joint_graph_element_get_size(): { width: number, height: number } | null {
    return this.size;
  }

  public fn_joint_graph_element_set_size(size: { width: number, height: number }): void {
    this.size = size;
  }

  public fn_joint_graph_element_render(graph: joint.dia.Graph): void {
    const shapes = joint.dia.Element.define('chdsr.' + this['elementType'], {
      attrs: this.attr,
    }, {
      markup: this.fn_joint_graph_element_get_markup(),
    });

    this.node = new shapes();
    this.node.attr(this.attr as joint.dia.Cell.Selectors);
    this.node.attr({
      '.label': {
        text: this.fn_graph_element_get_label() as string,
      },
    });
    this.node.resize(this.size!.width, this.size!.height);
    this.node.position(this.position!.x, this.position!.y);
    this.node.addTo(graph);
  }

  public fn_joint_graph_element_get_joint_node(): joint.shapes.basic.Generic {
    return this.node;
  }

  public fn_joint_graph_element_set_joint_node(node: joint.shapes.basic.Generic): void {
    this.node = node;
  }
}
