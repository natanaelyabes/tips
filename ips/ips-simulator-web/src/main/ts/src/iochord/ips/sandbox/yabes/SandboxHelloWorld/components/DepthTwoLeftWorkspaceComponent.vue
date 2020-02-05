<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="workspace component">

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
    <slot name="content"></slot>
  </div>
</template>

<style>

</style>

<style scoped>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.workspace.component {
  height: 100%;
  overflow: hidden;
  max-height: 100%;
  position: relative;
  display: flex;
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
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class DepthTwoLeftWorkspaceComponent extends BaseComponent {

}
</script>
