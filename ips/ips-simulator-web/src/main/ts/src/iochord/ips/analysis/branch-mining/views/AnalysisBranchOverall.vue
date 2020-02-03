<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
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
import { Vue, Component } from 'vue-property-decorator';
import DiffLayoutView from '@/iochord/ips/common/ui/layout/class/DiffLayoutView';
import LeftBarContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/LeftBarContentWrapperComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';
import ContentSplitComponent from '../components/ContentSplitComponent.vue';

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
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class AnalysisBranchOverall extends DiffLayoutView {

  /**
   * Title fields of AnalysisBranchOverall
   *
   * @type {string}
   * @memberof AnalysisBranchOverall
   */
  public title: string = '';

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
}
</script>
