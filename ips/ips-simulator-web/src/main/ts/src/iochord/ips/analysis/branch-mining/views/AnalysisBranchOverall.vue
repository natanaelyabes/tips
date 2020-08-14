<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="sandbox analysis branch">
    <LeftBarContentWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select class="ui floating scrolling dropdown button" @change="retreiveDataset" ref="datasetSelector" v-model="selectedDatasetId">
          <option value="---" selected>---</option>
          <option v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="left-bar-menu-item">
        <a href="/#/iochord/ips/analytics/branch/settings" class="item">Settings</a>
        <a href="/#/iochord/ips/analytics/branch/mining" class="item">Overall</a>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentSplitComponent></ContentSplitComponent>
      </template>
    </LeftBarContentWrapperComponent>
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
import DiffLayoutView from '@/iochord/ips/common/ui/layout/class/DiffLayoutView';
import LeftBarContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/LeftBarContentWrapperComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';
import ContentSplitComponent from '../components/ContentSplitComponent.vue';
import { getModule } from 'vuex-module-decorators';
import DataConnectionService from '../../../data/connection/services/DataConnectionService';

declare const $: any;

@Component({
  components: {
    LeftBarContentWrapperComponent,
    ContentSplitComponent,
    ContentSettingsComponent,
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
export default class AnalysisBranchOverall extends DiffLayoutView {

  /**
   * Title field of AnalysisBranchOverall
   *
   * @type {string}
   * @memberof AnalysisBranchOverall
   */
  public title: string = '';

  public datasets = {};

  @Prop(String)
  public datasetId?: string;

  public selectedDatasetId: string = '---';

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

  public declareSemanticModules() {
    $('.dropdown').dropdown();
  }
}
</script>
