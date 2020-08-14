<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="content settings component">
    <div class="ui basic segment">
      <form class="ui form">
        <div class="two fields">
          <div class="field">
            <label>Algorithms</label>
            <select class="ui search dropdown">
              <option>Algorithm A</option>
              <option>Algorithm B</option>
            </select>
          </div>
        </div>
        <div class="inline fields">
          <div class="field">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/data_management_filter/check.png" alt="" class="ui image" />
              </div>
            </a>
          </div>
          <div class="field">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/data_management_filter/cancel.png" alt="" class="ui image" />
              </div>
            </a>
          </div>
          <div class="field">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/data_management_filter/target-class.png" alt="" class="ui image" />
              </div>
            </a>
          </div>
          <div class="field">
            <button class="ui primary button">
              Calculate
            </button>
          </div>
        </div>
        <div class="ui hidden divider"></div>
        <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
          <table class="ui celled striped table">
            <thead>
              <tr>
                <th><a class="ui button">Column 1</a></th>
                <th><a class="ui button">Column 2</a></th>
                <th><a class="ui button">Column 3</a></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="selectable">
                  node_modules
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  test
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  build
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  package.json
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  Gruntfile.js
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  build
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  package.json
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  Gruntfile.js
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  build
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  package.json
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  Gruntfile.js
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  build
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  package.json
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  Gruntfile.js
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  build
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  package.json
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
              <tr>
                <td>
                  Gruntfile.js
                </td>
                <td>Initial commit</td>
                <td class="right aligned">10 hours ago</td>
              </tr>
            </tbody>
          </table>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';

declare const $: any;

@Component

/**
 * Settings component to set branch mining parameters or properties.
 *
 * @export
 * @class ContentSettingsComponent
 * @extends BaseComponent
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class ContentSettingsComponent extends SemanticComponent {
  public declareSemanticModules(): void {
    $('.dropdown').dropdown();
  }
}
</script>
