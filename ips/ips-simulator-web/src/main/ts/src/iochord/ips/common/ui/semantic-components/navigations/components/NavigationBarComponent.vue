<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="navigation-bar component">
    <div v-bind:class="[type, color, size]" class="ui menu">
      <slot name="menu-item"></slot>
    </div>
  </div>
</template>

<style scoped>
.ui.menu {
  width: 100%;
}
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';

@Component

/**
 * Navigation bar component.
 *
 * @export
 * @class NavigationBarComponent
 * @extends {SemanticComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class NavigationBarComponent extends SemanticComponent {

  /**
   * Type of navigation bar component.
   *
   * @private
   * @type {string}
   * @memberof NavigationBarComponent
   */
  @Prop() private type!: string;

  /**
   * Color of navigation bar component.
   *
   * @private
   * @type {string}
   * @memberof NavigationBarComponent
   */
  @Prop() private color!: string;

  /**
   * Size of navigation bar component.
   *
   * @private
   * @type {string}
   * @memberof NavigationBarComponent
   */
  @Prop() private size!: string;
}
</script>

