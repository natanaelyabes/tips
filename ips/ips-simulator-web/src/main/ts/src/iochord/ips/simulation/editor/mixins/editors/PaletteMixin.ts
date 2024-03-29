// Vue & libraries
import { Component, Mixins } from 'vue-property-decorator';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

// Mixins
import NodeMixin from './toolbox/NodeMixin';
import ConnectorMixin from './toolbox/ConnectorMixin';
import DataMixin from './toolbox/DataMixin';
import { getModule } from 'vuex-module-decorators';
import EditorState from '../../stores/editors/EditorState';

const editorState = getModule(EditorState);

/**
 * Enumerations for toolbox.
 *
 * @export
 * @enum {number}
 */
export enum TOOLBOX {
  CONNECTOR = 'CONNECTOR',
  NODE = 'NODE',
  DATA = 'DATA',
}

@Component

/**
 * The palette mixin object.
 *
 * @export
 * @class PaletteMixin
 * @extends {Mixins(BaseComponent, NodeMixin, DataMixin, ConnectorMixin)}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class PaletteMixin extends Mixins(BaseComponent, NodeMixin, DataMixin, ConnectorMixin) {

  /**
   * The toolbox mouse down event handler.
   *
   * @param {TOOLBOX} type
   * @param {MouseEvent} e
   * @returns {void}
   * @memberof PaletteMixin
   */
  public handleToolboxMouseDown(type: TOOLBOX, e: MouseEvent): void {
    if ((this as any).isDisabled) {
      return;
    }

    $('.sidebar.component .ui.basic.button.item').addClass('disabled');

    switch (type) {
      case TOOLBOX.CONNECTOR:
        editorState.setMode('connector');
        this.createConnector(e);
        break;
      case TOOLBOX.NODE:
        editorState.setMode('node');
        this.createNode((e.currentTarget as HTMLElement).title as any, e);
        break;
      case TOOLBOX.DATA:
        editorState.setMode('data');
        this.createData((e.currentTarget as HTMLElement).title as any, e);
        break;
    }
  }
}
