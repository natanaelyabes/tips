<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content mssql properties component">
    <form class="ui form">
      <h4 class="ui dividing header">Connection Name</h4>
        <div class="twelve wide field">
          <input type="text" name="connect[name]" placeholder="MySQL">
        </div>
      <h4 class="ui dividing header">Database Properties</h4>
        <div class="three fields">
          <div class="field">
            <label>Hostname</label>
            <input type="text" name="connect[hostname]">
          </div>
          <div class="field">
            <label>Port</label>
            <input type="text" name="connect[port]" placeholder="3306">
          </div>
          <div class="field">
            <label>Database</label>
            <input type="text" name="connect[database]">
          </div>
        </div>
      <h4 class="ui dividing header">Authentication</h4>
        <div class="two fields">
          <div class="field">
            <label>Username</label>
            <input type="text" name="connect[username]">
          </div>
          <div class="field">
            <label>Password</label>
            <input type="text" name="connect[password]">
          </div>
        </div>
      <h4 class="ui dividing header">SSL Options</h4>
        <div class="three fields">
          <div class="field">
            <label>SSL Mode</label>
              <select>
                <option selected="" value="prefer">prefer</option>
                <option value="verify-ca">verify-ca</option>
                <option value="verify-full">verify-full</option>
                <option value="require">require</option>
                <option value="prefer">prefer</option>
                <option value="allow">allow</option>
                <option value="disable">disable</option>
              </select>
          </div>
          <div class="field">
            <div class="ui checkbox">
              <input type="checkbox" tabindex="0" class="hidden">
              <label>Use system trust store</label>
            </div>
          </div>
          <div class="field">
            <label>Full path of trusted certificate</label>
            <input type="text" name="connect[certificate]">
          </div>
        </div>
      <h4 class="ui dividing header">Proxy</h4>
        <div class="three fields">
          <div class="field">
            <label>Proxy server</label>
            <input type="text" name="connect[proxyserver]">
          </div>
          <div class="field">
            <label>Proxy port</label>
            <input type="text" name="connect[proxyport]">
          </div>
          <div class="field">
            <label>Query timeout</label>
            <input type="text" name="connect[database]"  placeholder="30">
          </div>
        </div>
      <div class="inline fields">
        <div class="eight wide field">

        </div>
        <div class="eight wide field">
          <button class="ui primary button">
            Save
          </button>
          <button class="ui button">
            Discard
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<style scoped>

</style>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';


@Component

/**
 * Content MySQL properties component.
 *
 * @export
 * @class ContentMysqlPropertiesComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentMysqlPropertiesComponent extends BaseComponent {
  //
}
</script>
