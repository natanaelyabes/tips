<template>
  <div class="sandbox analysis resources">
    <LeftBarContentWrapperComponent>
      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="left-bar-menu-item">
        <!-- <div class="menu"> -->
          <a href="/#/iochord/ips/analytics/resource/settings" class="item">Settings</a>
          <a href="/#/iochord/ips/analytics/resource/mining" class="item">Overall</a>
        <!-- </div> -->
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
import Layout04View from '@/iochord/ips/common/ui/layout/class/Layout04';
import LeftBarContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/LeftBarContentWrapperComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';


@Component({
  components: {
    LeftBarContentWrapperComponent,
    ContentSettingsComponent,
  },
})
export default class AnalysisResourceMiningSettings extends Layout04View {
  public title: string = '';


  public mounted(): void { // implement business logic
    // alert('ding! DD ppang juwa');
  }

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Resources');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Resources mining`;
  }
}
</script>