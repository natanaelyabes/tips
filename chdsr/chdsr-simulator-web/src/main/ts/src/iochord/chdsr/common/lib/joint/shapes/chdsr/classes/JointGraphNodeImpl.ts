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
  private imageIcon: string = '';

  constructor();
  constructor(markup: string, attr: joint.dia.Cell.Selectors, position: { x: number, y: number }, size: { width: number, height: number });
  constructor(markup?: string, attr?: joint.dia.Cell.Selectors, position?: { x: number, y: number }, size?: { width: number, height: number }) {
    super();
    this.markup = markup || null;
    this.attr = attr || null;
    this.position = position || null;
    this.size = size || null;
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

  public getPosition(): { x: number; y: number; } | null {
    return this.position;
  }

  public setPosition(position: { x: number, y: number }): void {
    this.position = position;
  }

  public getSize(): { width: number, height: number } | null {
    return this.size;
  }

  public setSize(size: { width: number, height: number }): void {
    this.size = size;
  }

  public getImageIcon(): string {
    return this.imageIcon;
  }

  public setImageIcon(imageIcon: string): void {
    this.imageIcon = imageIcon;
  }

  public render(graph: joint.dia.Graph): void {

    this.node = new joint.shapes.standard.Image();
    this.node.attr({
      label: {
        text: this.getLabel() !== undefined || null ? this.getLabel() as string : '',
      },
      image: {
        xlinkHref: this.imageIcon,
      },
    });
    this.node.attributes.nodeId = this.getId();
    this.node.attributes.type = this.getType();
    this.node.resize(this.size!.width, this.size!.height);
    this.node.position(this.position!.x, this.position!.y);
    this.node.addTo(graph);
  }
}
