<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
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

<style scoped>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>

<style>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.sidebar.component .navigation-bar.component {
  height: 100%;
  max-height: 100%;
}

.sidebar.component .navigation-bar.component .ui.menu {
  border-radius: 0;
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 100%;
  border-top: 0;
}

.sidebar.component .navigation-bar.component .ui.menu::-webkit-scrollbar {
  width: .2em;
}

.sidebar.component .navigation-bar.component .ui.menu::-webkit-scrollbar-thumb {
  background: #a5b1c2;
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
import NavigationBarComponent from '@/iochord/chdsr/common/ui/semantic-components/navigations/components/NavigationBarComponent.vue';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    NavigationBarComponent,
  },
})
export default class SidebarComponent extends BaseComponent {
  @Prop() private type!: string;
}
</script>
