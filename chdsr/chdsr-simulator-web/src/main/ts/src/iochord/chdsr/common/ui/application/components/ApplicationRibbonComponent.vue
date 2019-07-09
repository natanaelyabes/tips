<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="application ribbon component">
    <NavigationBarComponent type="secondary">
      <template slot="menu-item">
        <slot name="application-ribbon-menu-item"></slot>
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
.application.ribbon.component {
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
export default class ApplicationRibbonComponent extends BaseComponent {

}
</script>
