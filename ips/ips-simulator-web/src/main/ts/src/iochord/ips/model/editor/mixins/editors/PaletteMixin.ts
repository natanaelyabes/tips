// Vue & libraries
import { Component, Mixins } from 'vue-property-decorator';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

// Mixins
import NodeMixin from './toolbox/NodeMixin';
import ConnectorMixin from './toolbox/ConnectorMixin';


enum TOOLBOX {
  connector,
  node,
}


@Component
export default class PaletteMixin extends Mixins(BaseComponent, NodeMixin, ConnectorMixin) {
  public handleToolboxMouseDown(type: TOOLBOX, e: MouseEvent): void {

    if ((this as any).isDisabled) {
      return;
    }

    $('.sidebar.component .ui.basic.button.item').addClass('disabled');

    switch (type) {
      case 'connector' as any:
        this.createConnector(e);
        break;
      case 'node' as any:
        this.createNode((e.currentTarget as HTMLElement).title as any, e);
        break;
    }
  }
}
