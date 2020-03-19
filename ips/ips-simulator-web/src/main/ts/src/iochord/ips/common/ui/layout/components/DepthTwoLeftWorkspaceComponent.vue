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
    <div id="depth-two">
      <slot name="depth-two-menu-item"></slot>
    </div>

    <!-- Content -->
    <div class="ui basic segment" id="content">
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

#depth-two {
  padding: 1rem 0;
  overflow-y: scroll;
  overflow-x: auto;
  min-width: fit-content;
}

.ui.basic.segment#content {
  margin-top: 0;
  padding-top: 2em;
  overflow-y: auto;
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
