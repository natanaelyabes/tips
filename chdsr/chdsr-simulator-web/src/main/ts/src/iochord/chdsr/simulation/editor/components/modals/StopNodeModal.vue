<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny stop modal">
    <i class="close icon"></i>
    <div id="stop_modal_title" class="header">
      <h3 class="ui green header">Stop</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" @change="handleChangedLabel()" v-model="tempStopLabel" id="stop_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <div class="inline field">
                <input type="checkbox" @change="handleChangedReport()" v-model="tempReport" class="hidden">
                <label>Report statistics</label>
              </div>
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
export default class StopNodeModal extends SemanticComponent {
  @Prop() private stopLabel!: string;
  @Prop() private stopReport!: boolean;

  private tempStopLabel: string = '';
  private tempReport: boolean = false;

  @Watch('stopLabel')
  public onChangeStopLabel(newVal: string): void {
    this.tempStopLabel = newVal;
  }

  @Watch('stopReport')
  public onChangeReport(newVal: boolean): void {
    this.tempReport = newVal;
  }

  public handleChangedLabel(): void {
    this.$emit('changeStopLabel', this.tempStopLabel);
  }

  public handleChangedReport(): void {
    this.$emit('changeStopReport', this.tempReport);
  }

  public mounted(): void {
    this.$nextTick(() => {
      this.tempStopLabel = this.stopLabel;
      this.tempReport = this.stopReport;
    });
  }
}
</script>