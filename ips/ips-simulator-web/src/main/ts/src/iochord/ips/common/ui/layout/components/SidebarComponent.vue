<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="sidebar component">
    <NavigationBarComponent :type="'vertical ' + type">
      <template slot="menu-item">
        <slot name="sidebar-menu-item"></slot>
      </template>
    </NavigationBarComponent>
  </div>
</template>

<style>
.sidebar.component {
  width: fit-content;
  min-width: fit-content;
}

.sidebar.component .navigation-bar.component {
  height: 100%;
  max-height: 100%;
}

.sidebar.component .navigation-bar.component .ui.menu {
  border-radius: 0;
  height: inherit;
  border-top: 0;
}

.sidebar.component .navigation-bar.component .ui.menu a.ui.basic.button.item {
  margin-left: .25em;
  margin-bottom: .25em;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue, Prop } from 'vue-property-decorator';

// Components
import NavigationBarComponent from '@/iochord/ips/common/ui/semantic-components/navigations/components/NavigationBarComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component({
  components: {
    NavigationBarComponent,
  },
})

/**
 * Sidebar component.
 *
 * @export
 * @class SidebarComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class SidebarComponent extends BaseComponent {

  /**
   * Specify the type of the sidebar.
   *
   * @private
   * @type {string}
   * @memberof SidebarComponent
   */
  @Prop() private type!: string;
}
</script>
