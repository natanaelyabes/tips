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

</style>

<script lang="ts">
import { Component, Prop } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';
import MessageComponent from '@/iochord/chdsr/common/ui/semantic/messages/classes/MessageComponent';

// JQuery Symbol Handler
declare const $: any;

@Component
export default class MessageIconComponent extends MessageComponent {
  @Prop() private color!: string; // warning|info|negative|error|positive|success|color
  @Prop() private icon!: string;
}
</script>
