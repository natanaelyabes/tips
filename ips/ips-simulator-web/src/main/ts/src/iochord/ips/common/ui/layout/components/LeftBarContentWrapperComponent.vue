<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="wrapper component">
    <!--  Header -->
    <HeaderComponent>
      <template slot="header-title">
        <slot name="header-title"/>
      </template>
      <template slot="header-breadcrumb">
        <slot name="header-breadcrumb" />
      </template>
    </HeaderComponent>

    <!-- Workspace -->
    <LeftBarContentWorkspaceComponent>
      <template slot="left-bar-menu-item">
        <slot name="left-bar-menu-item"></slot>
      </template>
      <template slot="content">
        <slot name="content"></slot>
      </template>
    </LeftBarContentWorkspaceComponent>
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
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import HeaderComponent from '@/iochord/ips/common/ui/layout/components/HeaderComponent.vue';
import LeftBarContentWorkspaceComponent from './LeftBarContentWorkspaceComponent.vue';

@Component({
  components: {
    HeaderComponent,
    LeftBarContentWorkspaceComponent,
  },
})

/**
 * Left bar content workspace component.
 *
 * @export
 * @class LeftBarContentWrapperComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class LeftBarContentWrapperComponent extends BaseComponent {

  /**
   * Boolean function to evaluate whether current component has left content component.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof LeftBarContentWrapperComponent
   */
  private get hasLeftContentComponent(): boolean {
    return !!this.$slots['content-left'];
  }

  /**
   * Boolean function to evaluate whether current component has right content component.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof LeftBarContentWrapperComponent
   */
  private get hasRightContentComponent(): boolean {
    return !!this.$slots['content-right'];
  }
}
</script>

