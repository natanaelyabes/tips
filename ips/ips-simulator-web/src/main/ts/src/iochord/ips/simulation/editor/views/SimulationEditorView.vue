<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="editor view">

    <!-- WrapperComponent -->
    <WrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Simulation Editor</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select class="ui floating scrolling dropdown button" ref="datasetSelector" @change="mine">
          <option value="Select a dataset">Select a dataset</option>
          <option :selected="datasetId == i" v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
        {{progressMessage}}
      </template>

      <!--  Left Sidebar Menu Item -->
      <template slot="left-sidebar-menu-item">
        <ControlPaletteComponent :isDisabled="isDisabled" />
        <NodePaletteComponent :isDisabled="isDisabled" />
        <DataPaletteComponent :isDisabled="isDisabled" />
      </template>

      <!--  Ribbon Menu Item -->
      <template slot="ribbon-menu-item">

        <!-- Left menu item -->
        <SimulationDataManagementComponent
          :isDisabled="isDisabled"
          @create="modelCreate"
          @example="modelLoadExample"
          @report="showReport" />

        <!-- Right menu item -->
        <div id="ribbon-player-menu" class="right menu">
          <SimulationPlayerComponent
            :isPlaying="isDisabled"
            @play = "isDisabled = true; loadNPlay();"
            @stop = "isDisabled = false" />
        </div>

        <div id="ribbon-layout-menu" class="right menu">
          <SimulationLayoutComponent
            @autolayout="modelAutoLayout"
            :isDisabled="isDisabled" />
        </div>
      </template>

      <!-- Content -->
      <template slot="content">
        <CanvasComponent :isDisabled="isDisabled" :key="reRenderKey" :response="graphData" />
      </template>

    </WrapperComponent>

    <div ref="report" class="ui overlay fullscreen modal">
      <i class="close icon"></i>
      <div class="header">
        Simulation Report
      </div>
      <div class="content" style="max-height: 75vh; overflow: auto">
        <table class="ui celled structured table">
          <thead>
            <tr>
              <th rowspan="2" style="width: 5%;">No</th>
              <th rowspan="2" style="width: 15%;">Element</th>
              <th rowspan="2">Description</th>
              <th colspan="5">Statistics</th>
            </tr>
            <tr>
              <th style="width: 10%;">Count (수량)</th>
              <th style="width: 10%;">Average (평균)</th>
              <th style="width: 10%;">Total (합계)</th>
              <th style="width: 10%;">Min (최소값)</th>
              <th style="width: 10%;">Max (최대 값)</th>
            </tr>
          </thead>
          <tbody>
            <template v-for="(gs, gsi) in report.groups" >
              <tr :key="gsi + '-' + 100">
                <td colspan="8">{{ gs.name }}</td>
              </tr>
              <template v-for="(es, esi) in gs.elements">
                <template v-if="es.subElements && es.subElements != null">
                  <tr v-for="(ess, essi) in es.subElements" :key="gsi + '-' + esi + '-' + essi + '-' + 1000">
                    <td v-if="essi == 1" :rowspan="Object.keys(es.subElements).length">{{ esi }}.</td>
                    <td v-if="essi == 1" :rowspan="Object.keys(es.subElements).length">{{ es.name }}</td>
                    <td>{{ ess.description }}</td>
                    <td>{{ ess.count ? ess.count : '-' }}</td>
                    <template v-if="ess.format == 'duration'">
                      <td>{{ ess.average ? getDurationString(ess.average) : '-' }}</td>
                      <td>{{ ess.total ? getDurationString(ess.total) : '-' }}</td>
                      <td>{{ ess.min ? getDurationString(ess.min) : '-' }}</td>
                      <td>{{ ess.max ? getDurationString(ess.max) : '-' }}</td>
                    </template>
                    <template v-else>
                      <td>{{ ess.average ? ess.average : '-' }} {{ess.format}}</td>
                      <td>{{ ess.total ? ess.total : '-' }} {{ess.format}}</td>
                      <td>{{ ess.min ? ess.min : '-' }} {{ess.format}}</td>
                      <td>{{ ess.max ? ess.max : '-' }} {{ess.format}}</td>
                    </template>
                  </tr>
                </template>
                <tr :key="esi + '-' + 10000" v-else>
                  <td>{{ esi }}.</td>
                  <td>{{ es.name }}</td>
                  <td>{{ es.description }}</td>
                  <td>{{ es.count ? es.count : '-' }}</td>
                  <template v-if="es.format == 'duration'">
                    <td>{{ es.average ? getDurationString(es.average) : '-' }}</td>
                    <td>{{ es.total ? getDurationString(es.total) : '-' }}</td>
                    <td>{{ es.min ? getDurationString(es.min) : '-' }}</td>
                    <td>{{ es.max ? getDurationString(es.max) : '-' }}</td>
                  </template>
                  <template v-else>
                    <td>{{ es.average ? es.average : '-' }} {{es.format}}</td>
                    <td>{{ es.total ? es.total : '-' }} {{es.format}}</td>
                    <td>{{ es.min ? es.min : '-' }} {{es.format}}</td>
                    <td>{{ es.max ? es.max : '-' }} {{es.format}}</td>
                  </template>
                </tr>
              </template>
            </template>
          </tbody>
        </table>
      </div>
      <div class="actions">
        <div class="ui black deny button">
          Close
        </div>
        <router-link v-if="report.replayId && report.replayId != ''" tag="a" :to="'/iochord/ips/analytics/process/discovery/' + report.replayId + '/1'" target="_blank" class="ui primary button">
          Replay
        </router-link>
      </div>
    </div>
  </div>
