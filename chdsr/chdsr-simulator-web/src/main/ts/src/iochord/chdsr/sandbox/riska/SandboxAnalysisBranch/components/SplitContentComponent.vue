<template>
  <div class="split content component">
      <slot name="content-left"></slot>

      <slot name="content-right"></slot>
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
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

@Component
export default class SplitContentComponent extends BaseComponent {

}
</script>
