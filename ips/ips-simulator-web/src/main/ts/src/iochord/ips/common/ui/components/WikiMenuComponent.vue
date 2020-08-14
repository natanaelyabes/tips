<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="wiki-menu component">
    <div class="ui compact message">
      <div class="ui center aligned header">{{ title }} [<a href="#" v-on:click="toggleWiki()">{{ status }}</a>]</div>
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
import { Component, Prop } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Wiki menu component.
 *
 * @export
 * @class WikiMenuComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class WikiMenuComponent extends BaseComponent {

  /**
   * Title of the wiki menu component
   *
   * @private
   * @type {string}
   * @memberof WikiMenuComponent
   */
  @Prop() private title!: string;

  /**
   * Indicates whether a wiki menu is shown. False otherwise.
   *
   * @private
   * @type {boolean}
   * @memberof WikiMenuComponent
   */
  private shown: boolean = true;

  /**
   * Indicates whether a wiki is shown or hidden.
   *
   * @private
   * @type {string}
   * @memberof WikiMenuComponent
   */
  private status: string = 'hide';

  /**
   * Toggle the wiki menu.
   *
   * @private
   * @memberof WikiMenuComponent
   */
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
