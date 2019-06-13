<template>
  <div class="application header component">
    <div class="ui basic segment">
      <h1 v-if="hasHeaderTitle"><slot name="application-header-title" /></h1>
      <div v-if="hasHeaderTitle === hasBreadcrumbComponent && hasHeaderTitle === true" class="ui divider"></div>

      <!-- BreadcrumbComponent -->
      <BreadcrumbComponent v-if="hasBreadcrumbComponent">
        <template slot="sections">
          <slot name="application-header-breadcrumb" />
        </template>
      </BreadcrumbComponent>

    </div>
  </div>
</template>

<style scoped>
.application.header.component .ui.basic.segment {
  margin: 0;
  border-bottom: 1px solid rgba(0,0,0,.2);
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import BreadcrumbComponent from '@/iochord/chdsr/common/ui/semantic/breadcrumbs/components/BreadcrumbComponent.vue';

@Component({
  components: {
    BreadcrumbComponent,
  },
})
export default class ApplicationHeaderComponent extends Vue {
  private get hasHeaderTitle(): boolean {
    return !!this.$slots['application-header-title'];
  }

  private get hasBreadcrumbComponent(): boolean {
    return !!this.$slots['application-header-breadcrumb'];
  }
}
</script>
