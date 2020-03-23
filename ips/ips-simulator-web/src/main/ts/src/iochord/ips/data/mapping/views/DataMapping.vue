<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="sandbox data management">
    <WrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Management</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select class="ui floating scrolling dropdown button" @change="retreiveDataset" ref="datasetSelector" v-model="selectedDatasetId">
          <option value="---" selected>---</option>
          <option v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentMappingComponent :key="reRenderKey" :datasetId="selectedDatasetId" ref="mapping" id="mapping"></ContentMappingComponent>
      </template>
    </WrapperComponent>
  </div>
</template>

<style>
.sandbox.data.management {
  height: 100%;
}

a.section {
  color: white!important;
  text-decoration: underline;
}
</style>

<script lang="ts">
import { Vue, Prop, Component } from 'vue-property-decorator';
import ExplorerLayoutView from '@/iochord/ips/common/ui/layout/class/ExplorerLayoutView';
import WrapperComponent from '@/iochord/ips/common/ui/layout/components/WrapperComponent.vue';
import ContentMappingComponent from '../components/ContentMappingComponent.vue';
import MappingService from '../services/MappingService';
import IMappingResource from '../interfaces/IMappingResource';
import MappingResource from '../models/MappingResource';
import MappingModule from '../stores/MappingModule';
import { getModule } from 'vuex-module-decorators';
import DataConnectionService from '../../connection/services/DataConnectionService';

// Vuex module
const mappingModule = getModule(MappingModule);

@Component({
  components: {
    WrapperComponent,
    ContentMappingComponent,
  },
})

/**
 * The content mapping component.
 *
 * @export
 * @class ContentMappingComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class DataMapping extends ExplorerLayoutView {
  public title: string = '';

  public datasets = {};

  @Prop(String)
  public datasetId?: string;

  public selectedDatasetId: string = '---';

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: Data Mapping');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Data Mapping`;
  }

  public mounted(): void {
    this.selectedDatasetId = '---';
    if (this.datasetId) this.selectedDatasetId = this.datasetId;
    this.retreiveDataset();
  }

  public retreiveDataset(): void {
    DataConnectionService.getInstance().getDataConnections((res: any) => {
      this.datasets = res.data;
    }, (tick: any) => {
      // this.datasets = tick;
    });

    if (this.datasetId) {
      this.selectedDatasetId = this.datasetId;
    }

    this.forceReRender();
  }
}
</script>
