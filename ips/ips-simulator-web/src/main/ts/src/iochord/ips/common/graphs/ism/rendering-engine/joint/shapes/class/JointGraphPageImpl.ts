import { GraphPageImpl } from '@/iochord/ips/common/graphs/ism/class/GraphPageImpl';
import * as joint from 'jointjs';

/**
 * Implementation of graph page for Joint.js renderer.
 *
 * @export
 * @class JointGraphPageImpl
 * @extends {GraphPageImpl}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class JointGraphPageImpl extends GraphPageImpl {

  /**
   * The graph of current page.
   *
   * @private
   * @type {joint.dia.Graph}
   * @memberof JointGraphPageImpl
   */
  private graph: joint.dia.Graph = new joint.dia.Graph();

  /**
   * Paper to draw graph on the canvas.
   *
   * @private
   * @type {joint.dia.Paper}
   * @memberof JointGraphPageImpl
   */
  private paper: joint.dia.Paper = new joint.dia.Paper({} as joint.dia.Paper.Options);

  /**
   * Paper to draw graph on the minimap.
   *
   * @private
   * @type {joint.dia.Paper}
   * @memberof JointGraphPageImpl
   */
  private minimap: joint.dia.Paper = new joint.dia.Paper({} as joint.dia.Paper.Options);

  /**
   * Creates an instance of JointGraphPageImpl.
   *
   * @memberof JointGraphPageImpl
   */
  constructor();
  constructor(graph: joint.dia.Graph, paper: joint.dia.Paper, minimap: joint.dia.Paper);
  constructor(graph?: joint.dia.Graph, paper?: joint.dia.Paper, minimap?: joint.dia.Paper) {
    super();
    this.graph = graph || new joint.dia.Graph();
    this.paper = paper || new joint.dia.Paper({} as joint.dia.Paper.Options);
    this.minimap = minimap || new joint.dia.Paper({} as joint.dia.Paper.Options);
  }

  /**
   * Returns the graph of current page.
   *
   * @returns {joint.dia.Graph}
   * @memberof JointGraphPageImpl
   */
  public getGraph(): joint.dia.Graph {
    return this.graph;
  }

  /**
   * Assigns the graph of current page.
   *
   * @param {joint.dia.Graph} graph
   * @memberof JointGraphPageImpl
   */
  public setGraph(graph: joint.dia.Graph): void {
    this.graph = graph;
  }

  /**
   * Returns the paper.
   *
   * @returns {joint.dia.Paper}
   * @memberof JointGraphPageImpl
   */
  public getPaper(): joint.dia.Paper {
    return this.paper;
  }

  /**
   * Assigns the paper where the graph need to be drawn.
   *
   * @param {joint.dia.Paper} paper
   * @memberof JointGraphPageImpl
   */
  public setPaper(paper: joint.dia.Paper): void {
    this.paper = paper;
  }

  /**
   * Returns the minimap.
   *
   * @returns {joint.dia.Paper}
   * @memberof JointGraphPageImpl
   */
  public getMinimap(): joint.dia.Paper {
    return this.minimap;
  }

  /**
   * Assigns the minimap where the graph need to be drawn.
   *
   * @param {joint.dia.Paper} minimap
   * @memberof JointGraphPageImpl
   */
  public setMinimap(minimap: joint.dia.Paper): void {
    this.minimap = minimap;
  }
}
