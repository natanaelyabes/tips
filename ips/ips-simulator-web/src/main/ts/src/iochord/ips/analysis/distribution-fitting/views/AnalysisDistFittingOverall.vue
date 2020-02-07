<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="sandbox analysis dist fitting">
    <LeftBarContentWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="left-bar-menu-item">
        <a href="/#/iochord/ips/analytics/distribution/settings" class="item">Settings</a>
        <a href="/#/iochord/ips/analytics/distribution/fitting" class="item">Overall</a>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentSplitComponent></ContentSplitComponent>
      </template>
    </LeftBarContentWrapperComponent>
  </div>
</template>

<style>
.sandbox.analysis.dist.fitting {
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
/**
 * @module   iochord/ips/analysis/distribution-fitting/
 */

import { Vue, Component } from 'vue-property-decorator';
import LeftBarContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/LeftBarContentWrapperComponent.vue';
import DiffLayoutView from '@/iochord/ips/common/ui/layout/class/DiffLayoutView';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';
import ContentSplitComponent from '../components/ContentSplitComponent.vue';

@Component({
  components: {
    LeftBarContentWrapperComponent,
    ContentSettingsComponent,
    ContentSplitComponent,
  },
})

/**
 * Overall page to provide a side by side
 * view between process model and its fitted distribution.
 *
 * @extends BaseComponent
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class AnalysisDistFittingOverall extends DiffLayoutView {

  /**
   * Title field of AnalysisDistFittingOverall
   *
   * @type {string}
   * @memberof AnalysisDistFittingOverall
   */
  public title: string = '';

  /**
   * Override browser properties for AnalysisDistFittingOverall
   *
   * @override
   * @memberof AnalysisDistFittingOverall
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Distribution Fitting');
  }

  /**
   * Override title for AnalysisDistFittingOverall
   *
   * @override
   * @memberof AnalysisDistFittingOverall
   */
  public setTitle(): void {
    this.title = `Distribution Fitting`;
  }
}
</script>
