<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="item filter component">
    <form class="ui form">
      <div class="item">
        <div class="field">
          <input type="text" name="connect[name]" placeholder="Filter name">
        </div>
        <div class="inline fields">
          <div class="field">
            <button class="ui primary button">
              Save
            </button>
          </div>
          <div class="field">
            <button class="ui button">
              Cancel
            </button>
          </div>
        </div>
        <div class="field">
          <select v-model="filterOptions" v-on:change="onFilterChange(filterOptions)" class="ui search dropdown">
            <option value="1" selected>Add Filter</option>
            <option value="2">Timeframe</option>
            <option value="3">Filtered performance</option>
            <option value="4">Start/end points</option>
            <option value="5">Attributes</option>
            <option value="6">Follow relations</option>
            <option value="7">Missing value imputations</option>
          </select>
        </div>
      </div>
    </form>
    <form class="ui form">
      <div class="item">
        <div class="ui basic large labels">
          <a class="ui label">
            Timeframe
            <i class="delete icon"></i>
          </a>
          <a class="ui label">
            Performance
            <i class="delete icon"></i>
          </a>
        </div>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import * as $ from 'jquery';

@Component

/**
 * Item filter component.
 *
 * @export
 * @class ItemFilterComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ItemFilterComponent extends BaseComponent {

  /**
   * Current content component.
   *
   * @type {string}
   * @memberof ItemFilterComponent
   */
  public currentContentComponent: string = '';

  /**
   * Filter options.
   *
   * @type {string}
   * @memberof ItemFilterComponent
   */
  public filterOptions: string = '';

  /**
   * Filter change event listener.
   *
   * @param {string} prm
   * @memberof ItemFilterComponent
   */
  public onFilterChange(prm: string) {
    switch (prm) {
      case '1':
        this.currentContentComponent = '';
      case '2':
        this.currentContentComponent = 'ContentFilterTimeComponent';
        break;
      case '3':
        this.currentContentComponent = 'ContentFilterPerformanceComponent';
        break;
      case '4':
        this.currentContentComponent = 'ContentFilterPointsComponent';
        break;
      case '5':
        this.currentContentComponent = 'ContentFilterAttributesComponent';
        break;
      case '6':
        this.currentContentComponent = 'ContentFilterFollowerComponent';
        break;
      case '7':
        this.currentContentComponent = 'ContentFilterMissingImputationComponent';
        break;
    }
    if (prm !== '1') {
      this.$root.$emit('ebContentComponent', this.currentContentComponent);
    }
  }
}
</script>
