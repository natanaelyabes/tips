<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
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
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="left-bar-menu-item">
        <a href="/#/iochord/ips/analytics/resource/settings" class="item">Settings</a>
        <a href="/#/iochord/ips/analytics/resource/mining" class="item">Overall</a>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentSettingsComponent></ContentSettingsComponent>
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
import { Vue, Component } from 'vue-property-decorator';
import DiffLayoutView from '@/iochord/ips/common/ui/layout/class/DiffLayoutView';
import LeftBarContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/LeftBarContentWrapperComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';

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
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default class AnalysisResourceMiningSettings extends DiffLayoutView {
  public title: string = '';

  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Resources');
  }

  public setTitle(): void {
    this.title = `Resources mining`;
  }
}
</script>
