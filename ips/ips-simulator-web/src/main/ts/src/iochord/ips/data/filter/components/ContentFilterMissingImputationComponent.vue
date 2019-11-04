<template>
  <div class="content filter missing imputation component">
    <section>
      <label>Data summary</label>
      <div class="ui three column grid">
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Number of missing event's attributes</label>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Number of missing events by case</label>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Number of missing event by variables</label>
            </div>
          </div>
        </div>
      </div>
    </section>

    <div class="ui hidden divider"></div>
    <section>
      <label>Data handling methods</label>
      <div class="ui form">
        <div class="grouped fields">
          <div class="field">
            <div class="ui slider checkbox">
              <input type="radio" name="method" checked="checked" value="deletion" @change="methodChanged($event)">
              <label>Simple deletion method (%): $expectedDataLoseRate</label>
            </div>
          </div>
          <div class="field">
            <div class="ui slider checkbox checked">
              <input type="radio" name="method" value="imputation" @change="methodChanged($event)">
              <label>Event imputation method (%): $expectedRestorationRate</label>
            </div>
          </div>
        </div>
      </div>
    </section>

    <div class="ui hidden divider"></div>
    <!-- Data deletion results -->
    <section  v-if=" selectedMethod == 'deletion'">
      <label>Summary of deletion results</label>
      <div class="ui three column grid">
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Number of Events Deleted: $count</label>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Number of Cases Deleted : $count</label>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Data Loss Rate (%)</label>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Data imputation input parameter & results -->
    <section v-if=" selectedMethod == 'imputation'">
      <label>Imputation Parameter</label>
        <form class="ui form">
          <div class="field">
            <label>Number of iteration</label>
            <input type="text" placeholder="1" value="1">
          </div>
          <div class="field">
            <label>Select variables</label>
            <select multiple="" class="ui dropdown">
              <option value="activity">activity</option>
              <option value="resource">resource</option>
              <option value="attribute1">attr1</option>
              <option value="attribute2">attr2</option>
            </select>
          </div>
          <button>Analyze</button>
        </form>

      <label>Expectation Result</label>
      <div class="ui two column grid">
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Expected percentage of repairing rate</label>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
            </div>
            <div class="content">
              <label>Expected processing time</label>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '../../../common/ui/layout/class/BaseComponent';

@Component
export default class ContentFilterMissingImputationComponent extends BaseComponent {

  public selectedMethod: string = 'deletion';
  public mounted(): void {
    //
  }

  public methodChanged(ev: any): void {
    this.selectedMethod = String($('input[name=method]:checked').val());
  }
}
</script>
