<template>
  <div class="banner component">
    <slot name="content"></slot>
  </div>
</template>

<style scoped>

</style>

<script lang="ts">
// Vue & Libraries
import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';

@Component
export default class BannerComponent extends BaseComponent {
  //
}
</script>
