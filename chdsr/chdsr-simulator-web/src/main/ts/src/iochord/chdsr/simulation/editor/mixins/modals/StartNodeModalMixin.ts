import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { Component } from 'vue-property-decorator';


@Component
export default class StartNodeModalMixin extends BaseComponent {

  // Parent start
  public parentStartLabel: string = '';
  public parentStartGenerator: string = '';

  /*
    Start Node functions
    - Send changes from parent to child
    - Retrieve changes from child to parent
  */
  public changeStartLabel(newVal: string): void {
    this.parentStartLabel = newVal;
  }

  public changeStartGenerator(newVal: string): void {
    this.parentStartGenerator = newVal;
  }

  public getParentStartLabel(): string {
    return this.parentStartLabel;
  }

  /* Start updated from Child */
  public changeStartLabelFromChild(e: any, graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStartLabel = e;
    currentSelectedElement.setLabel(this.parentStartLabel);
    graph.getPages()!
      .get(activePage.getId() as string)!.getNodes()!
      .set(currentSelectedElement.getId() as string, currentSelectedElement as GraphNode);
    callback();
  }

  public changeStartGeneratorFromChild(e: any, graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStartGenerator = e;
  }
}
