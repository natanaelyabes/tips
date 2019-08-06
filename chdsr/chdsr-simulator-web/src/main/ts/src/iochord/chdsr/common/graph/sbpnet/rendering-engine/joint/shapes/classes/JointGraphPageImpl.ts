import { GraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphPageImpl';
import * as joint from 'jointjs';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class JointGraphPageImpl extends GraphPageImpl {
  private graph: joint.dia.Graph = new joint.dia.Graph();
  private paper: joint.dia.Paper = new joint.dia.Paper({} as joint.dia.Paper.Options);
  private minimap: joint.dia.Paper = new joint.dia.Paper({} as joint.dia.Paper.Options);

  constructor();
  constructor(graph: joint.dia.Graph, paper: joint.dia.Paper, minimap: joint.dia.Paper);
  constructor(graph?: joint.dia.Graph, paper?: joint.dia.Paper, minimap?: joint.dia.Paper) {
    super();
    this.graph = graph || new joint.dia.Graph();
    this.paper = paper || new joint.dia.Paper({} as joint.dia.Paper.Options);
    this.minimap = minimap || new joint.dia.Paper({} as joint.dia.Paper.Options);
  }

  public getGraph(): joint.dia.Graph {
    return this.graph;
  }

  public setGraph(graph: joint.dia.Graph): void {
    this.graph = graph;
  }

  public getPaper(): joint.dia.Paper {
    return this.paper;
  }

  public setPaper(paper: joint.dia.Paper): void {
    this.paper = paper;
  }

  public getMinimap(): joint.dia.Paper {
    return this.minimap;
  }

  public setMinimap(minimap: joint.dia.Paper): void {
    this.minimap = minimap;
  }
}
