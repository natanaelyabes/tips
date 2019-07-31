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

      <!-- <template v-if="hasLeftContentComponent" slot="content-left">
        <slot name="content-left"></slot>
      </template>

      <template v-if="hasRightContentComponent" slot="content-right">
        <slot name="content-right"></slot>
      </template> -->
      <template slot="content">
        <slot name="content"></slot>
      </template>
    </LeftBarContentWorkspaceComponent>
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
.wrapper.component {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import HeaderComponent from '@/iochord/chdsr/common/ui/layout/components/HeaderComponent.vue';
import LeftBarContentWorkspaceComponent from './LeftBarContentWorkspaceComponent.vue';

@Component({
  components: {
    HeaderComponent,
    LeftBarContentWorkspaceComponent,
  },
})
export default class LeftBarContentWrapperComponent extends BaseComponent {
  private get hasLeftContentComponent(): boolean {
    return !!this.$slots['content-left'];
  }

  private get hasRightContentComponent(): boolean {
    return !!this.$slots['content-right'];
  }
}
</script>

