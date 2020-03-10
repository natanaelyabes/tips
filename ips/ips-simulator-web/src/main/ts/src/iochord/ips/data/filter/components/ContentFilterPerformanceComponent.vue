<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content filter performance component">
    <form class="ui form">
      <h4 class="ui dividing header">Perfomance</h4>
        <div class="inline fields">
          <div class="field">
            <label>Category</label>
          </div>
          <div class="field">
            <select>
                <option selected="" value="Custom">Custom...</option>
                <option value="variant">Variant</option>
                <option value="duration">Case duration</option>
                <option value="event">Number of events</option>
                <option value="utilization">Case utilization</option>
              </select>
          </div>
        </div>
        <textarea>this is chart</textarea>
        <div class="ui hidden divider"></div>
        <div class="field">
          <label>Custom code</label>
        </div>
        <div class="field">
          <textarea></textarea>
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
    </form>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import * as $ from 'jquery';

@Component

/**
 * Content filter performance component.
 *
 * @export
 * @class ContentFilterPerformanceComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentFilterPerformanceComponent extends BaseComponent {
  //
}
</script>
