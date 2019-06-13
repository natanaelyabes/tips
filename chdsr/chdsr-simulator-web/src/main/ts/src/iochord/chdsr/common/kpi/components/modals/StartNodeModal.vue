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
              <input type="text" id="start_txt_label" />
            </div>
          </div>
          <div class="row">
            <div class="three wide column">
              Generator
            </div>
            <div class="ten wide column">
              <input type="text" id="start_txt_generator" />
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

</style>


<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
@Component
export default class StartNodeModal extends Vue {

}
</script>