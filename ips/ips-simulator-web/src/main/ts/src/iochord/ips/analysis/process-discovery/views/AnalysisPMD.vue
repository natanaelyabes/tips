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
        <a class="ui label">
          Select dataset
        </a>
        <!-- <select ref="datasetSelector" @change="mine">
          <option value="---">---</option>
          <option :selected="datasetId == i" v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
        {{progressMessage}} -->
      </template>

      <!-- Setting Bar Ribbon -->
      <template slot="ribbon-bar-menu-item">
        <!-- Slots for parameters of each process model discovery algorithms -->
        <PMDHeuristicsRibbonComponent></PMDHeuristicsRibbonComponent>
      </template>

      <!-- Content -->
      <template slot="content">
        {{ graphJson }}
        <ModelViewer></ModelViewer>
      </template>

    </SettingsBarWrapperComponent>
  </div>
</template>

<style>
.sandbox.analysis.pmd {
  height: 100%;
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
import DataConnectionService from '@/iochord/ips/common/service/data/DataConnectionService';
import IsmDiscoveryService, { IsmDiscoveryConfiguration } from '@/iochord/ips/common/service/analysis/IsmDiscoveryService';
import ModelViewer from '@/iochord/ips/simulation/editor/components/ModelViewer.vue';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphImpl } from '@/iochord/ips/common/graph/ism/class/GraphImpl';

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
 * @author N. Y. Wirawan <ny4tips@gmail.com>
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
        const graph = JSON.parse(res.body);
        let n = 0;
        for (const i of Object.keys(graph.data.pages['0'].nodes)) {
          n++;
        }
        let c = 0;
        for (const i of Object.keys(graph.data.pages['0'].connectors)) {
          c++;
        }
        console.log(graph.data);
        const g: Graph = GraphImpl.deserialize(graph.data) as Graph;
        graphModule.setGraph(g);
        // GraphSubject.update(graphModule.graph);

        console.log(g);
        self.graphJson = 'This graph has ' + n + ' nodes and ' + c + ' connectors';
        self.progressMessage = '';
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
