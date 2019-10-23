<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny sim_control modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Simulation Control</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Replication no.</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Stopping Criteria</div>
            <div class="twelve wide column">
              <select class="ui dropdown">
                <option value>Criteria</option>
                <option value="time">Data</option>
                <option value="steps">Steps</option>
                <option value="custom">Custom</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Value</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Start Simulation time</div>
            <div class="ten wide column">
              <input type="text" id="x_txt_label">
            </div>
            <div class="two wide column">
              <button class="ui button">...</button>
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
import { Component, Prop, Vue } from 'vue-property-decorator';
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
export default class ControlModal extends SemanticComponent {
  public log(): void {
    console.log('Test');
  }
}
</script>
