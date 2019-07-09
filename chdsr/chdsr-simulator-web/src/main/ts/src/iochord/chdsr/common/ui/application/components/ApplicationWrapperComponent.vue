<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="application wrapper component">

    <!-- Application Header -->
    <ApplicationHeaderComponent v-if="hasHeader">
      <template slot="application-header-title">
        <slot name="application-header-title" />
      </template>
      <template slot="application-header-breadcrumb">
        <slot name="application-header-breadcrumb" />
      </template>
    </ApplicationHeaderComponent>

    <!-- Application Title Menu Item -->
    <ApplicationTitleMenuBarComponent v-if="hasTitleMenu">
      <template slot="application-title-menu-item">
        <slot name="application-title-menu-item" />
      </template>
    </ApplicationTitleMenuBarComponent>

    <!-- Application Workspace -->
    <ApplicationWorkspaceComponent>
      <template v-if="hasLeftSidebar" slot="application-left-sidebar-menu-item">
        <slot name="application-left-sidebar-menu-item"></slot>
      </template>
      <template v-if="hasRibbon" slot="application-ribbon-menu-item">
        <slot name="application-ribbon-menu-item"></slot>
      </template>
      <template v-if="hasContent" slot="application-content">
        <slot name="application-content"></slot>
      </template>
      <template v-if="hasRightSidebar" slot="application-right-sidebar-menu-item">
        <slot name="application-right-sidebar-menu-item"></slot>
      </template>
    </ApplicationWorkspaceComponent>
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
.application.wrapper.component {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

import ApplicationHeaderComponent from './ApplicationHeaderComponent.vue';
import ApplicationTitleMenuBarComponent from './ApplicationTitleMenuBarComponent.vue';
import ApplicationWorkspaceComponent from './ApplicationWorkspaceComponent.vue';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    ApplicationHeaderComponent,
    ApplicationTitleMenuBarComponent,
    ApplicationWorkspaceComponent,
  },
})
export default class ApplicationWrapperComponent extends BaseComponent {

  private get hasHeader(): boolean {
    return !!this.$slots['application-header-title'] || !!this.$slots['application-header-breadcrumb'];
  }

  private get hasTitleMenu(): boolean {
    return !!this.$slots['application-title-menu-item'];
  }

  private get hasLeftSidebar(): boolean {
    return !!this.$slots['application-left-sidebar-menu-item'];
  }

  private get hasRightSidebar(): boolean {
    return !!this.$slots['application-right-sidebar-menu-item'];
  }

  private get hasRibbon(): boolean {
    return !!this.$slots['application-ribbon-menu-item'];
  }

  private get hasContent(): boolean {
    return !!this.$slots['application-content'];
  }
}
</script>
