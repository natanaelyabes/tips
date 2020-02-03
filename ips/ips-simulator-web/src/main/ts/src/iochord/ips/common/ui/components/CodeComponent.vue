<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="code component">
    <pre><code v-bind:class="language"><slot name="code"></slot></code></pre>
  </div>
</template>

<style scoped>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
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


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class CodeComponent extends BaseComponent {
  @Prop() private code!: string;
  @Prop() private language!: string;

  private mounted(): void {
    const hljsInitHighlightning: any = hljs.initHighlighting;
    hljs.initHighlightingOnLoad();
    hljsInitHighlightning.called = false;
    hljs.initHighlighting();
  }
}
</script>