</template>

<style>
.sidebar.component .navigation-bar.component {
  overflow-y: auto;
  overflow-x: hidden;
}
</style>

<style scoped>
::-webkit-scrollbar {
  width: .2em!important;
  height: .2em!important;
}

.image-icon .ui.image {
  border-radius: 0;
  margin-bottom: .5em;
}

i.big.icon {
  margin-bottom: .25em!important;
}

@media screen and (max-width: 1440px) {
  #ribbon-player-menu {
    margin-left: 50px!important;
    margin-right: 110px!important;
  }
}
</style>

<style>
.editor.view {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.breadcrumb.component a.section {
  color: white;
  text-decoration: underline;
}

.pusher {
  overflow-y: hidden;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue, Watch, Prop } from 'vue-property-decorator';
import axios, { AxiosResponse, AxiosPromise } from 'axios';
import { getModule } from 'vuex-module-decorators';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// Classes
import AppLayoutView from '@/iochord/ips/common/ui/layout/class/AppLayoutView';
import { GraphImpl } from '@/iochord/ips/common/graphs/ism/class/GraphImpl';
import { GraphNodeImpl } from '@/iochord/ips/common/graphs/ism/class/GraphNodeImpl';
import { GraphConnectorImpl } from '@/iochord/ips/common/graphs/ism/class/GraphConnectorImpl';
import { GraphPageImpl } from '@/iochord/ips/common/graphs/ism/class/GraphPageImpl';

// Interfaces
import { AppLayout } from '@/iochord/ips/common/ui/layout/interfaces/AppLayout';
import { BrowserHasProperties } from '@/iochord/ips/common/browser/interfaces/BrowserHasProperties';
import { SemanticModulesIsUsed } from '@/iochord/ips/common/ui/semantic-components/SemanticModulesIsUsed';
import { Graph } from '@/iochord/ips/common/graphs/ism/interfaces/Graph';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Services
import { IsmModelService } from '@/iochord/ips/common/graphs/ism/services/IsmModelService';

// Components
import WrapperComponent from '@/iochord/ips/common/ui/layout/components/WrapperComponent.vue';
import ControlPaletteComponent from '../components/palette/ControlPaletteComponent.vue';
import NodePaletteComponent from '../components/palette/NodePaletteComponent.vue';
import DataPaletteComponent from '../components/palette/DataPaletteComponent.vue';
import SimulationPlayerComponent from '../components/ribbon/SimulationPlayerComponent.vue';
import SimulationLayoutComponent from '../components/ribbon/SimulationLayoutComponent.vue';
import SimulationDataManagementComponent from '../components/ribbon/SimulationDataManagementComponent.vue';
import CanvasComponent from '../components/canvas/CanvasComponent.vue';
import MinimapComponent from '../components/minimap/MinimapComponent.vue';

// Vuex & rxjs
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';

import { IsmSimulatorService } from '@/iochord/ips/simulation/editor/services/IsmSimulatorService';
import DataConnectionService from '@/iochord/ips/data/connection/services/DataConnectionService';
import IsmDiscoveryService, { IsmDiscoveryConfiguration } from '@/iochord/ips/analysis/process-discovery/services/IsmDiscoveryService';

// Vuex module
const graphModule = getModule(GraphModule);

declare const $: any;

@Component<SimulationEditorView>({
  components: {
    WrapperComponent,
    CanvasComponent,
    MinimapComponent,
    ControlPaletteComponent,
    NodePaletteComponent,
    DataPaletteComponent,
    SimulationPlayerComponent,
    SimulationLayoutComponent,
    SimulationDataManagementComponent,
  },
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})

/**
 * The simulation editor view.
 *
 * @export
 * @class SimulationEditorView
 * @extends {AppLayoutView}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class SimulationEditorView extends AppLayoutView {

  /**
   * Dataset Id field for selecting event log dataset.
   *
   * @type {*}
   * @memberof AnalysisPMD
   */
  @Prop({default: ''})
  public selectedDatasetId!: any;

  public datasetId: any = '';

  /**
   * Datasets field to receive JSON data from web service.
   *
   * @memberof AnalysisPMD
   */
  public datasets = {};

  /**
   * Field to collect basic statistics: number of graph and number of nodes.
   *
   * @type {string}
   * @memberof AnalysisPMD
   */
  public graphJson: string = '';

  /**
   * Field for message loaders.
   *
   * @type {string}
   * @memberof AnalysisPMD
   */
  public progressMessage: string = '';

  /**
   * Indicates whether a simulation editor view is being disabled. False otherwise.
   *
   * @type {boolean}
   * @memberof SimulationEditorView
   */
  public isDisabled: boolean = false;

  /**
   * Indicates whether a simulation editor is in the running state. False otherwise.
   *
   * @type {boolean}
   * @memberof SimulationEditorView
   */
  public isRunning: boolean = false;

  /**
   * Report object of the simulation editor.
   *
   * @private
   * @type {*}
   * @memberof SimulationEditorView
   */
  private report: any = {
    replayId: '',
    groups: {},
  };

  /**
   * Returns the duration in DD:hh:mm:ss format
   *
   * @param {*} seconds
   * @returns
   * @memberof SimulationEditorView
   */
  public getDurationString(seconds: any) {
    seconds = Number(seconds);
    const d = Math.floor(seconds / (3600 * 24));
    const h = Math.floor(seconds % (3600 * 24) / 3600);
    const m = Math.floor(seconds % 3600 / 60);
    const s = Math.floor(seconds % 60);
    const dDisplay = d > 0 ? d + 'D ' : '';
    const hDisplay = h > 0 ? h + 'h ' : '';
    const mDisplay = m > 0 ? m + 'm ' : '';
    const sDisplay = s > 0 ? s + 's ' : '';
    return dDisplay + hDisplay + mDisplay + sDisplay;
  }

  public created() {
    if (this.selectedDatasetId && this.selectedDatasetId !== '') {
      this.datasetId = this.selectedDatasetId;
      this.runMine(this.datasetId);
    }
  }

  /**
   * Perform analysis upon selected dataset by executing process mining algorithm.
   *
   * @memberof AnalysisPMD
   */
  public mine(): void {
    const self = this;
    const selectedDatasetId = (this.$refs['datasetSelector'] as any).value;
    self.datasetId = selectedDatasetId;
    self.runMine(selectedDatasetId);
  }

  /**
   * Discover process graph from the web service.
   *
   * @param {string} selectedDatasetId
   * @memberof AnalysisPMD
   */
  public runMine(selectedDatasetId: string): void {
    const self = this;
    self.report = {
      replayId: '',
      groups: {},
    };
    if (selectedDatasetId !== 'Select a dataset') {
      const config: IsmDiscoveryConfiguration = new IsmDiscoveryConfiguration();
      config.datasetId = selectedDatasetId;
      IsmDiscoveryService.getInstance().discoverIsmHybridGraph(config, (res: any) => {
        const graph = res.data;
        let n = 0; for (const i of Object.keys(graph.data.pages['0'].nodes)) {
          n++;
        }
        let c = 0; for (const i of Object.keys(graph.data.pages['0'].connectors)) {
          c++;
        }
        const g: Graph = GraphImpl.deserialize(graph.data) as Graph;
        graphModule.setGraph(g);
        self.graphJson = 'This graph has ' + n + ' nodes and ' + c + ' connectors.';
        self.progressMessage = '';
        self.forceReRender();
      }, (tick: any) => {
        self.progressMessage = tick.progressData;
        console.log(tick.progressData);
      });
    } else {
      self.graphJson = '';
      self.progressMessage = '';
    }
  }

  /**
   * Override the browser properties.
   *
   * @override
   * @memberof SimulationEditorView
   */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Simulation Editor: Editor');
  }

  /**
   * Assigns the title of the simulation editor view.
   *
   * @override
   * @memberof SimulationEditorView
   */
  public setTitle(): void {
    this.title = `Editor`;
  }

  /**
   * Vue mounted lifecycle.
   *
   * @override
   * @returns {Promise<void>}
   * @memberof SimulationEditorView
   */
  public async mounted(): Promise<void> {
    try {

      // Fetch graph to Vuex state
      await this.modelCreate();

      // Print to stdout
      console.log(graphModule.graph);

      // Update rxjs subject
      GraphSubject.update(graphModule.graph);

      // When graph is updated
      this.$observables.graph.subscribe((graph: Graph) => {

        // Update its Vuex state
        graphModule.setGraph(graph);

        // Print to stdout
        console.log(graph);
      });
    } catch (e) {
      console.error(e);
    }

    const self = this;
    DataConnectionService.getInstance().getDataConnections((res: any) => {
      self.datasets = res.data;
      if (self.datasetId !== '') {
        self.runMine(self.datasetId);
      }
    }, null);

    // Force re-render page component
    this.forceReRender();

    // Always reload component when resizing content
    // window.addEventListener('resize', () => {
    //   this.forceReRender();
    // });
  }

  /**
   * To load and play the simulation model.
   *
   * @memberof SimulationEditorView
   */
  public async loadNPlay() {
    const self = this;
    this.isRunning = true;
    $('.editor.canvas.ui.basic.segment').dimmer({
      displayLoader: true,
      variation: 'inverted',
      loaderVariation: 'slow blue medium elastic',
      loaderText: 'Simulation is running ...',
      closable: false,
    }).dimmer('show');

    if (this.datasetId !== 'Select a dataset' && this.datasetId !== '') {
      const config: IsmDiscoveryConfiguration = new IsmDiscoveryConfiguration();
      IsmSimulatorService.getInstance().postLoadNPlayWithDatasetWS(config, this.datasetId,
        (res: any) => {
          self.report = JSON.parse(res.body).data;
          $('.editor.canvas.ui.basic.segment').dimmer('hide');
          $(self.$refs['report']).modal('show');
          self.isDisabled = false;
          self.isRunning = false;
        }, (tick: any) => {
          if ($('.ui.loader.slow.blue.medium.elastic.text') && $('.ui.loader.slow.blue.medium.elastic.text').length > 0 && tick.progressData != null) {
            $('.ui.loader.slow.blue.medium.elastic.text')[0].innerHTML = '<div class="ui teal progress" data-percent="10" style="min-width: 400px;"><div class="bar" style="width: '
              + tick.progress + '%;"></div><div class="label">' + tick.progressData + ' (' + tick.progress + ' %)</div></div>';
          }
      });
    } else {
      IsmSimulatorService.getInstance().postLoadNPlayWS(graphModule.graph,
        (res: any) => {
          self.report = JSON.parse(res.body).data;
          $('.editor.canvas.ui.basic.segment').dimmer('hide');
          $(self.$refs['report']).modal('show');
          self.isDisabled = false;
          self.isRunning = false;
        }, (tick: any) => {
          if ($('.ui.loader.slow.blue.medium.elastic.text') && $('.ui.loader.slow.blue.medium.elastic.text').length > 0 && tick.progressData != null) {
            $('.ui.loader.slow.blue.medium.elastic.text')[0].innerHTML = '<div class="ui teal progress" data-percent="10" style="min-width: 400px;"><div class="bar" style="width: '
              + tick.progress + '%;"></div><div class="label">' + tick.progressData + ' (' + tick.progress + ' %)</div></div>';
          }
      });
    }
  }

  public showReport() {
    $(this.$refs['report']).modal('show');
  }

  /**
   * To create new simulation model and reset the canvas as blank.
   *
   * @memberof SimulationEditorView
   */
  public async modelCreate() {
    await graphModule.newGraph();
    this.report = {
      replayId: '',
      groups: {},
    };
    this.forceReRender();
  }

  /**
   * Load the example of simulation model.
   *
   * @memberof SimulationEditorView
   */
  public async modelLoadExample() {
    this.datasetId = '';
    this.report = {
      replayId: '',
      groups: {},
    };
    await graphModule.loadExampleGraph();
    this.forceReRender();
  }

  /**
   * Performs auto-layout algorithm to the graph and arrange the simulation model.
   *
   * @memberof SimulationEditorView
   */
  public modelAutoLayout() {
    this.forceReRender();
  }

  /**
   * Returns the graph data object.
   *
   * @readonly
   * @type {(Graph | undefined)}
   * @memberof SimulationEditorView
   */
  public get graphData(): Graph | undefined {
    return graphModule.graph;
  }
}
</script>