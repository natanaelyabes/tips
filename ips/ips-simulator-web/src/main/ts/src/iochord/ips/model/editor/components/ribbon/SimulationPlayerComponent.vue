<template>
  <div class="simulation player component item" style="display:flex">
    <div class="ui basic icon buttons">
      <button :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="step backward icon"></i></button>
      <button :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="backward icon"></i></button>
      <button :class="'ui button ' + (isPlaying ? 'primary' : '')" @click="$emit(isPlaying ? 'stop' : 'play')"><i :class="(isPlaying ? 'stop' : 'play') + ' icon'"></i></button>
      <button :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="forward icon"></i></button>
      <button :class="'ui button ' + (isPlaying ? '' : 'disabled')" :disabled="!isPlaying"><i class="step forward icon"></i></button>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Prop, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component
export default class SimulationPlayerComponent extends BaseComponent {
  //
  @Prop({
    default: false,
  })
  public isPlaying?: boolean;

}
</script>
