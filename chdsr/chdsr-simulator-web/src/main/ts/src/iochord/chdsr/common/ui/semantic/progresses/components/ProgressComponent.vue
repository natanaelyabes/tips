<template>
  <div class="progress component">
    <div id="progress" v-bind:data-percent="percent" v-bind:class="color" class="ui top attached progress">
      <div class="bar"></div>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

declare const $: any;

@Component
export default class ProgressComponent extends Vue {
  @Prop() private percent!: number;
  @Prop() private color!: string;

  private mounted(): void {
    $('#progress').progress();
  }
}
</script>
