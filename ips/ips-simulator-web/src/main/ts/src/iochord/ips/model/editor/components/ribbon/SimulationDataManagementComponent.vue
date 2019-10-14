<template>
  <div class="simulation data management component" style="display:flex">
    <div class="item"><div class="header"><strong>Simulation Data Management</strong></div></div>
    <div class="item">
      <div class="ui basic icon buttons">
        <a class="ui button" title="Save model" @click="doSaveModel()"><i class="save icon"></i></a>
        <a class="ui button" title="Upload model" @click="showUploadFileModal"><i class="upload icon"></i></a>
        <a class="ui button" title="Download model"><i class="download icon"></i></a>
        <a class="ui button" title="Show report"><i class="file outline alternate icon"></i></a>
      </div>
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
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

import { IsmModelService } from '@/iochord/ips/common/service/model/IsmModelService';

const graphModule = getModule(GraphModule);

declare const $: any;

@Component
export default class SimulationDataManagementComponent extends BaseComponent {
  public modelPaneIsOpen: boolean = true;

  private doSaveModel(): void {
    // console.log('WS-REQUEST', graphModule.graph);
    // IsmModelService.getInstance().callSaveModel(graphModule.graph, (tick: any) => {
    //   const graph = JSON.parse(tick.body);
    //   console.log('WS-RESPONSE', graph);
    //   alert('saved !');
    // });

    console.log(graphModule.serializedGraph);
  }

  private showUploadFileModal(): void {
    $('.ui.upload.file.modal').modal('show');
  }
}
</script>
