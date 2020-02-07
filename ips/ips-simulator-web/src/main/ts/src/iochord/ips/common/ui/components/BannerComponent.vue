<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="banner component">
    <slot name="content"></slot>
  </div>
</template>

<script lang="ts">
// Vue & Libraries
import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Banner component.
 *
 * @export
 * @class BannerComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class BannerComponent extends BaseComponent {
  //
}
</script>
