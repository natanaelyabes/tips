import { JointGraphElementIsPaintable } from '../interfaces/JointGraphElementIsPaintable';
import { JointGraphElementHasSize } from '../interfaces/JointGraphElementHasSize';
import { JointGraphElementHasMarkup } from '../interfaces/JointGraphElementHasMarkup';
import { GraphDataImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphDataImpl';
import { JointGraphElementHasPosition } from '../interfaces/JointGraphElementHasPosition';
import * as joint from 'jointjs';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
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

  public setPosition(position: { x: number; y: number; }): void {
    this.position = position;
  }

  public getSize(): { width: number; height: number; } | null {
    return this.size;
  }

  public setSize(size: { width: number; height: number; }): void {
    this.size = size;
  }

  public render(graph: joint.dia.Graph): void {
    this.data.attr(this.attr as joint.dia.Cell.Selectors);
    this.data.resize(this.size!.width, this.size!.height);
    this.data.position(this.position!.x, this.position!.y);
    this.data.addTo(graph);
  }
}
