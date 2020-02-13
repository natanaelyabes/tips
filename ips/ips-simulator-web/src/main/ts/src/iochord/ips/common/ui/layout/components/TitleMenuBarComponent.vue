<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="title-menu bar component">
    <NavigationBarComponent type="secondary">
      <template slot="menu-item">
        <slot name="title-menu-item" />
      </template>
    </NavigationBarComponent>
  </div>
</template>

<style scoped>
.title-menu.bar.component {
  border-bottom: 1px solid rgba(0,0,0,.2);
}
</style>

<style>
.title-menu.bar.component .ui.menu .dropdown.item .menu {
  min-width: max-content;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import NavigationBarComponent from '@/iochord/ips/common/ui/semantic-components/navigations/components/NavigationBarComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

declare const $: any;

@Component({
  components: {
    NavigationBarComponent,
  },
})

/**
 * Split content wrapper component.
 *
 * @export
 * @class SplitContentWrapperComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class TitleMenuBarComponent extends BaseComponent {
  //
}
</script>
