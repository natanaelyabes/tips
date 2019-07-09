<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="application sidebar component">
    <NavigationBarComponent type="vertical labeled icon">
      <template slot="menu-item">
        <slot name="application-sidebar-menu-item"></slot>
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
.application.sidebar.component .navigation-bar.component {
  height: 100%;
  max-height: 100%;
}

.application.sidebar.component .navigation-bar.component .ui.menu {
  border-radius: 0;
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 100%;
  border-top: 0;
}

.application.sidebar.component .navigation-bar.component .ui.menu::-webkit-scrollbar {
  width: .2em;
}

.application.sidebar.component .navigation-bar.component .ui.menu::-webkit-scrollbar-thumb {
  background: #a5b1c2;
}

.application.sidebar.component .navigation-bar.component .ui.menu a.ui.basic.button.item {
  margin-left: .25em;
  margin-bottom: .25em;
}
</style>


<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import NavigationBarComponent from '@/iochord/chdsr/common/ui/semantic/navigations/components/NavigationBarComponent.vue';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';


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
export default class ApplicationSidebarComponent extends BaseComponent {
  //
}
</script>
