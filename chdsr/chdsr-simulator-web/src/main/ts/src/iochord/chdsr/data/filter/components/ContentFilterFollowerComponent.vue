<template>
  <div class="content filter follower component">
    <form class="ui form">
      <h4 class="ui dividing header">Follow Relations</h4>
        <div class="two fields">
          <div class="inline four wide field">
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
          <div class="inline eight wide field">
            <label>Reference event value must be</label>
            <!-- <div class=" field"> -->
              <select>
                <option selected="" value="never">never followed</option>
                <option value="eventually">eventually followed</option>
                <option value="directly">directly followed</option>
                <option value="never">never followed</option>
              </select>
              <label>by a follower event</label>
            <!-- </div> -->
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
            <div class="inline field">
              <a class="ui basic button item">
                <div class="image-icon">
                  <img src="@/assets/images/icons/data_management_filter/check.png" alt="" class="ui image" />
                </div>
              </a>
              <a class="ui basic button item">
                <div class="image-icon">
                  <img src="@/assets/images/icons/data_management_filter/cancel.png" alt="" class="ui image" />
                </div>
              </a>
            </div>
            <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
              <table class="ui celled striped table">
                <thead>
                  <tr>
                    <th></th>
                    <th>Reference value (Origin)</th>
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
            <div class="inline field">
              <a class="ui basic button item">
                <div class="image-icon">
                  <img src="@/assets/images/icons/data_management_filter/check.png" alt="" class="ui image" />
                </div>
              </a>
              <a class="ui basic button item">
                <div class="image-icon">
                  <img src="@/assets/images/icons/data_management_filter/cancel.png" alt="" class="ui image" />
                </div>
              </a>
            </div>
            <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
              <table class="ui celled striped table">
                <thead>
                  <tr>
                    <th></th>
                    <th>Follower value (target)</th>
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
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

@Component
export default class ContentFilterFollowerComponent extends BaseComponent {

}
</script>
