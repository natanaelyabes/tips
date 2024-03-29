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
        <select class="ui floating scrolling dropdown button" v-model="datasetIdRef">
          <option value="Select a dataset" selected>Select a dataset</option>
          <option v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
        {{progressMessage}}
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="left-bar-menu-item" id="left-menu">
        <router-link :to="`/iochord/ips/analytics/resource/settings/${datasetId}`" 
          tag="a" class="item active"><i class="cog icon"></i>Settings</router-link>
        <router-link :to="`/iochord/ips/analytics/resource/mining/${datasetId}`" 
          tag="a" class="item"><i class="chart bar icon"></i>Overall</router-link>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentSettingsComponent :key="reRenderKey" :datasetId="datasetIdRef"></ContentSettingsComponent>
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
import DataConnectionService from '@/iochord/ips/data/connection/services/DataConnectionService';

declare const $: any;

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
  public datasetIdRef: string = 'Select a dataset';


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
    this.forceReRender();
    const self = this;
    DataConnectionService.getInstance().getDataConnections((res: any) => {
      self.datasets = res.data;
      if (this.datasetId !== '')
        this.datasetIdRef = this.datasetId;
    }, (tick: any) => {
      console.log('Checking progress ' + tick);
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

  public declareSemanticModules() {
    $('.dropdown').dropdown();
  }
}
</script>