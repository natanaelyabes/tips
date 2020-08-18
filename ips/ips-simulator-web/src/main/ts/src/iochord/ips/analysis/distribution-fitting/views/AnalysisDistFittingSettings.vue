<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
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
        <a href="/#/iochord/ips/analytics/distribution/settings" class="item active"><i class="cog icon"></i>Settings</a>
        <a href="/#/iochord/ips/analytics/distribution/fitting" class="item"><i class="bar chart icon"></i>Overall</a>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentSettingsComponent></ContentSettingsComponent>
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
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';
import ContentSplitComponent from '../components/ContentSplitComponent.vue';
import DiffLayoutView from '@/iochord/ips/common/ui/layout/class/DiffLayoutView';

@Component({
  components: {
    LeftBarContentWrapperComponent,
    ContentSettingsComponent,
    ContentSplitComponent,
  },
})

/**
 * Settings view to assign distribution fitting parameters or properties.
 *
 * @extends BaseComponent
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class AnalysisDistFittingSettings extends DiffLayoutView {

  /**
   * Title field for AnalysisDistFittingSettings
   *
   * @type {string}
   * @memberof AnalysisDistFittingSettings
   */
  public title: string = '';

  /**
   * Override browser properties for AnalysisDistFittingSettings
   *
   * @memberof AnalysisDistFittingSettings
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Distribution Fitting');
  }

  /**
   * Override title for AnalysisDistFittingSettings
   *
   * @memberof AnalysisDistFittingSettings
   */
  public setTitle(): void {
    this.title = `Distribution Fitting`;
  }
}
</script>
