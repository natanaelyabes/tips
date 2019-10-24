<template>
  <div class="toolbox palette component">
    <div class="item">
      <div class="header">Toolbox</div>
      <div class="menu">
        <a title="start" :disabled="isDisabled" @mousedown="handleToolboxMouseDown('node', $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')">
          <div class="image-icon">
            <img draggable="false" src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_start.png" alt="" class="ui centered image" />
          </div>
          Start
        </a>
        <a title="stop" :disabled="isDisabled" @mousedown="handleToolboxMouseDown('node', $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')">
          <div class="image-icon">
            <img draggable="false" src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_stop.png" alt="" class="ui centered image" />
          </div>
          Stop
        </a>
        <a title="activity" :disabled="isDisabled" @mousedown="handleToolboxMouseDown('node', $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')">
          <div class="image-icon">
            <img draggable="false" src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_activity.png" alt="" class="ui centered image" />
          </div>
          Activity
        </a>
        <a title="branch" :disabled="isDisabled" @mousedown="handleToolboxMouseDown('node', $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')">
          <div class="image-icon">
            <img draggable="false" src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_branch.png" alt="" class="ui centered image" />
          </div>
          Branch
        </a>
        <a title="connector" :disabled="isDisabled" @mousedown="handleToolboxMouseDown('connector', $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')">
          <div class="image-icon">
            <img draggable="false" src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_connector.png" alt="" class="ui centered image" />
          </div>
          Connector
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.image.icon img {
  user-select: none;
  -moz-user-select: none;
  -webkit-user-drag: none;
  -webkit-user-select: none;
  -ms-user-select: none;
}
</style>

<script lang="ts">
import { Prop, Component, Mixins } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import PaletteMixin from '../../mixins/editors/PaletteMixin';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';

const graphModule = getModule(GraphModule);

@Component({
  subscriptions: () => {
    return(
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class ToolboxPaletteComponent extends Mixins(BaseComponent, PaletteMixin) {

  @Prop({
    default: false,
  })
  public isDisabled?: boolean;

  public mounted(): void {
    //
  }
}
</script>
