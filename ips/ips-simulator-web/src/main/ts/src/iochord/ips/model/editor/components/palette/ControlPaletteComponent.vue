<template>
  <div class="control palette component">
    <div class="item">
      <div class="header">Control</div>
      <div class="menu">
        <a class="ui basic button item">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/control/control.png" alt="" class="ui centered image" />
          </div>
          Control
        </a>
        <a class="ui basic button item">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/control/configuration.png" alt="" class="ui centered image" />
          </div>
          Configuration
        </a>
      </div>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component
export default class ControlPaletteComponent extends BaseComponent {
  //
}
</script>
