<template>
  <div class="ui tiny generator modal">
    <i class="close icon"></i>
    <div id="x_title" class="ui red header">
      <h3 class="ui green header">Generator</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" id="generator_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Object</h4>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Data</div>
            <div class="nine wide column">
              <input type="text" id="x_txt_label">
            </div>
            <div class="three wide column">
              <button class="ui button">...</button>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Arrival Rate</h4>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Distribution Type</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Expression</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Unit</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Entities per Arrival</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Max Arrivals</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">First Creation</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
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
export default class GeneratorDataModal extends SemanticComponent {}
</script>