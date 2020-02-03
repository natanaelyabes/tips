<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="content filter attributes component">
    <form class="ui form">
      <h4 class="ui dividing header">Filter Attributes</h4>
        <div class="three fields">
          <div class="eight wide field">
              <label>Category</label>
              <select>
                <option selected="" value="Custom">Custom...</option>
                <option value="caseid">Case ID</option>
                <option value="activity">Activity</option>
                <option value="originator">Originator</option>
                <option value="resource">Resource</option>
                <option value="attribute1">Attribute 1 ...</option>
                <option value="attribute2">Attribute 2 ...</option>
              </select>
          </div>
          <div class="two wide field">
            <label>Filter by</label>
          </div>
          <div class="six wide field">
            <div class="grouped fields">
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-selected" tabindex="0" class="hidden">
                  <label>Preserve selected</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="mandatory" tabindex="0" class="hidden">
                  <label>Mandatory</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="forbidden" tabindex="0" class="hidden">
                  <label>Forbidden</label>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="ui hidden divider"></div>
        <div class="field">
          <button class="ui primary button">
            Filter
          </button>
          <button class="ui button">
            Cancel
          </button>
        </div>
        <div class="ui hidden divider"></div>
        <div class="inline fields">
          <div class="field">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/data_management_filter/check.png" alt="" class="ui image" />
              </div>
            </a>
          </div>
          <div class="field">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/data_management_filter/cancel.png" alt="" class="ui image" />
              </div>
            </a>
          </div>
        </div>

        <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 50%">
        <table class="ui celled striped table">
          <thead>
            <tr>
              <th></th>
              <th>Values</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="collapsing">
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 1</td>
            </tr>
            <tr>
              <td>
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 3</td>
            </tr>
            <tr>
              <td>
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 4</td>
            </tr>
            <tr>
              <td>
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 5</td>
            </tr>
            <tr>
              <td>
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 6</td>
            </tr>
            <tr>
              <td>
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 7</td>
            </tr>
            <tr>
              <td>
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 8</td>
            </tr>
            <tr>
              <td>
                <div class="ui fitted slider checkbox">
                  <input type="checkbox"> <label></label>
                </div>
              </td>
              <td>Case 9</td>
            </tr>
          </tbody>
        </table>
      </div>
    </form>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class ContentFilterAttributesComponent extends BaseComponent {

}
</script>
