<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
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
.workspace.component {
  height: 100%;
  overflow-x: auto;
  overflow-y: hidden;
  max-height: 100%;
  position: relative;
  display: flex;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import SidebarComponent from './SidebarComponent.vue';
import ContentComponent from './ContentComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component({
  components: {
    SidebarComponent,
    ContentComponent,
  },
})

/**
 * Workspace component.
 *
 * @export
 * @class WorkspaceComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class WorkspaceComponent extends BaseComponent {

  /**
   * Boolean function to evaluate whether current component has left sidebar component. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WorkspaceComponent
   */
  private get hasLeftSidebarComponent(): boolean {
    return !!this.$slots['left-sidebar-menu-item'];
  }

  /**
   * Boolean function to evaluate whether current component has content component. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WorkspaceComponent
   */
  private get hasContentComponent(): boolean {
    return !!this.$slots['content'];
  }

  /**
   * Boolean function to evaluate whether current component has right sidebar component. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WorkspaceComponent
   */
  private get hasRightSidebarComponent(): boolean {
    return !!this.$slots['right-sidebar-menu-item'];
  }
}
</script>
