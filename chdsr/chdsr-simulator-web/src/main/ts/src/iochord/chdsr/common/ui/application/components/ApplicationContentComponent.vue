<template>
  <div class="application content component">
    <ApplicationRibbonComponent v-if="hasApplicationRibbonComponent">
      <template slot="application-ribbon-menu-item">
        <slot name="application-ribbon-menu-item"></slot>
      </template>
    </ApplicationRibbonComponent>
    <slot name="application-content"></slot>
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
.application.content.component {
  width: 100%;
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
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import ApplicationRibbonComponent from './ApplicationRibbonComponent.vue';
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
    ApplicationRibbonComponent,
  },
})
export default class ApplicationContentComponent extends BaseComponent {
  private get hasApplicationRibbonComponent(): boolean {
    return !!this.$slots['application-ribbon-menu-item'];
  }
}
</script>

