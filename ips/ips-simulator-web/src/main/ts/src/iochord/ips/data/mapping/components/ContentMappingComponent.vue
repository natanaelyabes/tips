<template>
  <div class="content mapping component">
    <form class="ui form">
      <div class="two fields">
        <div class="field">
          <label>Mapping name</label>
          <input type="text" name="connect[name]" placeholder="Mapping 01">
        </div>
        <div class="field">
          <label>Mapping configuration</label>
          <input type="text" name="connect[name]" placeholder="Configuration...">
        </div>
      </div>
      <div class="inline fields">
        <div class="field">
          <button class="ui primary button">
            Map
          </button>
        </div>
        <div class="field">
          <button class="ui button">
            Cancel
          </button>
        </div>
      </div>
      <div class="inline fields">
        <div class="field">
          <a class="ui basic button item">
            <div class="image-icon">
              <img src="@/assets/images/icons/data_management_mapping/case-id.png" alt="" class="ui image" />
            </div>
          </a>
        </div>
        <div class="field">
          <a class="ui basic button item">
            <div class="image-icon">
              <img src="@/assets/images/icons/data_management_mapping/activity.png" alt="" class="ui image" />
            </div>
          </a>
        </div>
        <div class="field">
          <a class="ui basic button item">
            <div class="image-icon">
              <img src="@/assets/images/icons/data_management_mapping/timestamp.png" alt="" class="ui image" />
            </div>
          </a>
        </div>
        <div class="field">
          <a class="ui basic button item">
            <div class="image-icon">
              <img src="@/assets/images/icons/data_management_mapping/resource.png" alt="" class="ui image" />
            </div>
          </a>
        </div>
        <div class="field">
          <a class="ui basic button item">
            <div class="image-icon">
              <img src="@/assets/images/icons/data_management_mapping/case-id-attribute.png" alt="" class="ui image" />
            </div>
          </a>
        </div>
        <div class="field">
          <a class="ui basic button item">
            <div class="image-icon">
              <img src="@/assets/images/icons/data_management_mapping/event-attribute.png" alt="" class="ui image" />
            </div>
          </a>
        </div>
        <div class="field">
          <a class="ui basic button item">
            <div class="image-icon">
              <img src="@/assets/images/icons/data_management_mapping/unused-field.png" alt="" class="ui image" />
            </div>
          </a>
        </div>
      </div>
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
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import ContentDataViewComponent from '../../SandboxDataConnection/components/ContentDataViewComponent.vue';


@Component
export default class ContentMappingComponent extends BaseComponent {

}
</script>