import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { Component } from 'vue-property-decorator';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

@Component<ObjectTypeDataModalMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class ObjectTypeDataModalMixin extends BaseComponent {
  public parentObjectTypeDataLabel: string = '';

  public changeObjectTypeDataLabel(newVal: string): void {
    this.parentObjectTypeDataLabel = newVal;
  }

  public changeObjectTypeDataLabelFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentObjectTypeDataLabel = e;
    console.log(e);
  }
}
