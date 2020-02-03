<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="workspace component">
    <!-- Left sidebar -->
    <SidebarComponent type="labeled icon" v-if="hasLeftSidebarComponent">
      <template slot="sidebar-menu-item">
        <slot name="left-sidebar-menu-item"></slot>
      </template>
    </SidebarComponent>

    <!-- Content -->
    <ContentComponent v-if="hasContentComponent">
      <template slot="ribbon-menu-item">
        <slot name="ribbon-menu-item"></slot>
      </template>
      <template slot="content">
        <slot name="content"></slot>
      </template>
    </ContentComponent>

    <!-- Right sidebar -->
    <SidebarComponent type="labeled icon" v-if="hasRightSidebarComponent">
      <template slot="sidebar-menu-item">
        <slot name="right-sidebar-menu-item"></slot>
      </template>
    </SidebarComponent>
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
.workspace.component {
  height: 100%;
  overflow-x: auto;
  overflow-y: hidden;
  max-height: 100%;
  position: relative;
  display: flex;
}
</style>

<style>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>


<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import SidebarComponent from './SidebarComponent.vue';
import ContentComponent from './ContentComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    SidebarComponent,
    ContentComponent,
  },
})
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class WorkspaceComponent extends BaseComponent {
  private get hasLeftSidebarComponent(): boolean {
    return !!this.$slots['left-sidebar-menu-item'];
  }

  private get hasContentComponent(): boolean {
    return !!this.$slots['content'];
  }

  private get hasRightSidebarComponent(): boolean {
    return !!this.$slots['right-sidebar-menu-item'];
  }
}
</script>
