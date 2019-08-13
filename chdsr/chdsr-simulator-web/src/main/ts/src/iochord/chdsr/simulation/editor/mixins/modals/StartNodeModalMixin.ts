import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import { GraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphNodeImpl';
import { GraphStartEventNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/components/GraphStartEventNodeImpl';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';


const graphModule = getModule(GraphModule);

@Component<StartNodeModalMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
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
  public changeStartLabelFromChild(e: any, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStartLabel = e;

    // Change label of currentSelectedElement
    currentSelectedElement.setLabel(this.parentStartLabel);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: currentSelectedElement });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(currentSelectedElement.getId() as string, GraphStartEventNodeImpl.deserialize(currentSelectedElement) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeStartGeneratorFromChild(e: any, graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStartGenerator = e;
  }
}
