<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="simulation player component item" style="display:flex">
    <div class="ui basic icon buttons">
      <button title="Step Backward" :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="step backward icon"></i></button>
      <button title="Rewind" :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="backward icon"></i></button>
      <button :title="(isPlaying ? 'Stop' : 'Play')" :class="'ui button ' + (isPlaying ? 'primary' : '')" @click="$emit(isPlaying ? 'stop' : 'play')"><i :class="(isPlaying ? 'stop' : 'play') + ' icon'"></i></button>
      <button title="Forward" :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="forward icon"></i></button>
      <button title="Step Forward" :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="step forward icon"></i></button>
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
 * The simulation player component.
 *
 * @export
 * @class SimulationPlayerComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class SimulationPlayerComponent extends BaseComponent {

  /**
   * Indicates whether a simulation player is in the playing state. False otherwise.
   *
   * @type {boolean}
   * @memberof SimulationPlayerComponent
   */
  @Prop({ default: false }) public isPlaying?: boolean;
}
</script>
