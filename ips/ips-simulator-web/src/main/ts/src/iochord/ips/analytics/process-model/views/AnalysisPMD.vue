<template>
  <div class="sandbox analysis pmd">
    <SettingsBarWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select ref="datasetSelector" @change="mine">
          <option value="---">---</option>
          <option v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
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
import { Vue, Component } from 'vue-property-decorator';
import Layout03View from '@/iochord/ips/common/ui/layout/class/Layout03';
import SettingsBarWrapperComponent from '@/iochord/ips/common/ui/layout/components/SettingsBarWrapperComponent.vue';
import PMDHeuristicsRibbonComponent from '../components/PMDHeuristicsRibbonComponent.vue';
import DataConnectionService from '@/iochord/ips/common/service/data/DataConnectionService';
import IsmDiscoveryService, { IsmDiscoveryConfiguration } from '@/iochord/ips/common/service/analysis/IsmDiscoveryService';

@Component({
  components: {
    SettingsBarWrapperComponent,
    PMDHeuristicsRibbonComponent,
  },
})
export default class AnalysisPMD extends Layout03View {
  public title: string = '';

  public datasets = {};

  public graphJson: string = '';
  
  public progressMessage: string = '';

  public mine() {
    const self = this;
    const selectedDatasetId = this.$refs["datasetSelector"].value;
    if (selectedDatasetId != '---') {
      const config: IsmDiscoveryConfiguration = new IsmDiscoveryConfiguration();
      config.datasetId = selectedDatasetId;
      IsmDiscoveryService.getInstance().discoverIsmGraph(config, (res: any) => {
        const graph = JSON.parse(res.body);
        let n = 0;
        for (var i in graph.data.pages["0"].nodes) {
          n++;
        }
        let c = 0;
        for (var i in graph.data.pages["0"].connectors) {
          c++;
        }
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
    }, (tick: any) => {
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
