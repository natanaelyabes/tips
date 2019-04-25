<template>
  <div class="application title-menu bar component">
    <NavigationBarComponent type="secondary">
      <template slot="menu-item">
        <slot name="application-title-menu-item" />
      </template>
    </NavigationBarComponent>
  </div>
</template>

<style scoped>
.application.title-menu.bar.component {
  border-bottom: 1px solid rgba(0,0,0,.2);
}
</style>

<style>
.application.title-menu.bar.component .ui.menu .dropdown.item .menu {
  min-width: max-content;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import NavigationBarComponent from '@/iochord/chdsr/common/ui/semantic/navigations/components/NavigationBarComponent.vue';

declare const $: any;
@Component({
  components: {
    NavigationBarComponent,
  },
})
export default class ApplicationTitleMenuBarComponent extends Vue {

}
</script>
