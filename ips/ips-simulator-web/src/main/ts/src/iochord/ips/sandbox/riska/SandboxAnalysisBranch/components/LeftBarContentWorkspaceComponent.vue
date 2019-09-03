<template>
  <div class="left bar content workspace component">
    <!-- Left Bar Menu Item -->
    <SidebarComponent id="left-bar">
      <template slot="sidebar-menu-item">
        <slot name="left-bar-menu-item"></slot>
      </template>
    </SidebarComponent>

    <!-- <SplitContentComponent v-if="hasSplitContentComponent"> -->
      <!-- <template slot="content-left">
        <slot name="content-left"></slot>
      </template>

      <template slot="content-right">
        <slot name="content-right"></slot>
      </template> -->
      <div class="ui basic segment">
        <slot name="content"></slot>
      </div>
    <!-- </SplitContentComponent> -->
  </div>
</template>

<style scoped>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.left.bar.content.workspace.component {
  height: 100%;
  overflow: hidden;
  max-height: 100%;
  position: relative;
  display: inline-flex;
}

.ui.basic.segment {
  overflow-y: auto;
  padding: 0;
  width: 100%;
}
</style>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import SidebarComponent from '@/iochord/ips/common/ui/layout/components/SidebarComponent.vue';

@Component({
  components: {
    SidebarComponent,
  },
})
export default class LeftBarContentWorkspaceComponent extends BaseComponent {
  private get hasSplitContentComponent(): boolean {
    return !!this.$slots['content-right'];
  }
}
</script>
