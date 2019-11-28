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
              <div class="six wide column">
                <template v-if="stopCriteria !== 'CUSTOM'">
                  <input type="number" v-model="value" id="ctrl_txt_value" />
                </template>
                <template v-else>
                  <select id="act_txtfunction" class="ui fluid search dropdown">
                    <option v-for="fx in functions" :key="fx.id" :value="fx.id">{{fx.label}} ({{fx.id}})</option>
                  </select>
                </template>
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
                <tr>
                  <td>{{ ei }}</td>
                  <td>{{ ele.label }}</td>
                  <td style="width: 10%;">Entity per Arrival</td>
                  <td style="width: 15%;">{{ ele.entitiesPerArrival }}</td>
                  <td style="width: 10%;">Arrival Rate</td>
                  <td style="width: 15%;">{{ ele.expression }}</td>
                  <td style="width: 10%;">Max Arrival</td>
                  <td style="width: 15%;">{{ ele.maxArrival }}</td>
                </tr>
              </template>
              <template v-if="ele.elementType && ele.elementType == 'resource'">
                <tr>
                  <td>{{ ei }}</td>
                  <td>{{ ele.label }}</td>
                  <td>Number of Resource</td>
                  <td>{{ ele.numberOfResource }}</td>
                  <td colspan="4">&nbsp;</td>
                </tr>
              </template>
            </template>
            <tr>
              <td colspan="8">NODE TOOLBOX</td>
            </tr>
            <template v-for="(ele, ei) in this.graph.pages.get('0').nodes._values">
              <template v-if="ele.elementType && ele.elementType == 'activity'">
                <tr>
                  <td>{{ ei }}</td>
                  <td>{{ ele.label }}</td>
                  <td>Setup Time</td>
                  <td>{{ ele.setupTime }}</td>
                  <td>Processing Time (처리 시간)</td>
                  <td>{{ ele.processingTime }}</td>
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
  private stopCriteria?: 'TIME' | 'STEPS' | 'CUSTOM' = 'TIME';
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
        onChange: (val: 'TIME' | 'STEPS' | 'CUSTOM') => {
          this.stopCriteria = val;

          if (this.stopCriteria === 'CUSTOM') {
            this.$nextTick(() => {
              this.initDropdown();
            });
          }
        },
      })
    ;

    this.initDropdown();

    $('#start-simulation-date').calendar();
  }

  public initDropdown(): void {
    $('#act_txtfunction')
      .dropdown('set selected', this.function)
      .dropdown({
        onChange: (val: string) => {
          this.function = val;
        },
      })
    ;
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
