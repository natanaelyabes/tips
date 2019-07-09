<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="progress component">
    <div id="progress" v-bind:data-percent="percent" v-bind:class="color" class="ui top attached progress">
      <div class="bar"></div>
    </div>
  </div>
</template>

<style>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Prop } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';

declare const $: any;


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
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
