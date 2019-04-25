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
import { Component, Prop, Vue } from 'vue-property-decorator';

// JQuery Symbol Handler
declare const $: any;

@Component
export default class MessageCompactComponent extends Vue {
  @Prop() private size!: string;  // mini|tiny|small|large|big|huge|massive
  @Prop() private dismissable!: boolean;

  private mounted(): void {
    $('.message .close')
      .on('click', function(this: any) {
        $(this)
          .closest('.message')
          .transition('fade')
        ;
      })
    ;
  }

}
</script>
