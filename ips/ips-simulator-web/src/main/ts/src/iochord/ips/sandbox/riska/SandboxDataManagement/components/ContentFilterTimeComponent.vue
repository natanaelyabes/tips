<template>
  <div class="content filter time component">
    <form class="ui form">
      <h4 class="ui dividing header">Timeframe</h4>
        <div class="three fields">
          <div class="ten wide field">
            <textarea rows="11">this is for chart</textarea>
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
                  <input type="radio" name="preserve-case" tabindex="0" class="hidden">
                  <label>contained</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case" tabindex="0" class="hidden">
                  <label>intersecting</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case" tabindex="0" class="hidden">
                  <label>started</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case" tabindex="0" class="hidden">
                  <label>completed</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case" tabindex="0" class="hidden">
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
                <input type="checkbox" tabindex="0" class="hidden">
                <label>Define offsets</label>
              </div>
              <input type="text" placeholder="9">
            </div>
            <div class="field ui checkbox">
              <input type="checkbox" tabindex="0" class="hidden">
              <label>Define office hour</label>
            </div>
            <div class="inline fields">
              <div class="eight wide field center">
                <label>Start</label>
              </div>
                <input type="text" placeholder="9">
            </div>
            <div class="inline fields">
              <div class="eight wide field center">
                <label>End</label>
              </div>
                <input type="text" placeholder="18">
            </div>
            <div class="inline fields">
              <div class="eight wide field ui checkbox">
                <input type="checkbox" tabindex="0" class="hidden">
                <label>Define holidays</label>
              </div>
              <select>
                <option selected="" value="Custom">Custom...</option>
                <option value="Weekend">Weekend</option>
              </select>
            </div>
            <div class="three inline fields">
              <div class="field ui input">
                <input type="text" placeholder="Summer holiday">
              </div>
              <!-- range start-end selection date-time calender event, starts from today -->
              <div class="field ui calendar" id="standard_calendar"> 
                <div class="ui input left icon">
                  <i class="calendar icon"></i>
                  <input type="text" placeholder="Date">
                </div>
              </div>
              <button class="ui button">
                Add
              </button>
            </div>
            <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 150px; width: 100%">
              <table class="ui celled striped table">
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
              <div class="field ui calendar" id="date_calendar">
                <div class="ui input left icon">
                  <i class="calendar icon"></i>
                  <input type="text" placeholder="Date">
                </div>
              </div>
              <div class="field ui calendar" id="time_calendar">
                <div class="ui input left icon">
                  <i class="time icon"></i>
                  <input type="text" placeholder="Time">
                </div>
              </div>
            </div>
            <div class="three inline fields">
              <div class="field ui input">
                <label>End timeframe</label>
              </div>
              <div class="field ui calendar" id="date_calendar">
                <div class="ui input left icon">
                  <i class="calendar icon"></i>
                  <input type="text" placeholder="Date">
                </div>
              </div>
              <div class="field ui calendar" id="time_calendar">
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

@Component
export default class ContentFilterTimeComponent extends BaseComponent {
// $('#standard_calendar')
//   .calendar()
// ;
}
</script>
