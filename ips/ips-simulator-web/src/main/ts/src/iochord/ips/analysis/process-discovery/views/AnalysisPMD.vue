<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="sandbox analysis pmd">
    <SettingsBarWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select class="ui floating scrolling dropdown button" ref="datasetSelector" @change="mine">
          <option value="---">---</option>
          <option :selected="datasetId == i" v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
        {{progressMessage}}
      </template>

      <!-- Setting Bar Ribbon -->
      <template slot="ribbon-bar-menu-item">
        <!-- Slots for parameters of each process model discovery algorithms -->
        <PMDHeuristicsRibbonComponent></PMDHeuristicsRibbonComponent>
      </template>

      <!-- Content -->
      <template slot="content" >
        <div v-if="graphJson" class="statistics">
          {{ graphJson }}
        </div>
        <ModelViewer ref="viewer"></ModelViewer>
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
  background: rgba(0,0,0,0.67);
  width: 200px;
  padding: .2em .5em;
  position: absolute;
  border: 1px solid black;
  border-radius: .5em;
  color: greenyellow;
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
 * @author I. R. Pulshashi <pulshashi@ideas.web.id>
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
    if (selectedDatasetId !== '---') {
      const config: IsmDiscoveryConfiguration = new IsmDiscoveryConfiguration();
      config.datasetId = selectedDatasetId;
      IsmDiscoveryService.getInstance().discoverIsmGraph(config, (res: any) => {
        const graph = res.data;

        let n = 0;
        for (const i of Object.keys(graph.data.pages['0'].nodes)) {
          n++;
        }

        let c = 0;

        for (const i of Object.keys(graph.data.pages['0'].connectors)) {
          c++;
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
    }, (tick: any) => {
      // self.datasets = tick;
    });
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
}
</script>
