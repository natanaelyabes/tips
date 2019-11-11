<template>
  <div class="content filter missing imputation component">
    <section>
      <h4 class="ui dividing header">Missing Data Filter</h4>
      <h5>Data summary</h5>
      <div class="ui three column grid">
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
              <BarChart :model="barData1"/>
            </div>
            <div class="content">
              <label>Number of missing event's attributes</label>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
              <BarChart :model="barData2"/>
            </div>
            <div class="content">
              <label>Number of missing events by case</label>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="ui fluid card">
            <div class="image">
              <BarChart :model="barData3"/>
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
      <h5 class="ui horizontal left aligned divider header">Data handling methods</h5>
      <div class="ui form">
        <div class="grouped fields">
          <div class="field">
            <div class="ui slider checkbox">
              <input type="radio" name="method" checked="checked" value="deletion" @change="methodChanged($event)">
              <label>Simple deletion method (%): <span class="ui red text"> {{ expectedDataLoseRate }}</span></label>
            </div>
          </div>
          <div class="field">
            <div class="ui slider checkbox checked">
              <input type="radio" name="method" value="imputation" @change="methodChanged($event)">
              <label>Event imputation method (%): <span class="ui red text"> {{ expectedRestorationRate }}</span> </label>
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
      <!-- <h5 class="ui horizontal left aligned divider header">Setting Parameters</h5> -->
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
              <!-- <option value="activity">activity</option>
              <option value="resource">resource</option>
              <option value="attribute1">attr1</option>
              <option value="attribute2">attr2</option> -->
            </select>
          </div>
        </div>
        <button class="ui primary button">Analyze</button>
      </form>

      <h5 class="ui horizontal left aligned divider header">Event Imputation Result</h5>
      <div class="ui two statistics">
        <div class="statistic">
          <div class="value">95 %</div>
          <div class="label">Expected percentage of repairing rate</div>
        </div>
        <div class="statistic">
          <div class="value">3 min</div>
          <div class="label">Expected processing time</div>
        </div>
      </div>
      <div class="ui hidden divider"></div>
      <IpsTable :dataHeader="headerTest" :model="dataModelTest" :pagination="true"/>
    </section>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '../../../common/ui/layout/class/BaseComponent';
import BarChart from '../../../common/charts/BarChart.vue';
import IpsTable from '../../../common/table/IpsTable.vue';

@Component({
  components: {
    BarChart,
    IpsTable,
  },
})
export default class ContentFilterMissingImputationComponent extends BaseComponent {

  public selectedMethod: string = 'deletion';
  public barData1: any = [];
  public barData2: any = [];
  public barData3: any = [];
  public headerTest: any = [];
  public dataModelTest: any = [];
  public selected: any = [];
  public atrOptions: any = [];
  public expectedDataLoseRate: number = 10.0;
  public expectedRestorationRate: number = 78.6;

  public mounted(): void {
    this.barData1 = [{ name: '1997', value: 86085, type: 'America' },
                { name: '2007', value: 144776, type: 'America' },
                { name: '2017', value: 193868, type: 'America' },
                { name: '1997', value: 9616, type: 'China' },
                { name: '2007', value: 35715, type: 'China' },
                { name: '2017', value: 122503, type: 'China' },
                { name: '1997', value: 44122, type: 'Japan' },
                { name: '2007', value: 45153, type: 'Japan' },
                { name: '2017', value: 48675, type: 'Japan' },
                { name: '1997', value: 22159, type: 'Germany' },
                { name: '2007', value: 34447, type: 'Germany' },
                { name: '2017', value: 36865, type: 'Germany' }];

    this.barData2 = [{ name: '1997', value: 86085, type: 'America' },
                { name: '2007', value: 144776, type: 'America' },
                { name: '2017', value: 193868, type: 'America' }];

    this.barData3 = [{ name: '1997', value: 22159, type: 'Germany' },
                { name: '2007', value: 34447, type: 'Germany' },
                { name: '2017', value: 36865, type: 'Germany' }];

    // table data format
    this.headerTest = ['Case ID', 'No. of Event', 'Missing Category', 'No. of Missing Attribute'];
    this.dataModelTest = [[1, 14, 'Resource', 3], [2, 12, 'Timestamp', 2], [3, 10, 'Attr <key, value>', 5]];

    // select option format
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

  public methodChanged(ev: any): void {
    this.selectedMethod = String($('input[name=method]:checked').val());
  }

}
</script>
