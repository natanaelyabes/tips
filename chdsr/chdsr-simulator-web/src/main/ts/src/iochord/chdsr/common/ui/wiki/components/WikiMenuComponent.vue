<template>
  <div class="wiki-menu component">
    <div class="ui compact message">
      <div class="ui center aligned header">{{ title }} [<a href="#" v-on:click="fn_wiki_toggle_wiki($event)">{{ status }}</a>]</div>
      <slot v-bind:shown="shown" name="menu"></slot>
    </div>
  </div>
</template>

<style scoped>
.wiki-menu.component .ui.compact.message {
  z-index: 9999;
}
</style>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class WikiMenuComponent extends Vue {

  @Prop() private title!: string;

  private shown: boolean = true;
  private status: string = 'hide';

  private fn_wiki_toggle_wiki(): void {
    if (this.shown) {
      this.shown = false;
      this.status = 'show';
    } else {
      this.shown = true;
      this.status = 'hide';
    }
  }
}
</script>
