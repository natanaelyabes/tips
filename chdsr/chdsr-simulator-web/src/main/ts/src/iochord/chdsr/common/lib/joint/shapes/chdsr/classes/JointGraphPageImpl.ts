import { GraphPageImpl } from '@/iochord/chdsr/common/graph/classes/GraphPageImpl';
import * as joint from 'jointjs';

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

  public fn_joint_graph_page_get_graph(): joint.dia.Graph {
    return this.graph;
  }

  public fn_joint_graph_page_set_graph(graph: joint.dia.Graph): void {
    this.graph = graph;
  }

  public fn_joint_graph_page_get_paper(): joint.dia.Paper {
    return this.paper;
  }

  public fn_joint_graph_page_set_paper(paper: joint.dia.Paper): void {
    this.paper = paper;
  }

  public fn_joint_graph_page_get_minimap(): joint.dia.Paper {
    return this.minimap;
  }

  public fn_joint_graph_page_set_minimap(minimap: joint.dia.Paper): void {
    this.minimap = minimap;
  }
}
