<template>
  <div class="ui tiny queue modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Queue</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Label</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Queue Type</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">
              <div class="ui checkbox">
                <input type="checkbox" class="hidden">
                <label>Shared buffer</label>
              </div>
            </div>
            <div class="twelve wide column"></div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Type</h4>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">
              <div class="ui radio checkbox">
                <input type="radio" name="xrb" checked class="hidden">
                <label>Single</label>
              </div>
            </div>
            <div class="twelve wide column"></div>
          </div>
          <div class="row">
            <div class="four wide column">
              <div class="ui radio checkbox">Queue Size</div>
            </div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <div class="ui radio checkbox">
                <input type="radio" name="xrb" checked class="hidden">
                <label>Dedicated queue to resources</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <table class="ui celled compact table">
                <thead>
                  <tr>
                    <th>Sub Queue ID</th>
                    <th>Queue Size</th>
                    <th>Resource ID</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                      <button class="ui negative basic button">
                        <i class="close icon"></i>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
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
declare const $: any;

@Component
export default class QueueDataModal extends Vue {}
</script>