import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { Component } from 'vue-property-decorator';
import { BRANCH_GATE, BRANCH_TYPE, BRANCH_RULE } from '@/iochord/ips/common/graph/ism/enums/BRANCH';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';
import { GraphBranchNodeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphBranchNodeImpl';
import { GraphBranchNode } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphBranchNode';

const graphModule = getModule(GraphModule);

@Component<BranchNodeModalMixins>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class BranchNodeModalMixins extends BaseComponent {
  // Parent branch
  public parentBranchLabel: string = '';
  public parentBranchSelectedGate: BRANCH_GATE = BRANCH_GATE.AND;
  public parentBranchSelectedType: BRANCH_TYPE = BRANCH_TYPE.JOIN;
  public parentBranchSelectedRule: BRANCH_RULE = BRANCH_RULE.CONDITION;

  /*
    Branch Node functions
    - Send changes from parent to child
    - Retrieve changes from child to parent
  */
  public changeBranchLabel(newVal: string): void {
    this.parentBranchLabel = newVal;
  }

  public changeBranchSelectedGate(newVal: BRANCH_GATE): void {
    this.parentBranchSelectedGate = newVal;
  }

  public changeBranchSelectedType(newVal: BRANCH_TYPE): void {
    this.parentBranchSelectedType = newVal;
  }

  public changeBranchSelectedRule(newVal: BRANCH_RULE): void {
    this.parentBranchSelectedRule = newVal;
  }

  /* Branch updated from Child */
  public changeBranchLabelFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentBranchLabel = e;

    // Change label of currentSelectedElement
    currentSelectedElement.setLabel(this.parentBranchLabel);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: currentSelectedElement });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(currentSelectedElement.getId() as string, GraphBranchNodeImpl.deserialize(currentSelectedElement) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeBranchSelectedGateFromChild(e: BRANCH_GATE, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentBranchSelectedGate = e;

    // Set branch node
    const branchNode = (currentSelectedElement as GraphBranchNode);
    branchNode.setGate(this.parentBranchSelectedGate);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: branchNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(branchNode.getId() as string, GraphBranchNodeImpl.deserialize(branchNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeBranchSelectedTypeFromChild(e: BRANCH_TYPE, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentBranchSelectedType = e;

    // Set branch node
    const branchNode = (currentSelectedElement as GraphBranchNode);
    branchNode.setBranchType(this.parentBranchSelectedType);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: branchNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(branchNode.getId() as string, GraphBranchNodeImpl.deserialize(branchNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeBranchSelectedRuleFromChild(e: BRANCH_RULE, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentBranchSelectedRule = e;

    // Set branch node
    const branchNode = (currentSelectedElement as GraphBranchNode);
    branchNode.setRule(this.parentBranchSelectedRule);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: branchNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(branchNode.getId() as string, GraphBranchNodeImpl.deserialize(branchNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }
}
