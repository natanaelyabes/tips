<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="content filter time component">
    <form class="ui form">
      <h4 class="ui dividing header">Timeframe</h4>
        <div class="three fields">
          <div class="ten wide field">
            <HistogramChart :model="[1,2,3,4,5,6,7,8,9,10]"/>
          </div>
          <div class="four wide field">
            <textarea rows="4">this is for chart</textarea>
            <div class="ui hidden divider"></div>
            <textarea rows="4">this is for chart</textarea>
          </div>
          <div class="four wide field">
            <div class="ui label">Preserve cases</div>
            <div class="grouped fields">
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case" checked="checked">
                  <label>contained</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case">
                  <label>intersecting</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case">
                  <label>started</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case">
                  <label>completed</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case">
                  <label>trim to frame</label>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="ui hidden divider"></div>
        <div class="two fields">
          <div class="eight wide field">
            <div class="inline fields">
              <div class="eight wide field ui checkbox">
                <input type="checkbox" id="timefilterOffsetCheckbox" value="offsets" @change="checkTimefilterOffsetCheckbox($event)" v-model="timefilterOffsetCheckboxModel">
                <label for="offsets">Define offsets</label>
              </div>
              <input type="text" placeholder="9" id="timefilterOffsetInput" value="" :disabled="offsetValidated ? true : false">
            </div>
            <div class="field ui checkbox">
              <input type="checkbox" id="timefilterOfficehourCheckbox" value="officeHour" @change="checkTimefilterOfficehourCheckbox($event)" v-model="checkTimefilterOfficehourCheckboxModel">
              <label for="officeHour">Define office hour</label>
            </div>
            <div class="inline fields">
              <div class="eight wide field center">
                <label>Start</label>
              </div>
                <input type="text" placeholder="9" id="timefilterOfficehourStartInput"  value="" :disabled="officeHourValidated ? true : false">
            </div>
            <div class="inline fields">
              <div class="eight wide field center">
                <label>End</label>
              </div>
                <input type="text" placeholder="18" id="timefilterOfficehourEndInput"  value="" :disabled="officeHourValidated ? true : false">
            </div>
            <div class="inline fields">
              <div class="eight wide field ui checkbox">
                <input type="checkbox" id="timefilterHolidaysCheckbox" value="holiday" @change="checkTimefilterHolidaysCheckbox($event)" v-model="checkTimefilterHolidaysCheckboxModel">
                <label for="holidays">Define holidays</label>
              </div>
              <select id="timefilterHolidaysOptions" :disabled="holidaysValidated ? true : false">
                <option selected="selected" value="Custom">Custom...</option>
                <option value="Weekend">Weekend</option>
              </select>
            </div>
            <div class="three inline fields">
              <div class="field ui input">
                <input type="text" placeholder="Summer holiday" id="timefilterHolidaysCustomInput"  :disabled="holidaysValidated ? true : false">
              </div>
              <!-- range start-end selection date-time calender event, starts from today -->
              <div class="field ui calendar" id="timefilterHolidaysCustomCalendar">
                <div class="ui input left icon">
                  <i class="calendar icon"></i>
                  <input type="text" placeholder="Date" :disabled="holidaysValidated ? true : false">
                </div>
              </div>
              <button class="ui button" :disabled="holidaysValidated ? true : false">
                Add
              </button>
            </div>
            <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 150px; width: 100%">
              <table class="ui celled striped table" id="timefilterHolidaysCustomTable" :disabled="holidaysValidated ? true : false">
                <thead>
                  <tr>
                    <th>Holiday name</th>
                    <th>Start date</th>
                    <th>End date</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Summer vacation</td>
                    <td class="right aligned">2019-07-28</td>
                    <td class="right aligned">2019-08-03</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="eight wide field">
            <div class="three inline fields">
              <div class="field ui input">
                <label>Start timeframe</label>
              </div>
              <div class="field">
                <div class="ui calendar" id="timefilterStdtTimeframeCalendar">
                  <div class="ui input left icon">
                    <i class="calendar icon"></i>
                    <input type="text" placeholder="Start date">
                  </div>
                </div>
              </div>
              <div class="field ui calendar" id="timefilterStdtTimeframeTs">
                <div class="ui input left icon">
                  <i class="time icon"></i>
                  <input type="text" placeholder="00:00">
                </div>
              </div>
            </div>
            <div class="three inline fields">
              <div class="field ui input">
                <label>End timeframe</label>
              </div>
              <div class="field ui calendar" id="timefilterEtdtTimeframeCalendar">
                <div class="ui input left icon">
                  <i class="calendar icon"></i>
                  <input type="text" placeholder="Date">
                </div>
              </div>
              <div class="field ui calendar" id="timefilterEtdtTimeframeTs">
                <div class="ui input left icon">
                  <i class="time icon"></i>
                  <input type="text" placeholder="Time">
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="field">
          <button class="ui primary button">
            Filter
          </button>
          <button class="ui button">
            Cancel
          </button>
        </div>
    </form>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import HistogramChart from '../../../common/charts/HistogramChart.vue';

// JQuery
declare const $: any;

@Component({
  components: {
    HistogramChart,
  },
})
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class ContentFilterTimeComponent extends BaseComponent {
// $('#standard_calendar')
//   .calendar()
// ;
  public timefilterOffsetCheckboxModel: string = '';
  public checkTimefilterOfficehourCheckboxModel: string = '';
  public checkTimefilterHolidaysCheckboxModel: string = '';

  public offsetValidated: boolean = true;
  public officeHourValidated: boolean = true;
  public holidaysValidated: boolean = true;

  public mounted(): void {
    console.log($('#timefilterStdtTimeframeCalendar'));

    $('#timefilterStdtTimeframeCalendar').calendar({
      type: 'date',
    });

    $('#timefilterStdtTimeframeTs').calendar({
      type: 'time',
    });

    $('#timefilterEtdtTimeframeCalendar').calendar({
      type: 'date',
    });

    $('#timefilterEtdtTimeframeTs').calendar({
      type: 'time',
    });

    $('#timefilterHolidaysCustomCalendar').calendar({
      type: 'date',
    });
  }

  public checkTimefilterOfficehourCheckbox(ev: any): void {
    // console.log(this.checkTimefilterOfficehourCheckboxModel);
    // console.log(ev);
    if (this.checkTimefilterOfficehourCheckboxModel) {
      this.officeHourValidated = false;
      // console.log(this.offsetValidated);
    } else {
      this.officeHourValidated = true;
      // console.log(this.offsetValidated);
    }
  }

  public checkTimefilterHolidaysCheckbox(ev: any): void {
    console.log(this.checkTimefilterHolidaysCheckboxModel);
    if (this.checkTimefilterHolidaysCheckboxModel) {
      this.holidaysValidated = false;
      // console.log(this.offsetValidated);
    } else {
      this.holidaysValidated = true;
      // console.log(this.offsetValidated);
    }
  }

  public checkTimefilterOffsetCheckbox(ev: any): void {
    console.log(this.timefilterOffsetCheckboxModel);

    if (this.timefilterOffsetCheckboxModel) {
      this.offsetValidated = false;
      // console.log(this.offsetValidated);
    } else {
      this.offsetValidated = true;
      // console.log(this.offsetValidated);
    }
  }
}
</script>
