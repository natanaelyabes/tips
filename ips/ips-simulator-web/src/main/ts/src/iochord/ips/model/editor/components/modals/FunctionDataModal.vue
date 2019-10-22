<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny function modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Function</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Name</div>
            <div class="twelve wide column">
              <input @change="handleChangedLabel($event)" type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Input Parameter</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Code Segment</h4>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <textarea></textarea>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Return Parameter</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="actions">
      <p><em>Node properties are automatically saved</em></p>
    </div>
  </div>
</template>

<style>
/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
declare const $: any;


/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class FunctionDataModal extends SemanticComponent {
  @Prop() private functionDataLabel!: string;

  private tempFunctionDataLabel: string = '';

  @Watch('functionDataLabel')
  public onChangeFunctionDataLabel(newVal: string): void {
    this.tempFunctionDataLabel = newVal;
  }

  public handleChangedLabel(): void {
    this.$emit('changeFunctionDataLabel', this.tempFunctionDataLabel);
  }

  public mounted(): void {
    this.tempFunctionDataLabel = this.functionDataLabel;
  }

  public log(): void {
    console.log('Test');
  }
}
</script>
