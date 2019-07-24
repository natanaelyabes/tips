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
          <select class="ui search dropdown">
            <option value="">Add Filter</option>
            <option value="timeframe">Timeframe</option>
            <option value="performance">Filtered performance</option>
            <option value="points">Start/end points</option>
            <option value="attributes">Attributes</option>
            <option value="relations">Follow relations</option>
            <option value="missings">Missing value imputations</option>
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

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import * as $ from 'jquery';

@Component
export default class ItemFilterComponent extends BaseComponent {

}
</script>
