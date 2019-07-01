<template>
  <div class="ui compact message" v-bind:class="size">
    <i v-if="dismissable" class="close icon"></i>
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
import MessageComponent from '@/iochord/chdsr/common/ui/semantic/messages/classes/MessageComponent';

@Component
export default class MessageCompactComponent extends MessageComponent {
  //
}
</script>
