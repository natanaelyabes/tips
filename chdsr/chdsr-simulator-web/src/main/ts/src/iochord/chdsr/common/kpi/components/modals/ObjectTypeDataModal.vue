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
              <input type="text" id="object_type_txt_label">
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
export default class ObjectTypeDataModal extends SemanticComponent {}
</script>