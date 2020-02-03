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
    <DepthTwoLeftWorkspaceComponent>
      <template slot="depth-one-menu-item">
        <slot name="depth-one-menu-item"></slot>
      </template>

      <template slot="depth-two-menu-item">
        <slot name="depth-two-menu-item"></slot>
      </template>

      <template slot="content">
        <slot name="content"></slot>
      </template>
    </DepthTwoLeftWorkspaceComponent>
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
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import HeaderComponent from '@/iochord/ips/common/ui/layout/components/HeaderComponent.vue';
import DepthTwoLeftWorkspaceComponent from '@/iochord/ips/common/ui/layout/components/DepthTwoLeftWorkspaceComponent.vue';

@Component({
  components: {
    HeaderComponent,
    DepthTwoLeftWorkspaceComponent,
  },
})
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class DepthTwoLeftWrapperComponent extends BaseComponent {

}
</script>
