<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content filter missing imputation component">
    <div class="ui basic segment">
      <section>
        <h4 class="ui dividing header">Missing Data Filter</h4>
        <h5>Data summary</h5>
        <div class="ui two column grid">
          <div class="column">
            <div class="ui fluid card" style="height: 375px">
              <div class="image">
                <BarChart :model="barData1"/>
              </div>
              <div class="content">
                <label>Number of missing events by case <br />(Case 별 missing event 수)</label>
              </div>
            </div>
          </div>
          <div class="column">
            <div class="ui fluid card" style="height: 375px">
              <div class="image" style="height: 305px">
                <div class="ui two statistics">
                  <div class="statistic">
                    <div class="value">6 %</div>
                    <div class="label">Timestamp</div>
                  </div>
                  <div class="statistic">
                    <div class="value">12 %</div>
                    <div class="label">Resource</div>
                  </div>
                  <div class="statistic">
                    <div class="value">1 %</div>
                    <div class="label">Attr A</div>
                  </div>
                  <div class="statistic">
                    <div class="value">6 %</div>
                    <div class="label">Attr B</div>
                  </div>
                  <div class="statistic">
                    <div class="value">1 %</div>
                    <div class="label">Attr C</div>
                  </div>
                  <div class="statistic">
                    <div class="value">6 %</div>
                    <div class="label">Attr D</div>
                  </div>
                </div>
              </div>
              <div class="content">
                <label>Percentage of missing events by attributes <br />(속성별 missing event %)</label>
              </div>
            </div>
          </div>
        </div>
      </section>

      <div class="ui hidden divider"></div>

      <section>
        <h5 class="ui horizontal left aligned divider header">Data handling methods</h5>
        <div class="ui form">
          <div class="grouped fields">
            <div class="field">
              <div class="ui slider checkbox">
                <input type="radio" name="method" checked="checked" value="deletion" @change="methodChanged($event)">
                <label>Simple deletion method (expected data lost rate: <span class="ui red text"> {{ expectedDataLoseRate }}</span> %)</label>
              </div>
            </div>
            <div class="field">
              <div class="ui slider checkbox checked">
                <input type="radio" name="method" value="imputation" @change="methodChanged($event)">
                <label>Event imputation method (expected restoration rate: <span class="ui red text"> {{ expectedRestorationRate }}</span> %)</label>
              </div>
            </div>
          </div>
        </div>
      </section>

      <div class="ui hidden divider"></div>

      <!-- Data deletion results -->
      <section  v-if=" selectedMethod == 'deletion'">
        <form class="ui form">
          <button class="ui primary button">Analyze</button>
        </form>
        <h5 class="ui horizontal left aligned divider header">Simple Deletion Results</h5>
        <div class="ui three statistics">
          <div class="statistic">
            <div class="value">400</div>
            <div class="label">Number of Events Deleted</div>
          </div>
          <div class="statistic">
            <div class="value">400</div>
            <div class="label">Number of Cases Deleted</div>
          </div>
          <div class="statistic">
            <div class="value">10 %</div>
            <div class="label">Data Loss Rate</div>
          </div>
        </div>
        <div class="ui hidden divider" />
        <IpsTable :dataHeader="headerTest" :model="dataModelTest" :pagination="true"/>
      </section>

      <!-- Data imputation input parameter & results -->
      <section v-if=" selectedMethod == 'imputation'">
        <form class="ui form">
          <div class="fields">
            <div class="field">
              <label>Number of iteration</label>
              <input type="text" placeholder="1" value="1">
            </div>
            <div class="field">
              <label>Select variables</label>
              <select class="ui fluid dropdown" v-model="selected" >
                <option v-for="option in atrOptions" :value="option.id" :key="option.id">{{option.text}} </option>
              </select>
            </div>
          </div>
          <button class="ui primary button">Analyze</button>
        </form>

        <h5 class="ui horizontal left aligned divider header">Event Imputation Result</h5>
        <div class="ui two statistics">
          <div class="statistic">
            <div class="value">95 ~ 97 %</div>
            <div class="label">Expected percentage of repairing rate</div>
          </div>
          <div class="statistic">
            <div class="value">3 ~ 10 min</div>
            <div class="label">Expected processing time</div>
          </div>
        </div>
        <div class="ui hidden divider"></div>
        <IpsTable :dataHeader="headerTest" :model="dataModelTest" :pagination="true"/>
      </section>
    </div>
  </div>
</template>

