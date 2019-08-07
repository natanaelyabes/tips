<template>
  <div class="simulation player component" style="display:flex">
    <div class="item"><div class="header"><strong>Simulation Player</strong></div></div>
    <div class="item">
      <div class="ui basic icon buttons">
        <a class="ui button"><i class="step backward icon"></i></a>
        <a class="ui button"><i class="backward icon"></i></a>
        <a class="ui button"><i class="play icon"></i></a>
        <a class="ui button"><i class="stop icon"></i></a>
        <a class="ui button"><i class="forward icon"></i></a>
        <a class="ui button"><i class="step forward icon"></i></a>
      </div>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

@Component
export default class SimulationPlayerComponent extends BaseComponent {
  //
}
</script>
