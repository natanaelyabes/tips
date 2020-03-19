<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content filter points component">
    <form class="ui form">
      <h4 class="ui dividing header">Start/End Points</h4>
        <div class="two fields">
          <div class="inline four wide field">
            <label>Filter by</label>
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
          <div class="inline eight wide field">
            <div class="ui label">Preserve cases</div>
            <div class="grouped fields">
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case" checked="checked">
                  <label>discard case</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case">
                  <label>trim longest</label>
                </div>
              </div>
              <div class="field">
                <div class="ui radio checkbox">
                  <input type="radio" name="preserve-case">
                  <label>trim first</label>
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
        <div class="two fields">
          <div class="eight wide field">
            <div class="ui checkbox">
              <input type="checkbox">
              <label>Add artificial start activity</label>
            </div>
            <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
              <table class="ui celled striped table">
                <thead>
                  <tr>
                    <th></th>
                    <th>Start points</th>
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
          </div>
          <div class="eight wide field">
            <div class="ui checkbox">
              <input type="checkbox">
              <label>Add artificial end activity</label>
            </div>
            <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
              <table class="ui celled striped table">
                <thead>
                  <tr>
                    <th></th>
                    <th>End points</th>
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
          </div>
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
 * Content filter points component.
 *
 * @export
 * @class ContentFilterPointsComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentFilterPointsComponent extends BaseComponent {
  //
}
</script>
