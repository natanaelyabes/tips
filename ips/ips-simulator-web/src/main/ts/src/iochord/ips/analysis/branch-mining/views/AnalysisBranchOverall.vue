<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="sandbox analysis branch">
    <DepthTwoLeftWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select class="ui floating scrolling dropdown button" @change="retreiveDataset" ref="datasetSelector" v-model="selectedDatasetId">
          <option value="Select a dataset" selected>Select a dataset</option>
          <option v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="depth-one-menu-item">
        <router-link :to="`/iochord/ips/analytics/branch/settings/${datasetId}`" 
          tag="a" class="item"><i class="cog icon"></i>Settings</router-link>
        <router-link :to="`/iochord/ips/analytics/branch/mining/${datasetId}`" 
          tag="a" class="item active"><i class="chart bar icon"></i>Overall</router-link>
      </template>

      <!-- Content -->
      <template slot="depth-two-menu-item">
        <ItemBranchList />
      </template>

      <template slot="content">
        <ItemProcessModel />
      </template>
    </DepthTwoLeftWrapperComponent>
  </div>
</template>

<style>
.sandbox.analysis.branch {
  height: 100%;
}

a.section {
  color: white!important;
  text-decoration: underline;
}

.ui.segment {
  margin: 0;
}
</style>

<script lang="ts">
import { Vue, Prop, Component } from 'vue-property-decorator';
import ExplorerLayoutView from '@/iochord/ips/common/ui/layout/class/ExplorerLayoutView';
import DepthTwoLeftWrapperComponent from '@/iochord/ips/common/ui/layout/components/DepthTwoLeftWrapperComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';
import ContentSplitComponent from '../components/ContentSplitComponent.vue';
import { getModule } from 'vuex-module-decorators';
import DataConnectionService from '../../../data/connection/services/DataConnectionService';
import ItemProcessModel from '../components/ItemProcessModel.vue';
import ItemBranchList from '../components/ItemBranchList.vue';

declare const $: any;

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
    ItemProcessModel,
    ItemBranchList,
  },
})

/**
 * Overall page to view side by side comparison
 * between process model and its branching rule.
 *
 * @export
 * @class AnalysisBranchOverall
 * @extends DiffLayoutView
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class AnalysisBranchOverall extends ExplorerLayoutView {

  /**
   * Title field of AnalysisBranchOverall
   *
   * @type {string}
   * @memberof AnalysisBranchOverall
   */
  public title: string = '';

  public datasets = {};

  @Prop({default: ''})
  public datasetId!: string;

  public selectedDatasetId: string = 'Select a dataset';

  /**
   * Override browser properties for AnalysisBranchOverall
   *
   * @override
   * @memberof AnalysisBranchOverall
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Branch');
  }

  /**
   * Override title for AnalysisBranchOverall
   *
   * @override
   * @memberof AnalysisBranchOverall
   */
  public setTitle(): void {
    this.title = `Branch mining`;
  }

  public mounted(): void {
    this.selectedDatasetId = 'Select a dataset';
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

  public declareSemanticModules() {
    $('.dropdown').dropdown();
  }
}
</script>
