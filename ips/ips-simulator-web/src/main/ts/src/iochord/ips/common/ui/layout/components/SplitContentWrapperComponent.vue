<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="wrapper component">
    <SplitContentComponent>
      <template slot="content-left">
        <slot name="content-left"></slot>
      </template>

      <template slot="content-right">
        <slot name="content-right"></slot>
      </template>
    </SplitContentComponent>
  </div>
</template>

<style scoped>
.wrapper.component {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import SplitContentComponent from './SplitContentComponent.vue';

@Component({
  components: {
    SplitContentComponent,
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
export default class SplitContentWrapperComponent extends BaseComponent {
  //
}
</script>
