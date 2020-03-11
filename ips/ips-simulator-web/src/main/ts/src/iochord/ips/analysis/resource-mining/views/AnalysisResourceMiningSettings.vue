<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div class="sandbox analysis resources">
    <LeftBarContentWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select v-model="datasetIdRef">
          <option value="">---</option>
          <option :selected="datasetId == i" v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
        {{progressMessage}}
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="left-bar-menu-item">
        <a :href="`/#/iochord/ips/analytics/resource/settings/${datasetId}`" class="item active">Settings</a>
        <a :href="`/#/iochord/ips/analytics/resource/mining/${datasetId}`" class="item">Overall</a>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentSettingsComponent :datasetId="datasetIdRef"></ContentSettingsComponent>
      </template>
    </LeftBarContentWrapperComponent>
  </div>
</template>

<style>
.sandbox.analysis.resources {
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
import { Vue, Component, Prop } from 'vue-property-decorator';
import DiffLayoutView from '@/iochord/ips/common/ui/layout/class/DiffLayoutView';
import LeftBarContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/LeftBarContentWrapperComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';
import DataConnectionService from '@/iochord/ips/common/service/data/DataConnectionService';

@Component({
  components: {
    LeftBarContentWrapperComponent,
    ContentSettingsComponent,
  },
})

/**
 * Settings view to assign resoruce miner parameters or properties.
 *
 * @extends DiffLayoutView
 * @package ips
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
export default class AnalysisResourceMiningSettings extends DiffLayoutView {
  public title: string = '';

  /**
   * Dataset Id retrieved from params for selecting event log dataset.
   *
   * @type {string}
   * @memberof AnalysisResourceMining
   */
  @Prop({default: ''})
  public datasetId!: string;
  
  /**
   * Temporary ref of dataset Id for selecting event log dataset.
   *
   * @type {string}
   * @memberof AnalysisResourceMining
   */
  public datasetIdRef: string = '';

  
  /**
   * Datasets field to receive JSON data from web service.
   *
   * @memberof AnalysisResourceMining
   */
  public datasets = {};

  /**
   * Field for message loaders.
   *
   * @type {string}
   * @memberof AnalysisResourceMining
   */
  public progressMessage: string = '';
    
  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisResourceMining
   */
  public mounted(): void {
    const self = this;
    DataConnectionService.getInstance().getDataConnections((res: any) => {
      self.datasets = res.data;
    }, (tick: any) => {
      console.log("ini progress dr get "+tick);
    });
  }
  
  /**
   * Override browser properties for AnalysisResourceMining
   *
   * @override
   * @memberof AnalysisResourceMining
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Resource');
  }

  /**
   * Override title for AnalysisResourceMining
   *
   * @override
   * @memberof AnalysisResourceMining
   */
  public setTitle(): void {
    this.title = `Resource Mining`;
  }
}
</script>