<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="wrapper component">

    <!--  Header -->
    <HeaderComponent v-if="hasHeader">
      <template slot="header-title">
        <slot name="header-title"/>
      </template>
      <template slot="header-breadcrumb">
        <slot name="header-breadcrumb" />
      </template>
    </HeaderComponent>

    <!--  Title Menu Item -->
    <TitleMenuBarComponent v-if="hasHeader && hasTitleMenu">
      <template slot="title-menu-item">
        <slot name="title-menu-item" />
      </template>
    </TitleMenuBarComponent>

    <!--  Workspace -->
    <WorkspaceComponent>
      <template v-if="hasLeftSidebar" slot="left-sidebar-menu-item">
        <slot name="left-sidebar-menu-item"></slot>
      </template>
      <template v-if="hasRibbon" slot="ribbon-menu-item">
        <slot name="ribbon-menu-item"></slot>
      </template>
      <template v-if="hasContent" slot="content">
        <slot name="content"></slot>
      </template>
      <template v-if="hasRightSidebar" slot="right-sidebar-menu-item">
        <slot name="right-sidebar-menu-item"></slot>
      </template>
    </WorkspaceComponent>
  </div>
</template>

<style scoped>
.wrapper.component {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

import HeaderComponent from './HeaderComponent.vue';
import TitleMenuBarComponent from './TitleMenuBarComponent.vue';
import WorkspaceComponent from './WorkspaceComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component({
  components: {
    HeaderComponent,
    TitleMenuBarComponent,
    WorkspaceComponent,
  },
})

/**
 * Wrapper component.
 *
 * @export
 * @class WrapperComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class WrapperComponent extends BaseComponent {

  /**
   * Boolean function to evaluate whether current component has header. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WrapperComponent
   */
  private get hasHeader(): boolean {
    return !!this.$slots['header-title'] || !!this.$slots['header-breadcrumb'];
  }

  /**
   * Boolean function to evaluate whether current component has title menu. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WrapperComponent
   */
  private get hasTitleMenu(): boolean {
    return !!this.$slots['title-menu-item'];
  }

  /**
   * Boolean function to evaluate whether current component has left side bar. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WrapperComponent
   */
  private get hasLeftSidebar(): boolean {
    return !!this.$slots['left-sidebar-menu-item'];
  }

  /**
   * Boolean function to evaluate whether current component has right side bar. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WrapperComponent
   */
  private get hasRightSidebar(): boolean {
    return !!this.$slots['right-sidebar-menu-item'];
  }

  /**
   * Boolean function to evaluate whether current component has ribbon. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WrapperComponent
   */
  private get hasRibbon(): boolean {
    return !!this.$slots['ribbon-menu-item'];
  }

  /**
   * Boolean function to evaluate whether current component has content. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof WrapperComponent
   */
  private get hasContent(): boolean {
    return !!this.$slots['content'];
  }
}
</script>
