<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny object_type modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Object Type</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Name</div>
            <div class="thirteen wide column">
              <input type="text" @change="handleChangedLabel($event)" v-model="tempObjectTypeDataLabel" id="object_type_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Type</h4>
            </div>
          </div>
          <div class="row">
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
            <div class="two wide column">
              <button class="ui button">...</button>
            </div>
            <div class="two wide column">
              <button class="ui positive basic button">
                <i class="plus icon"></i>
              </button>
            </div>
          </div>
          <div class="row">
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
            <div class="two wide column">
              <button class="ui button">...</button>
            </div>
            <div class="two wide column">
              <button class="ui negative basic button">
                <i class="close icon"></i>
              </button>
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
export default class ObjectTypeDataModal extends SemanticComponent {
  @Prop() private objectTypeDataLabel!: string;

  private tempObjectTypeDataLabel: string = '';

  @Watch('objectTypeDataLabel')
  public onChangeObjectTypeDataLabel(newVal: string): void {
    this.tempObjectTypeDataLabel = newVal;
  }

  public handleChangedLabel(): void {
    this.$emit('changeObjectTypeDataLabel', this.tempObjectTypeDataLabel);
  }

  public mounted(): void {
    this.tempObjectTypeDataLabel = this.objectTypeDataLabel;
  }
}
</script>
