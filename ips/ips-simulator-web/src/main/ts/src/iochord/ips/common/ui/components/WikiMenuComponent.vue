<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="wiki-menu component">
    <div class="ui compact message">
      <div class="ui center aligned header">{{ title }} [<a href="#" v-on:click="toggleWiki($event)">{{ status }}</a>]</div>
      <slot v-bind:shown="shown" name="menu"></slot>
    </div>
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
.wiki-menu.component .ui.compact.message {
  z-index: 9999;
}
</style>

<script lang="ts">
import { Component, Prop } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class WikiMenuComponent extends BaseComponent {

  @Prop() private title!: string;

  private shown: boolean = true;
  private status: string = 'hide';

  private toggleWiki(): void {
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
