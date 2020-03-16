<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content mssql properties component">
    <form class="ui form">
      <h4 class="ui dividing header">Connection Name</h4>
        <div class="field">
          <input type="text" name="connect[name]" placeholder="Hadoop Filesystem">
        </div>
      <h4 class="ui dividing header">Connection Properties</h4>
        <div class="two fields">
          <div class="field">
            <label>Host</label>
            <input type="text" name="connect[hostname]">
          </div>
          <div class="field">
            <label>Port</label>
            <input type="text" name="connect[port]" placeholder="27017">
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
 * Content Hadoop properties component.
 *
 * @export
 * @class ContentHadoopPropertiesComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentHadoopPropertiesComponent extends BaseComponent {
  //
}
</script>
