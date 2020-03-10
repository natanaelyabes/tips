<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="depth two component">
    <!-- Depth One Menu Item -->
    <SidebarComponent id="depth-one">
      <template slot="sidebar-menu-item">
        <slot name="depth-one-menu-item"></slot>
      </template>
    </SidebarComponent>

    <!-- Depth Two Menu Item -->
    <SidebarComponent id="depth-two">
      <template slot="sidebar-menu-item">
        <slot name="depth-two-menu-item"></slot>
      </template>
    </SidebarComponent>

    <!-- Content -->
    <div class="ui basic segment">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<style scoped>
.depth.two.component {
  height: 100%;
  overflow: hidden;
  max-height: 100%;
  position: relative;
  display: flex;
}

#depth-one {
  white-space: nowrap;
}

.ui.basic.segment {
  overflow-y: scroll;
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
 * Depth two left workspace component of PageLayout.
 *
 * @export
 * @class ContentComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class DepthTwoLeftWorkspaceComponent extends BaseComponent {
  //
}
</script>
