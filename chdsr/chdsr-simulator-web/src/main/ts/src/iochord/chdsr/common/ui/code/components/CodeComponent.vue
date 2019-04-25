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
import { Component, Prop, Vue } from 'vue-property-decorator';

import '../../../../../node_modules/highlight.js/styles/default.css';
import * as hljs from 'highlight.js';

@Component
export default class CodeComponent extends Vue {
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
