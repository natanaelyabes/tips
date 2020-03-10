<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content oracle properties component">
    <form class="ui form">
      <h4 class="ui dividing header">Connection Name</h4>
        <div class="field">
          <input type="text" name="connect[name]" placeholder="Oracle">
        </div>
      <h4 class="ui dividing header">Database Properties</h4>
        <div class="three fields">
          <div class="field">
            <label>Hostname</label>
            <input type="text" name="connect[hostname]">
          </div>
          <div class="field">
            <label>Port</label>
            <input type="text" name="connect[hostname]" placeholder="1521">
          </div>
          <div class="field">
            <label>Database</label>
            <input type="text" name="connect[hostname]">
          </div>
        </div>
        <div class="inline fields">
          <div class="three wide field">
            <div class="ui checkbox">
              <input type="checkbox" tabindex="0" class="hidden">
              <label>Use TNS service name</label>
            </div>
          </div>
          <div class="five wide field">
            <label>TNS name</label>
            <input type="text" name="connect[tnsname]">
          </div>
        </div>
      <h4 class="ui dividing header">Authentication</h4>
      <div class="inline fields">
        <div class="eight wide field">
          <label>Username</label>
          <input type="text" name="connect[username]">
        </div>
        <div class="eight wide field">

        </div>
        <div class="eight wide field">
          <label>Password</label>
          <input type="text" name="connect[password]">
        </div>
        <div class="eight wide field">

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
    <h4 class="ui dividing header">Filter Query</h4>
    <form class="ui form">
      <div class="sixteen wide field">
        <textarea rows="10" cols="50"></textarea>
      </div>
      <div class="inline fields">
          <div class="eight wide field"></div>
          <div align="right" class="eight wide field">
            <button class="ui right floated button">
              Execute &#38; Preview
            </button>
          </div>
      </div>
    </form>

    <h4 class="ui dividing header">Data Preview</h4>
    <table class="ui celled table">
      <thead>
        <tr>
          <th>Hostname</th>
          <th>Port</th>
          <th>Database</th>
          <th>TNS Name</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
      </tbody>
    </table>
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
 * Content Oracle properties component.
 *
 * @export
 * @class ContentOraclePropertiesComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentOraclePropertiesComponent extends BaseComponent {
  //
}
</script>
