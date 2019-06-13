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
              <input type="text" id="stop_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <div class="inline field">
                <input type="checkbox" class="hidden">
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
import { Component, Prop, Vue } from "vue-property-decorator";
@Component
export default class StopNodeModal extends Vue {}
</script>