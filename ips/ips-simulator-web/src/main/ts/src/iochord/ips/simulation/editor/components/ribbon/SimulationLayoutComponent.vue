<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="simulation layout component item" style="display:flex">
    <div class="ui basic icon buttons">
      <div class="ui basic icon buttons">
        <button @click="$emit('autolayout')" title="Auto Layout" :class="'ui button ' + (isDisabled ? 'disabled' : '')" :disabled="isDisabled"><i class="shuffle icon"></i></button>
      </div>
    </div>
  </div>
</template>

<style>
.ui.basic.icon.button {
  margin-left: 2em;
}
</style>

<script lang="ts">
import { Prop, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * The simulation layout component.
 *
 * @export
 * @class SimulationLayoutComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class SimulationLayoutComponent extends BaseComponent {

  /**
   * Indicates whether a simulation layout component is being disabled. False otherwise.
   *
   * @type {boolean}
   * @memberof SimulationLayoutComponent
   */
  @Prop({ default: false }) public isDisabled?: boolean;
}
</script>
