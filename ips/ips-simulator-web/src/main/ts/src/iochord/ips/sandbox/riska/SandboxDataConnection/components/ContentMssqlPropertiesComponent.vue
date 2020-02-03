<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="content mssql properties component">
    <form class="ui form">
      <h4 class="ui dividing header">Connection Name</h4>
        <div class="twelve wide field">
          <input type="text" name="connect[name]" placeholder="Microsoft SQL Server">
        </div>
      <h4 class="ui dividing header">Database Properties</h4>
        <div class="three fields">
          <div class="field">
            <label>Server</label>
            <input type="text" name="connect[server]">
          </div>
          <div class="field">
            <label>Port</label>
            <input type="text" name="connect[port]" placeholder="1521">
          </div>
          <div class="field">
            <label>Database</label>
            <input type="text" name="connect[database]">
          </div>
        </div>
        <div class="inline fields">
          <div class="three wide field">
            <div class="ui checkbox">
              <input type="checkbox" tabindex="0" class="hidden">
              <label>Encrypt</label>
            </div>
          </div>
          <div class="five wide field">
            <label>Minimum TLS</label>
            <select>
              <option selected="" value="1.0">1.0</option>
              <option value="1.1">1.1</option>
              <option value="1.2">1.2</option>
            </select>
          </div>
        </div>
      <h4 class="ui dividing header">Authentication</h4>
      <div class="fields">
        <div class="eight wide field">
          <div class="ui checkbox">
              <input type="checkbox" tabindex="0" class="hidden">
              <label>Use trusted connection</label>
          </div>
        </div>
        <div class="eight wide field">
          <div class="ui checkbox">
              <input type="checkbox" tabindex="0" class="hidden">
              <label>Use NTLM</label>
          </div>
        </div>
      </div>
      <div class="fields">
        <div class="eight wide field">
          <label>Server SPN</label>
          <input type="text" name="connect[spn]">
        </div>
        <div class="eight wide field">
          <label>Username</label>
          <input type="text" name="connect[username]">
        </div>
        <div class="eight wide field">
          <label>Password</label>
          <input type="text" name="connect[password]">
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
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class ContentMssqlPropertiesComponent extends BaseComponent {

}
</script>
