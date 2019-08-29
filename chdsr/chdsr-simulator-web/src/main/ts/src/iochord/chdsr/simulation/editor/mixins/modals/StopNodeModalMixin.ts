import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';
import { GraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphNodeImpl';
import { GraphStopEventNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/components/GraphStopEventNodeImpl';

const graphModule = getModule(GraphModule);

@Component<StopNodeModalMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
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
  public changeStopLabelFromChild(e: any, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStopLabel = e;

    // Change label of currentSelectedElement
    currentSelectedElement.setLabel(this.parentStopLabel);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: currentSelectedElement });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(currentSelectedElement.getId() as string, GraphStopEventNodeImpl.deserialize(currentSelectedElement) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeStopReportFromChild(e: any, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStopReport = e;

    // Change report statistics status of currentSelectedElement
    currentSelectedElement.setReportStatistics(this.parentStopReport);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: currentSelectedElement });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(currentSelectedElement.getId() as string, GraphStopEventNodeImpl.deserialize(currentSelectedElement) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }
}
