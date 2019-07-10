<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny start modal">
    <i class="close icon"></i>
    <div id="start_modal_title" class="header">
      <h3 class="ui green header">Start</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">
              Label
            </div>
            <div class="thirteen wide column">
              <input type="text" @change="handleChangedLabel()" v-model="tempStartLabel" id="start_txt_label" />
            </div>
          </div>
          <div class="row">
            <div class="three wide column">
              Generator
            </div>
            <div class="ten wide column">
              <input type="text" @change="handleChangedGenerator()" v-model="tempGenerator" id="start_txtgen" />
            </div>
            <div class="three wide column">
              <button class="ui button">...</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="actions">
      <div class="ui save button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<style>
/**
 *
 * @package chdsr
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';
declare const $: any;

/**
 *
 * @package chdsr
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class StartNodeModal extends SemanticComponent {
  @Prop() private startLabel!: string;
  @Prop() private generator!: string;

  private tempStartLabel: string = '';
  private tempGenerator: string = '';

  @Watch('startLabel')
  public onChangeStartLabel(newVal: string): void {
    this.tempStartLabel = newVal;
  }

  @Watch('generator')
  public onChangeGenerator(newVal: string): void {
    this.tempGenerator = newVal;
  }

  public handleChangedLabel(): void {
    this.$emit('changeStartLabel', this.tempStartLabel);
  }

  public handleChangedGenerator(): void {
    this.$emit('changeGenerator', this.tempGenerator);
  }

  public mounted(): void {
    this.$nextTick(() => {
      this.tempStartLabel = this.startLabel;
      this.tempGenerator = this.generator;
    });
  }
}
</script>