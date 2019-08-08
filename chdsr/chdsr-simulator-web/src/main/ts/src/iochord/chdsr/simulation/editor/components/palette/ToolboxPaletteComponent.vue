<template>
  <div class="toolbox palette component">
    <div class="item">
      <div class="header">Toolbox</div>
      <div class="menu">
        <a @mousedown="createItem('start', $event)" class="ui basic button item">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_start.png" alt="" class="ui centered image" />
          </div>
          Start
        </a>
        <a @mousedown="createItem('stop', $event)" class="ui basic button item">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_stop.png" alt="" class="ui centered image" />
          </div>
          Stop
        </a>
        <a @mousedown="createItem('activity', $event)" class="ui basic button item">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_activity.png" alt="" class="ui centered image" />
          </div>
          Activity
        </a>
        <a @mousedown="createItem('branch', $event)" class="ui basic button item">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_branch.png" alt="" class="ui centered image" />
          </div>
          Branch
        </a>
        <a class="ui basic button item">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_connector.png" alt="" class="ui centered image" />
          </div>
          Connector
        </a>
      </div>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import PaletteMixin from '@/iochord/chdsr/simulation/editor/mixins/editors/PaletteMixin';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
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
  public mounted(): void {
    //
  }
}
</script>
