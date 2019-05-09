import { JointGraphElementIsPaintable } from './../interfaces/JointGraphElementIsPaintable';
import { JointGraphElementHasSize } from './../interfaces/JointGraphElementHasSize';
import { JointGraphElementHasMarkup } from '../interfaces/JointGraphElementHasMarkup';
import { GraphDataImpl } from '@/iochord/chdsr/common/graph/classes/GraphDataImpl';
import { JointGraphElementHasPosition } from '../interfaces/JointGraphElementHasPosition';
import * as joint from 'jointjs';

export class JointGraphDataImpl extends GraphDataImpl implements JointGraphElementHasMarkup, JointGraphElementHasSize, JointGraphElementHasPosition, JointGraphElementIsPaintable {
  private markup: string | null;
  private attr: joint.dia.Cell.Selectors | null;
  private position: { x: number, y: number } | null;
  private size: { width: number, height: number } | null;
  private data: joint.shapes.basic.Generic = new joint.shapes.basic.Generic();

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

  public fn_joint_graph_element_set_position(position: { x: number; y: number; }): void {
    this.position = position;
  }

  public fn_joint_graph_element_get_size(): { width: number; height: number; } | null {
    return this.size;
  }

  public fn_joint_graph_element_set_size(size: { width: number; height: number; }): void {
    this.size = size;
  }

  public fn_joint_graph_element_render(graph: joint.dia.Graph): void {
    this.data.attr(this.attr as joint.dia.Cell.Selectors);
    this.data.resize(this.size!.width, this.size!.height);
    this.data.position(this.position!.x, this.position!.y);
    this.data.addTo(graph);
  }

  public fn_joint_graph_element_get_joint_data(): joint.shapes.basic.Generic {
    return this.data;
  }

  public fn_joint_graph_element_set_joint_data(data: joint.shapes.basic.Generic): void {
    this.data = data;
  }
}
