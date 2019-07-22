<!--
  @package chdsr
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
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.title-menu.bar.component {
  border-bottom: 1px solid rgba(0,0,0,.2);
}
</style>

<style>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.title-menu.bar.component .ui.menu .dropdown.item .menu {
  min-width: max-content;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import NavigationBarComponent from '@/iochord/chdsr/common/ui/semantic-components/navigations/components/NavigationBarComponent.vue';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

declare const $: any;


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
export default class TitleMenuBarComponent extends BaseComponent {
  //
}
</script>
