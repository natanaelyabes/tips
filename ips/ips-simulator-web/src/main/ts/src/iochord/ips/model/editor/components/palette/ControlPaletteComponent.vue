<template>
  <div class="control palette component">
    <div class="item">
      <div class="header">Control</div>
      <div class="menu">
        <a @click="openControl()" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" :disabled="isDisabled">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/control/control.png" alt="" class="ui centered image" />
          </div>
          Control
        </a>
        <a @click="openConfiguration()" :class="'ui basic button item' + (isDisabled ? ' disabled' : '')" :disabled="isDisabled">
          <div class="image-icon">
            <img src="@/assets/images/icons/simulation_editor_icon/control/configuration.png" alt="" class="ui centered image" />
          </div>
          Configuration
        </a>
      </div>
    </div>

    <div class="ui tiny control modal">
      <i class="close icon"></i>
      <div class="header">
        <h3 class="ui green header">Control</h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="four wide column">
                Replication Number
              </div>
              <div class="twelve wide column">
                <input type="number" v-model="replNum" id="ctrl_txt_replNum" />
              </div>
            </div>

            <div class="row">
              <div class="four wide column">
                Stopping Criteria
              </div>
              <div class="six wide column">
                <select id="ctrl_txt_stop" class="ui fluid search dropdown" v-model="stopCriteria">
                  <option value="TIME">Time</option>
                  <option value="STEPS">Steps</option>
                  <option value="CUSTOM">Custom</option>
                </select>
              </div>
              <div id="stop-crit" class="six wide column">
                <template v-if="stopCriteria !== 'CUSTOM'">
                  <input type="number" v-model="value" id="ctrl_txt_value" />
                </template>
                <select id="act_txtfunction" class="ui fluid search dropdown" v-model="this.function">
                  <option v-for="fx in functions" :key="fx.getId()" :value="fx.getId()">{{fx.getLabel()}} ({{fx.getId()}})</option>
                </select>
              </div>
            </div>

            <div class="row">
              <div class="four wide column">
                Start Simulation Time
              </div>
              <div class="twelve wide column">
                <div class="ui calendar" id="start-simulation-date">
                  <div class="ui fluid input left icon">
                    <i class="calendar icon"></i>
                    <input type="text" v-model="startSimulationDate" placeholder="Date/Time">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui positive button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <div class="ui overlay fullscreen configuration modal">
      <i class="close icon"></i>
      <div class="header">
        <h3 class="ui green header">Configuration</h3>
      </div>
      <div class="content" style="max-height: 75vh; overflow: auto">
        <template v-if="this.graph && this.graph.pages && this.graph.pages.get('0')">
          <table class="ui celled structured table">
          <thead>
            <tr>
              <th style="width: 5%;">No</th>
              <th style="width: 20%;">Element</th>
              <th colspan="6">Configuration</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="8">DATA TOOLBOX</td>
            </tr>
            <template v-for="(ele, ei) in this.graph.pages.get('0').data._values">
              <template v-if="ele.elementType && ele.elementType == 'generator'">
                <tr :key="ei">
                  <td>{{ ei }}</td>
                  <td contenteditable>{{ ele.label }}</td>
                  <td style="width: 10%;">Entity per Arrival</td>
                  <td contenteditable style="width: 15%;">{{ ele.entitiesPerArrival }}</td>
                  <td style="width: 10%;">Arrival Rate</td>
                  <td contenteditable style="width: 15%;">{{ ele.expression }}</td>
                  <td style="width: 10%;">Max Arrival</td>
                  <td contenteditable style="width: 15%;">{{ ele.maxArrival }}</td>
                </tr>
              </template>
              <template v-if="ele.elementType && ele.elementType == 'resource'">
                <tr :key="ei">
                  <td>{{ ei }}</td>
                  <td contenteditable>{{ ele.label }}</td>
                  <td>Number of Resource</td>
                  <td contenteditable>{{ ele.numberOfResource }}</td>
                  <td colspan="4">&nbsp;</td>
                </tr>
              </template>
            </template>
            <tr>
              <td colspan="8">NODE TOOLBOX</td>
            </tr>
            <template v-for="(ele, ei) in this.graph.pages.get('0').nodes._values">
              <template v-if="ele.elementType && ele.elementType == 'activity'">
                <tr :key="ei">
                  <td>{{ ei }}</td>
                  <td contenteditable>{{ ele.label }}</td>
                  <td>Setup Time</td>
                  <td contenteditable>{{ ele.setupTime }}</td>
                  <td>Processing Time (처리 시간)</td>
                  <td contenteditable>{{ ele.processingTime }}</td>
                  <td colspan="2">&nbsp;</td>
                </tr>
              </template>
            </template>
          </tbody>
          </table>
        </template>
      </div>
      <div class="actions">
        <div class="ui positive button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>
  </div>
</template>

<style>
#stop-crit .ui.fluid.search.dropdown {
  display: none;
}
</style>

<script lang="ts">
import { Prop, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { TSMap } from 'typescript-map';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';

declare const $: any;

const graphModule = getModule(GraphModule);

@Component
export default class ControlPaletteComponent extends BaseComponent {
  @Prop({ default: false })
  public isDisabled?: boolean;

  private replNum?: number = 0;
  private stopCriteria?: string = 'TIME';
  private value?: number = 0;
  private startSimulationDate?: string = '';
  private function?: string = '';

  private graph: any = null;

  public openControl(): void {
    $('.ui.control.modal').modal('show');

    // Initialize dropdown with default value
    $('#ctrl_txt_stop')
      .dropdown('set selected', this.stopCriteria)
      .dropdown({
        onChange: (val: string) => {
          if (val === 'CUSTOM') {
            $('#stop-crit .ui.fluid.search.dropdown').css('display', 'block');
          } else if (val === 'TIME' || val === 'STEPS') {
            $('#stop-crit .ui.fluid.search.dropdown').css('display', 'none');
          }
          this.stopCriteria = val;
        },
      })
    ;

    $('#start-simulation-date').calendar();
  }

  public openConfiguration(): void {
    $('.ui.configuration.modal').modal('show');
    this.graph = graphModule.graph;
  }

  public get functions(): GraphData[] {
    let functions;
    try {
      const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
      const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
      functions = nodeData.values().filter((value: GraphData) => value.getType() === 'function');
    } catch (e) {
      functions = e;
    }
    return functions;
  }

}
</script>
