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
import { Component, Prop } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';

declare const $: any;

@Component
export default class ProgressComponent extends SemanticComponent {
  @Prop() private percent!: number;
  @Prop() private color!: string;

  /** @Override */
  public declareSemanticModules(): void {
    this.declareProgress();
  }

  private declareProgress(): void {
    $('#progress').progress();
  }
}
</script>
