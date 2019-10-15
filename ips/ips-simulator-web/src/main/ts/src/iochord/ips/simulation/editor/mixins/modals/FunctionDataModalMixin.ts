import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { Component } from 'vue-property-decorator';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

@Component<FunctionDataModalMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class FunctionDataModalMixin extends BaseComponent {
  public parentFunctionDataLabel: string = '';

  public changeFunctionDataLabel(newVal: string): void {
    this.parentFunctionDataLabel = newVal;
  }

  public changeFunctionDataLabelFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentFunctionDataLabel = e;
    console.log(e);
  }
}
