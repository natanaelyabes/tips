<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="sandbox analysis pmd">
    <SettingsBarWrapperComponent>

      <!-- Header -->
      <template v-if="!replayId" slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select class="ui floating scrolling dropdown button" ref="datasetSelector" @change="mine">
          <option value="Select a dataset">Select a dataset</option>
          <option :selected="datasetId == i" v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
        {{progressMessage}}
      </template>
      <template v-else slot="header-breadcrumb">
        {{ datasetId }}
      </template>

      <!-- Setting Bar Ribbon -->
      <template slot="ribbon-bar-menu-item">
        <!-- Slots for parameters of each process model discovery algorithms -->
        <PMDHeuristicsRibbonComponent ref="configurer" :replayOnly2="true" 
          @onRun="mine" 
          @startReplay="startReplay"
          @pauseReplay="pauseReplay"
          @stopReplay="stopReplay"
          @downloadModel="downloadModel"
          @downloadDataset="downloadDataset"
          :replayId="replayId"
          :replayState="replayState"
          :fpBasedFitness="fpBasedFitness"
          :trBasedFitness="trBasedFitness"></PMDHeuristicsRibbonComponent>
        <div style="float: right;" v-html="message"></div>
      </template>

      <!-- Content -->
      <template slot="content" >
        <div v-if="graphJson" class="statistics">
          {{ graphJson }}
        </div>
        <ModelViewer ref="viewer" @graphLoaded="onGraphLoaded"></ModelViewer>
      </template>

    </SettingsBarWrapperComponent>
  </div>
</template>

<style scoped>
.sandbox.analysis.pmd {
  height: calc(100% - 150.5px);
  /* overflow-y: hidden; */
}

.statistics {
  background: rgba(255,255,255,0.67);
  width: 200px;
  padding: .2em .5em;
  position: absolute;
  border: 1px solid white;
  border-radius: .5em;
  color: #03628c;
  top: 10px;
  left: 10px;
  z-index: 9999;
}
</style>

<script lang="ts">
/**
 * @module   iochord/ips/analysis/process-discovery/
 */

import { Vue, Component, Prop } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import VisualizerLayoutView from '@/iochord/ips/common/ui/layout/class/VisualizerLayoutView';
import SettingsBarWrapperComponent from '@/iochord/ips/common/ui/layout/components/SettingsBarWrapperComponent.vue';
import PMDHeuristicsRibbonComponent from '../components/PMDHeuristicsRibbonComponent.vue';
import DataConnectionService from '@/iochord/ips/data/connection/services/DataConnectionService';
import IsmDiscoveryService, { IsmDiscoveryConfiguration } from '../services/IsmDiscoveryService';
import ModelViewer from '@/iochord/ips/simulation/editor/components/ModelViewer.vue';
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';
import { Graph } from '@/iochord/ips/common/graphs/ism/interfaces/Graph';
import { GraphImpl } from '@/iochord/ips/common/graphs/ism/class/GraphImpl';
import { TSMap } from 'typescript-map';
import * as joint from 'jointjs';
import { V } from 'jointjs';

const graphModule = getModule(GraphModule);

@Component({
  components: {
    SettingsBarWrapperComponent,
    PMDHeuristicsRibbonComponent,
    ModelViewer,
  },
})

