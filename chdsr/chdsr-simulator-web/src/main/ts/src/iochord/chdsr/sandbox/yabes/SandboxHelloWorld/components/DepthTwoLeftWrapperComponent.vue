<template>
  <div class="wrapper component">
    <!--  Header -->
    <HeaderComponent>
      <template slot="header-title">
        <slot name="header-title"/>
      </template>
      <template slot="header-breadcrumb">
        <slot name="header-breadcrumb"/>
      </template>
    </HeaderComponent>

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
.wrapper.component {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>


<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import HeaderComponent from '@/iochord/chdsr/common/ui/layout/components/HeaderComponent.vue';
import TitleMenuBarComponent from '@/iochord/chdsr/common/ui/layout/components/TitleMenuBarComponent.vue';
import DepthTwoLeftWorkspaceComponent from './DepthTwoLeftWorkspaceComponent.vue';

@Component({
  components: {
    HeaderComponent,
    DepthTwoLeftWorkspaceComponent,
  },
})
export default class DepthTwoLeftWrapperComponent extends BaseComponent {

}
</script>
