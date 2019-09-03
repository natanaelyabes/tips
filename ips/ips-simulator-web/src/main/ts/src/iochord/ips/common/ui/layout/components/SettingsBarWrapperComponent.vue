<template>
  <div class="settings bar wrapper component">
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
    <SettingsBarWorkspaceComponent>
      <template slot="ribbon-bar-menu-item">
        <slot name="ribbon-bar-menu-item"></slot>
      </template>

      <template slot="content">
        <slot name="content"></slot>
      </template>
    </SettingsBarWorkspaceComponent>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';
import HeaderComponent from '@/iochord/ips/common/ui/layout/components/HeaderComponent.vue';
import SettingsBarWorkspaceComponent from './SettingsBarWorkspaceComponent.vue';

@Component({
  components: {
    HeaderComponent,
    SettingsBarWorkspaceComponent,
  },
})
export default class SettingsBarWrapperComponent extends BaseComponent {

}
</script>
