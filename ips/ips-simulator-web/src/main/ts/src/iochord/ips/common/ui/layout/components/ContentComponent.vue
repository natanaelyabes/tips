<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
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
.content.component {
  width: 100%;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import RibbonComponent from './RibbonComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component({
  components: {
    RibbonComponent,
  },
})

/**
 * Content component of PageLayout
 *
 * @export
 * @class ContentComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class ContentComponent extends BaseComponent {

  /**
   * Boolean function to evaluate whether a ribbon component exists. False otherwise.
   *
   * @readonly
   * @private
   * @type {boolean}
   * @memberof ContentComponent
   */
  private get hasRibbonComponent(): boolean {
    return !!this.$slots['ribbon-menu-item'];
  }
}
</script>

