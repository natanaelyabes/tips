<template>
  <div class="application content component">
    <ApplicationRibbonComponent v-if="hasApplicationRibbonComponent">
      <template slot="application-ribbon-menu-item">
        <slot name="application-ribbon-menu-item"></slot>
      </template>
    </ApplicationRibbonComponent>
    <slot name="application-content"></slot>
  </div>
</template>

<style scoped>
.application.content.component {
  width: 100%;
}
</style>

<style>

</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import ApplicationRibbonComponent from './ApplicationRibbonComponent.vue';

@Component({
  components: {
    ApplicationRibbonComponent,
  },
})
export default class ApplicationContentComponent extends Vue {
  private get hasApplicationRibbonComponent(): boolean {
    return !!this.$slots['application-ribbon-menu-item'];
  }
}
</script>

