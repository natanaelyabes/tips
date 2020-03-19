<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="split content component">
    <slot name="content-left"></slot>
    <slot name="content-right"></slot>
  </div>
</template>

<style scoped>
.split.content.component {
  display: block;
  width: 100%;
  height: 100%;
  overflow-y: none;
}
</style>

<style>

</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Split content component.
 *
 * @export
 * @class SplitContentComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class SplitContentComponent extends BaseComponent {
  //
}
</script>
