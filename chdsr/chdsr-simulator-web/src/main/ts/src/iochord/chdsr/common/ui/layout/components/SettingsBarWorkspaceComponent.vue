<template>
  <div class="settings bar wrapper component">
    <!-- Ribbon Menu Item -->
    <RibbonComponent id="ribbon-bar">
      <template slot="ribbon-menu-item">
        <slot name="ribbon-bar-menu-item"></slot>
      </template>
    </RibbonComponent>

    <!-- Content -->
    <div class="ui basic segment">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<style>
.ribbon.bar {
  height: 30%;
  width: 100%;
  max-height: 100%;
  display: flex;
}

.ui.basic.segment {
  overflow-y: scroll;
  width: 100%;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import RibbonComponent from '@/iochord/chdsr/common/ui/layout/components/RibbonComponent.vue';

@Component({
  components: {
    RibbonComponent,
  },
})
export default class SettingsBarWorkspaceComponent extends BaseComponent {

}

</script>
