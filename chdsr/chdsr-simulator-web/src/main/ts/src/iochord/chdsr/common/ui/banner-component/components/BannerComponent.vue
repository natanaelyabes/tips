<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="banner component">
    <slot name="content"></slot>
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
</style>

<script lang="ts">
// Vue & Libraries
import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class BannerComponent extends BaseComponent {
  //
}
</script>
