<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="simulation data management component item" style="display:flex">
    <!-- <div class="item"><div class="header"><strong>Data Management</strong></div></div> -->
    <div class="ui basic icon buttons">
      <button :class="'ui button' + (isDisabled ? ' disabled' : '')" title="Create new model" :disabled="isDisabled" @click="$emit('create')"><i class="file icon"></i></button>
      <button :class="'ui button' + (isDisabled ? ' disabled' : '')" title="Save model" :disabled="isDisabled" @click="$emit('save')"><i class="save icon"></i></button>
      <button :class="'ui button' + (isDisabled ? ' disabled' : '')" title="Load example model" :disabled="isDisabled" @click="$emit('example')"><i class="upload icon"></i></button>
      <button :class="'ui button' + (isDisabled ? ' disabled' : '')" title="Upload model" :disabled="isDisabled" @click="showUploadFileModal()"><i class="upload icon"></i></button>
      <button :class="'ui button' + (isDisabled ? ' disabled' : '')" title="Download model" :disabled="isDisabled" @click="$emit('download')"><i class="download icon"></i></button>
      <button :class="'ui button' + (isDisabled ? ' disabled' : '')" title="Show report" :disabled="isDisabled" @click="$emit('report')"><i class="file outline alternate icon"></i></button>
    </div>

    <!-- Upload Modals -->
    <div class="ui upload file modal">
      <i class="close icon"></i>
      <div class="header">
        Upload Model
      </div>
      <div class="content">
        <div class="description">
          <div class="ui header">Upload a json file by paste it in the form below.</div>
          <p>This is an experimental feature.</p>
          <div class="ui form">
            <div style="width:100%" class="ui labeled input">
              <textarea></textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui positive right labeled icon button">
          Upload
          <i class="upload icon"></i>
        </div>
        <div class="ui black deny button">
          Cancel
        </div>
      </div>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Prop, Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import axios, { AxiosResponse } from 'axios';

import { IsmModelService } from '@/iochord/ips/common/service/model/IsmModelService';

const graphModule = getModule(GraphModule);

declare const $: any;

@Component

/**
 * The simulation data management component.
 *
 * @export
 * @class SimulationDataManagementComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class SimulationDataManagementComponent extends BaseComponent {

  /**
   * Indicates that the simulation model is being disabled.
   *
   * @type {boolean}
   * @memberof SimulationDataManagementComponent
   */
  @Prop({ default: false }) public isDisabled?: boolean;

  /**
   * Indicates whether a model pane is being opened.
   *
   * @type {boolean}
   * @memberof SimulationDataManagementComponent
   */
  public modelPaneIsOpen: boolean = true;

  /**
   * Store the simulation model over HTTP REST protocol.
   *
   * @private
   * @returns {Promise<void>}
   * @memberof SimulationDataManagementComponent
   */
  private async doSaveModel(): Promise<void> {
    console.log('BEFORE: ' + JSON.stringify(graphModule.graph));

    const result = await axios.post('http://ips-api.tips.iochord.co.kr/ips/api/v1/model/ism/edit/MODEL', JSON.stringify(graphModule.graph), {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
    });

    console.log('AFTER: ' + JSON.stringify(result.data));
  }

  /**
   * Display the upload file modal.
   *
   * @private
   * @memberof SimulationDataManagementComponent
   */
  private showUploadFileModal(): void {
    $('.ui.upload.file.modal').modal('show');
  }
}
</script>
