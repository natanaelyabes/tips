<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="code component">
    <pre><code v-bind:class="language"><slot name="code"></slot></code></pre>
  </div>
</template>

<style scoped>
.code.component pre {
  white-space: pre-wrap;
  white-space: -moz-pre-wrap;
  white-space: -o-pre-wrap;
  word-wrap: break-word;
}
</style>

<script lang="ts">
import { Component, Prop } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

import '#root/node_modules/highlight.js/styles/default.css';
import * as hljs from 'highlight.js';

@Component

/**
 * Code component.
 *
 * @export
 * @class CodeComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class CodeComponent extends BaseComponent {

  /**
   * The code to be displayed by this component.
   *
   * @private
   * @type {string}
   * @memberof CodeComponent
   */
  @Prop() private code!: string;

  /**
   * The language of the code.
   *
   * @private
   * @type {string}
   * @memberof CodeComponent
   */
  @Prop() private language!: string;

  /**
   * Mounted lifecyle of the component.
   *
   * @private
   * @memberof CodeComponent
   */
  private mounted(): void {
    const hljsInitHighlightning: any = hljs.initHighlighting;
    hljs.initHighlightingOnLoad();
    hljsInitHighlightning.called = false;
    hljs.initHighlighting();
  }
}
</script>