<style scoped>
.eq-card.ui.card {
  flex: 1; /* Shrink and grow according to available height */
  flex-basis: 0;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '../../../common/ui/layout/class/BaseComponent';
import BarChart from '../../../common/charts/BarChart.vue';
import IpsTable from '../../../common/tables/IpsTable.vue';

@Component({
  components: {
    BarChart,
    IpsTable,
  },
})

/**
 * Content filter follower component.
 *
 * @export
 * @class ContentFilterFollowerComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentFilterMissingImputationComponent extends BaseComponent {

  /**
   * Method for filter missing imputation.
   *
   * @type {string}
   * @memberof ContentFilterMissingImputationComponent
   */
  public selectedMethod: string = 'deletion';

  /**
   * Data for bar chart 1.
   *
   * @type {*}
   * @memberof ContentFilterMissingImputationComponent
   */
  public barData1: any = [];

  /**
   * Data for bar chart 2.
   *
   * @type {*}
   * @memberof ContentFilterMissingImputationComponent
   */
  public barData2: any = [];

  /**
   * Data for bar chart 3.
   *
   * @type {*}
   * @memberof ContentFilterMissingImputationComponent
   */
  public barData3: any = [];

  /**
   * Data for header.
   *
   * @type {*}
   * @memberof ContentFilterMissingImputationComponent
   */
  public headerTest: any = [];

  /**
   * Data for model.
   *
   * @type {*}
   * @memberof ContentFilterMissingImputationComponent
   */
  public dataModelTest: any = [];

  /**
   * List of selected items.
   *
   * @type {*}
   * @memberof ContentFilterMissingImputationComponent
   */
  public selected: any = [];

  /**
   * List of attribute options.
   *
   * @type {*}
   * @memberof ContentFilterMissingImputationComponent
   */
  public atrOptions: any = [];

  /**
   * The desired data lose rate if the missing imputation filter is applied.
   *
   * @type {number}
   * @memberof ContentFilterMissingImputationComponent
   */
  public expectedDataLoseRate: number = 10.0;

  /**
   * The desired restoration rate if the missing imputation filter is applied.
   *
   * @type {number}
   * @memberof ContentFilterMissingImputationComponent
   */
  public expectedRestorationRate: number = 78.6;

  /**
   * Vue mounted lifecycle.
   *
   * @memberof ContentFilterMissingImputationComponent
   */
  public mounted(): void {
    this.barData2 = [
      { name: 'Timestamp', value: 3, type: 'Case 10' },
      { name: 'Resource', value: 5, type: 'Case 10' },
      { name: 'Attribute 1', value: 2, type: 'Case 10' },
      { name: 'Timestamp', value: 2, type: 'Case 24' },
      { name: 'Resource', value: 2, type: 'Case 24' },
      { name: 'Attribute 1', value: 2, type: 'Case 24' },
      { name: 'Attribute 2', value: 2, type: 'Case 24' },
      { name: 'Timestamp', value: 3, type: 'Case 64' },
      { name: 'Timestamp', value: 4, type: 'Case 2' },
    ];

    this.barData1 = [
      { name: 'Case 10', value: 10, type: 'missing event' },
      { name: 'Case 24', value: 8, type: 'missing event' },
      { name: 'Case 64', value: 3, type: 'missing event' },
      { name: 'Case 2', value: 4, type: 'missing event' },
    ];

    this.barData3 = [
      { name: 'Timestamp', value: 3, type: 'missing event' },
      { name: 'Resource', value: 5, type: 'missing event' },
      { name: 'Attribute 1', value: 2, type: 'missing event' },
      { name: 'Timestamp', value: 2, type: 'missing event' },
      { name: 'Resource', value: 2, type: 'missing event' },
      { name: 'Attribute 1', value: 2, type: 'missing event' },
      { name: 'Attribute 2', value: 2, type: 'missing event' },
      { name: 'Timestamp', value: 3, type: 'missing event' },
      { name: 'Timestamp', value: 4, type: 'missing event' },
    ];

    // Table data format
    this.headerTest = ['Case ID', 'No. of Event', 'Missing Category', 'No. of Missing Event'];
    this.dataModelTest = [[1, 14, 'Resource', 3], [2, 12, 'Timestamp', 2], [3, 10, 'Attr <key, value>', 5]];

    // Select option format
    this.atrOptions = [
      {id: 1, text: 'attribute 1'},
      {id: 2, text: 'attribute 2'},
      {id: 3, text: 'attribute 3'},
      {id: 4, text: 'attribute 4'},
      {id: 5, text: 'attribute 5'},
      {id: 6, text: 'activity'},
      {id: 7, text: 'resource'},
    ];
  }

  /**
   * Listens to the changed value for method selection.
   *
   * @param {*} ev
   * @memberof ContentFilterMissingImputationComponent
   */
  public methodChanged(ev: any): void {
    this.selectedMethod = String($('input[name=method]:checked').val());
  }
}
</script>
