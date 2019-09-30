import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';
import { GraphStartEventNodeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphStartEventNodeImpl';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import { GraphDataGenerator } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphDataGenerator';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';

import { TSMap } from 'typescript-map';

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

  /* Start updated from Child */
  public changeStartLabelFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
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

  public changeStartGeneratorFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentStartGenerator = e;

    // Get data based on selection value e
    const data = (activePage.getData() as TSMap<string, GraphData>).get(this.parentStartGenerator);

    // Set generator for the start node
    const startNode = (currentSelectedElement as GraphStartEventNodeImpl);
    startNode.setGenerator(data as GraphDataGenerator);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: startNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(startNode.getId() as string, GraphStartEventNodeImpl.deserialize(startNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }
}
