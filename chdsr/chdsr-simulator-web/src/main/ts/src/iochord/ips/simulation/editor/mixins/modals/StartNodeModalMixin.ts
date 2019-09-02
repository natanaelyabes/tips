import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/classes/GraphNodeImpl';
import { GraphStartEventNodeImpl } from '@/iochord/ips/common/graph/ism/classes/components/GraphStartEventNodeImpl';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';


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

  public changeStartGeneratorFromChild(e: any, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStartGenerator = e;
  }
}
