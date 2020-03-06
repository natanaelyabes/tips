<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content history component">
    <form class="ui form">
      <!-- history graph -->
      <div class="field">
        <textarea>this is where the history graph rendered</textarea>
      </div>
      <div class="field">
        <button class="ui button">
          Use data
        </button>
      </div>
      <h4 class="ui dividing header">Data Preview</h4>
        <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
          <table class="ui celled striped table">
            <thead>
              <tr>
                <th>Column 1</th>
                <th>Column 2</th>
                <th>Column 3</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="collapsing">
                  node_modules
                </td>
                <td>Initial commit</td>
                <td class="right aligned collapsing">10 hours ago</td>
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
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Content history component.
 *
 * @export
 * @class ContentHistoryComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentHistoryComponent extends BaseComponent {

}
</script>
