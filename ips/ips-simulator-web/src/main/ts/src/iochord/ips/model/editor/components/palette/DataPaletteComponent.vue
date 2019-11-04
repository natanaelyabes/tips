<template>
  <div class="data palette component">
    <div class="item">
      <div class="header">Data Toolbox</div>
      <div class="menu">
        <a title="datatable" :disabled="isDisabled" @mousedown="handleToolboxMouseDown(DATA, $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" >
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/datatable.png" alt="" class="ui centered image" />
          </div>
          Data Table
        </a>
        <a title="generator" :disabled="isDisabled" @mousedown="handleToolboxMouseDown(DATA, $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" >
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/generator.png" alt="" class="ui centered image" />
          </div>
          Generator
        </a>
        <a title="objecttype" :disabled="isDisabled" @mousedown="handleToolboxMouseDown(DATA, $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" >
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/object_type.png" alt="" class="ui centered image" />
          </div>
          Object Type
        </a>
        <a title="function" :disabled="isDisabled" @mousedown="handleToolboxMouseDown(DATA, $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" >
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/function.png" alt="" class="ui centered image" />
          </div>
          Function / Method
        </a>
        <a title="resource" :disabled="isDisabled" @mousedown="handleToolboxMouseDown(DATA, $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" >
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/resources.png" alt="" class="ui centered image" />
          </div>
          Resources
        </a>
        <!-- <a title="monitor" :disabled="isDisabled" @mousedown="handleToolboxMouseDown(DATA, $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" >
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/kpi.png" alt="" class="ui centered image" />
          </div>
          Monitor KPI
        </a> -->
        <a title="queue" :disabled="isDisabled" @mousedown="handleToolboxMouseDown(DATA, $event)" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" >
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/queue.png" alt="" class="ui centered image" />
          </div>
          Queue
        </a>
      </div>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Prop, Component, Mixins } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import PaletteMixin, { TOOLBOX } from '../../mixins/editors/PaletteMixin';

@Component
export default class DataPaletteComponent extends Mixins(BaseComponent, PaletteMixin) {
  @Prop({ default: false })
  public isDisabled?: boolean;

  public get DATA(): TOOLBOX {
    return TOOLBOX.DATA;
  }
}
</script>
