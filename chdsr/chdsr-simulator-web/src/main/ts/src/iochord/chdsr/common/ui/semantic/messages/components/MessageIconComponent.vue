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
import { Component, Prop, Vue } from 'vue-property-decorator';

// JQuery Symbol Handler
declare const $: any;

@Component
export default class MessageIconComponent extends Vue {
  @Prop() private color!: string; // warning|info|negative|error|positive|success|color
  @Prop() private size!: string;  // mini|tiny|small|large|big|huge|massive
  @Prop() private icon!: string;
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
