<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="footer component">
    <footer>
      <p>Copyright &copy; {{currentYear}} Business Service Computing Laboratory. All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>
.footer.component{
  position: static;
  bottom: 1em;
  margin: 2em 0em 0em;
}
</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Footer component.
 *
 * @export
 * @class FooterComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class FooterComponent extends BaseComponent {

  /**
   * The current date.
   *
   * @private
   * @type {Date}
   * @memberof FooterComponent
   */
  private currentDate: Date = new Date();

  /**
   * Returns the year of current date object.
   *
   * @readonly
   * @private
   * @type {number}
   * @memberof FooterComponent
   */
  private get currentYear(): number {
    return this.currentDate.getFullYear();
  }
}
</script>
