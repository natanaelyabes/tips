<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content query component">
    <form class="ui form">
      <h4 class="ui dividing header">Filter Query</h4>
        <div class="field">
          <textarea></textarea>
        </div>
        <div class="inline fields">
          <div class="eight wide field">

          </div>
          <div class="eight wide field">
            <button class="ui primary button">
              Execute & Preview
            </button>
            <button class="ui button">
              Reset Query
            </button>
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
 * Content query component.
 *
 * @export
 * @class ContentQueryComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentQueryComponent extends BaseComponent {
  //
}
</script>
