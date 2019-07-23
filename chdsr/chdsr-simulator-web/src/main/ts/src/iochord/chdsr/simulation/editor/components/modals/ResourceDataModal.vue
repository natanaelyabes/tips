<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny resource modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Resource</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Group ID</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="six wide column">
              <div class="ui checkbox">
                <input type="checkbox" class="hidden">
                <label>Import from table</label>
              </div>
            </div>
            <div class="ten wide column">
              <button class="ui button">...</button>
            </div>
          </div>
          <div class="row">
            <table class="ui celled compact table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Quantity</th>
                  <th>Capacity</th>
                  <th>Available Time</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td></td>
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
    <div class="actions">
      <div class="ui save button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<style>
/**
 *
 * @package chdsr
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic-components/SemanticComponent';

declare const $: any;


/**
 *
 * @package chdsr
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class ResourceDataModal extends SemanticComponent {}
</script>