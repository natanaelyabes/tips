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
              <input type="text" @change="handleChangedLabel()" v-model="_stopLabel" id="stop_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <div class="inline field">
                <input type="checkbox" @change="handleChangedReport()" v-model="_report" class="hidden">
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
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '../../../ui/semantic/SemanticComponent';
declare const $: any;

@Component
export default class StopNodeModal extends SemanticComponent {
  @Prop() private stopLabel!: string;
  @Prop() private report!: boolean;

  private _stopLabel!: string;
  private _report!: boolean;

  public handleChangedLabel(): void {
    this.$emit('changeStopLabel', this._stopLabel);
  }

  public handleChangedReport(): void {
    this.$emit('changeReport', this._report);
  }

  private beforeMount(): void {
    this._stopLabel = this.stopLabel;
    this._report = this.report;
  }
}
</script>