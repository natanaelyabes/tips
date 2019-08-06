import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';
import { Component } from 'vue-property-decorator';

@Component
export default class StopNodeModalMixin extends BaseComponent {

  // Parent stop
  public parentStopLabel: string = '';
  public parentStopReport: boolean = false;

  /*
    Stop Node functions
    - Send changes from parent to child
    - Retrieve changes from child to parent
  */
  public changeStopLabel(newVal: string): void {
    this.parentStopLabel = newVal;
  }

  public changeStopReport(newVal: boolean): void {
    this.parentStopReport = newVal;
  }

  /* Stop updated from Child */
  public changeStopLabelFromChild(e: any, graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStopLabel = e;
    console.log(currentSelectedElement);
    currentSelectedElement.setLabel(this.parentStopLabel);
    graph.getPages() !
      .get(activePage.getId() as string) !.getNodes() !
      .set(currentSelectedElement.getId() as string, currentSelectedElement as GraphNode);
    callback();
  }

  public changeStopReportFromChild(e: any, graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStopReport = e;
    currentSelectedElement.setReportStatistics(this.parentStopReport);
    graph.getPages() !
      .get(activePage.getId() as string) !.getNodes() !
      .set(currentSelectedElement.getId() as string, currentSelectedElement as GraphNode);
    callback();
  }
}
