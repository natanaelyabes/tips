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
          <label for="filter[name]">Create filter</label>
          <input id="filter[name]" type="text" name="filter[name]" placeholder="Filter name">
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
      </div>
    </form>
    <form class="ui form">
      <div class="item">
        <div class="field">
          <label for="filter[add]">Select filter</label>
          <select id="filter[add]" v-model="filterOptions" v-on:change="onFilterChange(filterOptions)" class="ui search dropdown">
            <option value="1" selected>Add Filter</option>
            <option value="2">Timeframe</option>
            <option value="3">Filtered performance</option>
            <option value="4">Start/end points</option>
            <option value="5">Attributes</option>
            <option value="6">Follow relations</option>
            <option value="7">Missing value imputations</option>
          </select>
        </div>
        <div class="ui message">
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
      </div>
    </form>
  </div>
</template>

<style scoped>
.ui.form .item {
  padding: 0;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import SemanticComponent from '../../../common/ui/semantic-components/SemanticComponent';

// JQuery Handler
declare const $: any;

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
export default class ItemFilterComponent extends SemanticComponent {

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

  public declareSemanticModules() {
    $('dropdown').dropdown();
  }
}
</script>
