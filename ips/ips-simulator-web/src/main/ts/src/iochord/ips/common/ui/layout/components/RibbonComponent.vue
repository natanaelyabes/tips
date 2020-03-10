<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="ribbon component">
    <NavigationBarComponent type="secondary" id="navigation-bar">
      <template slot="menu-item">
        <slot name="ribbon-menu-item"></slot>
      </template>
    </NavigationBarComponent>
  </div>
</template>

<style scoped>
.ribbon.component {
  border-bottom: 1px solid rgba(0,0,0,.2);
}

#navigation-bar {
  overflow-x: auto;
  overflow-y: hidden;
}
</style>

<style>
 @media screen and (max-width: 1440px) {
  .ribbon.component .ui.menu.secondary {
    width: max-content;
  }
 }
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import NavigationBarComponent from '@/iochord/ips/common/ui/semantic-components/navigations/components/NavigationBarComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component({
  components: {
    NavigationBarComponent,
  },
})

/**
 * Ribbon component.
 *
 * @export
 * @class RibbonComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class RibbonComponent extends BaseComponent {
  //
}
</script>
