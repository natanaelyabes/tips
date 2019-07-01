<template>
  <div class="application ribbon component">
    <NavigationBarComponent type="secondary">
      <template slot="menu-item">
        <slot name="application-ribbon-menu-item"></slot>
      </template>
    </NavigationBarComponent>
  </div>
</template>

<style scoped>
.application.ribbon.component {
  border-bottom: 1px solid rgba(0,0,0,.2);
}
</style>

<style>

</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import NavigationBarComponent from '@/iochord/chdsr/common/ui/semantic/navigations/components/NavigationBarComponent.vue';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';

@Component({
  components: {
    NavigationBarComponent,
  },
})
export default class ApplicationRibbonComponent extends BaseComponent {

}
</script>
