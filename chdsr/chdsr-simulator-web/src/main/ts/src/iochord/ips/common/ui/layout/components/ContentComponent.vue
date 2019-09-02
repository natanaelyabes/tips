<template>
  <div class="content component">
    <RibbonComponent v-if="hasRibbonComponent">
      <template slot="ribbon-menu-item">
        <slot name="ribbon-menu-item"></slot>
      </template>
    </RibbonComponent>
    <slot name="content"></slot>
  </div>
</template>

<style scoped>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.content.component {
  width: 100%;
}
</style>

<style>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import RibbonComponent from './RibbonComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    RibbonComponent,
  },
})
export default class ContentComponent extends BaseComponent {
  private get hasRibbonComponent(): boolean {
    return !!this.$slots['ribbon-menu-item'];
  }
}
</script>