/**
 * Process discovery page to mine heuristc net.
 *
 * @export
 * @class AnalysisPMD
 * @extends {VisualizerLayoutView}
 *
 * @package ts
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
export default class AnalysisPMD extends VisualizerLayoutView {

  /**
   * Title field for AnalysisPMD.
   *
   * @type {string}
   * @memberof AnalysisPMD
   */
  public title: string = '';

  /**
   * Dataset Id field for selecting event log dataset.
   *
   * @type {*}
   * @memberof AnalysisPMD
   */
  @Prop({default: ''})
  public datasetId!: any;

  @Prop()
  public replayId?: any;

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

  public message = '';

  public config: IsmDiscoveryConfiguration | null = null;

  public renderer: any = null;

  public animStr: any = null;

  public replaySvg: any = null;

  public replayState = -1;

  public replayTime = 0;

  public fpBasedFitness: any = -1;

  public trBasedFitness: any = -1;

  /**
   * Perform analysis upon selected dataset by executing process mining algorithm.
   *
   * @memberof AnalysisPMD
   */
  public mine(): void {
    const self = this;
    const selectedDatasetId = (this.$refs['datasetSelector'] as any).value;
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
    if (selectedDatasetId !== 'Select a dataset') {
      const configurer = this.$refs['configurer'] as any;
      const config: IsmDiscoveryConfiguration = new IsmDiscoveryConfiguration();
      config.datasetId = selectedDatasetId;
      config.positiveObservationThreshold = configurer.freqTh;
      config.dependencyThreshold = configurer.depTh;
      this.config = config;
      IsmDiscoveryService.getInstance().discoverIsmGraph(config, (res: any) => {
        const graph = res.data;
        let n = 0; for (const i of Object.keys(graph.data.pages['0'].nodes)) {
          n++;
        }
        let c = 0; for (const i of Object.keys(graph.data.pages['0'].connectors)) {
          c++;
        }
        this.message = '';
        if (graph.data.hasOwnProperty('attributes')) {
          if (graph.data.attributes.hasOwnProperty('trFitness')) {
            this.trBasedFitness = (parseFloat(graph.data.attributes.trFitness) * 100).toFixed(2);
          }
          if (graph.data.attributes.hasOwnProperty('fpFitness')) {
            this.fpBasedFitness = (parseFloat(graph.data.attributes.fpFitness) * 100).toFixed(2);
          }
        }
        const g: Graph = GraphImpl.deserialize(graph.data) as Graph;
        graphModule.setGraph(g);
        self.graphJson = 'This graph has ' + n + ' nodes and ' + c + ' connectors.';
        self.progressMessage = '';
        (self.$refs['viewer'] as any).forceReRender();
      }, (tick: any) => {
        self.progressMessage = tick.progressData;
      });
    } else {
      self.graphJson = '';
      self.progressMessage = '';
    }
  }


  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisPMD
   */
  public mounted(): void {
    const self = this;
    DataConnectionService.getInstance().getDataConnections((res: any) => {
      self.datasets = res.data;
      if (self.datasetId !== '') {
        self.runMine(self.datasetId);
      }
    }, null);
  }

  /**
   * Override browser properties for AnalysisPMD
   *
   * @override
   * @memberof AnalysisPMD
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Process Model Discovery');
  }

  /**
   * Override title for AnalysisPMD
   *
   * @override
   * @memberof AnalysisPMD
   */
  public setTitle(): void {
    this.title = `Process Model Discovery`;
  }

  public onGraphLoaded(renderer: any) {
    this.renderer = renderer;
    this.animStr = null;
    this.replayState = -1;
    if (this.config != null) {
      this.renderer.jointPages.map((jointPage: any, jointPageId: any) => {
        const cPaths: any = {};
        jointPage.jointConnectors.map((arc: any, arcId: any) => {
          const p = jointPage.getPaper().findViewByModel(arc.getConnector()).$('.connection')[0];
          cPaths[arcId] = p.id;
        });
        this.config!.connectorPaths = cPaths;
        IsmDiscoveryService.getInstance().discoverIsmGraphAnimation(this.config!, (res: any) => {
          if (res.data.data !== '') {
            this.animStr = res.data.data;
            // it only works on 1 jointpage
            this.replaySvg = jointPage.getPaper().svg;
            jointPage.getPaper().viewport.innerHTML += this.animStr;
            this.stopReplay();
          }
        }, (tick: any) => {
          console.log(tick);
        });
      });
    }
  }

  public startReplay() {
    if (this.renderer == null || this.animStr == null || this.replaySvg == null || this.replayState === 2) return;
    this.replayState = 1;
    this.replayTime += 10 / 1000;
    if (this.replayTime > this.config!.animatorLength) {
      this.stopReplay();
      return;
    }
    this.replaySvg.setCurrentTime(this.replayTime);
    setTimeout(this.startReplay, 10);
  }

  public pauseReplay() {
    if (this.renderer == null || this.animStr == null || this.replaySvg == null) return;
    if (this.replayState === 1) {
      this.replayState = 2;
    } else {
      this.replayState = 1;
      this.startReplay();
    }
  }

  public stopReplay() {
    if (this.renderer == null || this.animStr == null) return;
    if (this.replaySvg != null) {
      this.replayState = 0;
      this.replayTime = 0;
      this.replaySvg.pauseAnimations();
      this.replaySvg.setCurrentTime(0);
    }
  }

  public downloadDataset() {
    const dlUrl = `${process.env.VUE_APP_BASE_URI}` + DataConnectionService.BASE_URI + '/export/csv/' + this.datasetId;
    console.log(dlUrl);
    window.open(dlUrl);
  }

  public downloadModel() {
    if (this.replaySvg != null) {
      let svg = '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="' + this.datasetId + '" width="100%" height="100%" xmlns:ev="http://www.w3.org/2001/xml-events" style="overflow: hidden;"><style>.marker-vertices, .link-tools, .svg-pan-zoom-control, .connection-wrap, .log-replay { display: none;}</style>';
      svg += this.replaySvg.innerHTML;
      svg += '</svg>';
      const file = new Blob([svg], {type: 'image/svg+xml'});
      if (window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(file, this.datasetId);
      } else { // Others
          const a = document.createElement('a');
          const url = URL.createObjectURL(file);
          a.href = url;
          a.download = this.datasetId;
          document.body.appendChild(a);
          a.click();
          setTimeout(() => {
              document.body.removeChild(a);
              window.URL.revokeObjectURL(url);
          }, 0);
      }
    }
  }
}
</script>
