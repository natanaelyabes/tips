<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
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

<script lang="ts">
import { Prop, Component, Mixins } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import PaletteMixin, { TOOLBOX } from '../../mixins/editors/PaletteMixin';

@Component

/**
 * The data palette component.
 *
 * @export
 * @class DataPaletteComponent
 * @extends {Mixins(BaseComponent, PaletteMixin)}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class DataPaletteComponent extends Mixins(BaseComponent, PaletteMixin) {

  /**
   * Indicates whether a data palette component is being disabled. False otherwise.
   *
   * @type {boolean}
   * @memberof DataPaletteComponent
   */
  @Prop({ default: false }) public isDisabled?: boolean;

  /**
   * Returns the TOOLBOX.DATA enumeration.
   *
   * @readonly
   * @type {TOOLBOX}
   * @memberof DataPaletteComponent
   */
  public get DATA(): TOOLBOX {
    return TOOLBOX.DATA;
  }
}
</script>
