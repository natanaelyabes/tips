<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="ui icon message" v-bind:class="[color, size]">
    <i v-if="dismissable" class="close icon"></i>
    <i v-if="icon" v-bind:class="icon" class="icon"></i>
    <div class="content">
      <div class="header"><slot name="header"></slot></div>
      <p><slot name="body"></slot></p>
      <slot name="list"></slot>
    </div>
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
</style>

<script lang="ts">
import { Component, Prop } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import MessageComponent from '@/iochord/ips/common/ui/semantic-components/messages/class/MessageComponent';

// JQuery Symbol Handler
declare const $: any;


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class MessageIconComponent extends MessageComponent {
  @Prop() private color!: string; // warning|info|negative|error|positive|success|color
  @Prop() private icon!: string;
}
</script>
