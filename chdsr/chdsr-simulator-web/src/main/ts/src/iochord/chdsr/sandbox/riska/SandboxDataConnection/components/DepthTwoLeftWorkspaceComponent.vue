<template>
  <div class="depth two component">
    <!-- Depth One Menu Item -->
    <SidebarComponent>
      <template slot="sidebar-menu-item">
        <slot name="depth-one-menu-item"></slot>
      </template>
    </SidebarComponent>

    <!-- Depth Two Menu Item -->
    <SidebarComponent>
      <template slot="sidebar-menu-item">
        <slot name="depth-two-menu-item"></slot>
      </template>
    </SidebarComponent>

    <!-- Content -->
    <div class="ui basic segment">
      <slot name="content"></slot>
    </div>




    <!-- <template name="depth-two-menu">
      <slot name="depth-two-menu">dsdksdjk</slot>
    </template> -->
  </div>


</template>

<style scoped>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.depth.two.component {
  height: 100%;
  overflow: hidden;
  max-height: 100%;
  position: relative;
  display: flex;
}

.ui.basic.segment {
  overflow-y: scroll;
  width: 100%;
}
</style>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import SidebarComponent from '@/iochord/chdsr/common/ui/layout/components/SidebarComponent.vue';

@Component({
  components: {
      SidebarComponent,
  },
})
export default class DepthTwoLeftWorkspaceComponent extends BaseComponent {

}
</script>
