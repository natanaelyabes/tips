<template>
  <div class="application workspace component">
    <!-- Left sidebar -->
    <ApplicationSidebarComponent v-if="hasApplicationLeftSidebarComponent">
      <template slot="application-sidebar-menu-item">
        <slot name="application-left-sidebar-menu-item"></slot>
      </template>
    </ApplicationSidebarComponent>

    <!-- Content -->
    <ApplicationContentComponent v-if="hasApplicationContentComponent">
      <template slot="application-ribbon-menu-item">
        <slot name="application-ribbon-menu-item"></slot>
      </template>
      <template slot="application-content">
        <slot name="application-content"></slot>
      </template>
    </ApplicationContentComponent>

    <!-- Right sidebar -->
    <ApplicationSidebarComponent v-if="hasApplicationRightSidebarComponent">
      <template slot="application-sidebar-menu-item">
        <slot name="application-right-sidebar-menu-item"></slot>
      </template>
    </ApplicationSidebarComponent>
  </div>
</template>

<style scoped>
.application.workspace.component {
  height: 100%;
  overflow: hidden;
  max-height: 100%;
  position: relative;
  display: flex;
}
</style>

<style>

</style>


<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import ApplicationSidebarComponent from './ApplicationSidebarComponent.vue';
import ApplicationContentComponent from './ApplicationContentComponent.vue';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';

@Component({
  components: {
    ApplicationSidebarComponent,
    ApplicationContentComponent,
  },
})
export default class ApplicationWorkspaceComponent extends BaseComponent {
  private get hasApplicationLeftSidebarComponent(): boolean {
    return !!this.$slots['application-left-sidebar-menu-item'];
  }

  private get hasApplicationContentComponent(): boolean {
    return !!this.$slots['application-content'];
  }

  private get hasApplicationRightSidebarComponent(): boolean {
    return !!this.$slots['application-right-sidebar-menu-item'];
  }
}
</script>
