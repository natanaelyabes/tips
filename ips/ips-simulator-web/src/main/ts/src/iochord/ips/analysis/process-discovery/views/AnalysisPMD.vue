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
        <select ref="datasetSelector" @change="mine">
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
      <template slot="content">
        {{graphJson}}
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
import { Vue, Component, Prop } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import Layout03View from '@/iochord/ips/common/ui/layout/class/Layout03';
import SettingsBarWrapperComponent from '@/iochord/ips/common/ui/layout/components/SettingsBarWrapperComponent.vue';
import PMDHeuristicsRibbonComponent from '../components/PMDHeuristicsRibbonComponent.vue';
import DataConnectionService from '@/iochord/ips/common/service/data/DataConnectionService';
import IsmDiscoveryService, { IsmDiscoveryConfiguration } from '@/iochord/ips/common/service/analysis/IsmDiscoveryService';
import ModelViewer from '@/iochord/ips/model/editor/components/ModelViewer.vue';
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
export default class AnalysisPMD extends Layout03View {
  public title: string = '';

  @Prop({default: ''})
  public datasetId!: any;

  public datasets = {};

  public graphJson: string = '';

  public progressMessage: string = '';

  public mine(): void {
    const self = this;
    const selectedDatasetId = (this.$refs['datasetSelector'] as any).value;
    self.runMine(selectedDatasetId);
  }

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

  public mounted(): void { // implement business logic
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

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Process Model Discovery');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Process Model Discovery`;
  }
}
</script>
