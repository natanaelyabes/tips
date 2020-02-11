<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="left bar content workspace component">

    <!-- Left Bar Menu Item -->
    <SidebarComponent id="left-bar">
      <template slot="sidebar-menu-item">
        <slot name="left-bar-menu-item"></slot>
      </template>
    </SidebarComponent>

    <div class="ui basic segment">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<style scoped>
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

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import SidebarComponent from '@/iochord/ips/common/ui/layout/components/SidebarComponent.vue';

@Component({
  components: {
    SidebarComponent,
  },
})

/**
 * Left bar content workspace component.
 *
 * @export
 * @class LeftBarContentWorkspaceComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class LeftBarContentWorkspaceComponent extends BaseComponent {

  /**
   * Boolean function to evaluate whether split content component does exists. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof LeftBarContentWorkspaceComponent
   */
  private get hasSplitContentComponent(): boolean {
    return !!this.$slots['content-right'];
  }
}
</script>
