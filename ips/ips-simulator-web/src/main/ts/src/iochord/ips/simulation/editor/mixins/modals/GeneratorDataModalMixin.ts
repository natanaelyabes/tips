import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { Component } from 'vue-property-decorator';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

@Component<GeneratorDataModalMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class GeneratorDataModalMixin extends BaseComponent {
  public parentGeneratorDataLabel: string = '';

  public changeGeneratorDataLabel(newVal: string): void {
    this.parentGeneratorDataLabel = newVal;
  }

  public changeGeneratorDataLabelFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentGeneratorDataLabel = e;
    console.log(e);
  }
}
