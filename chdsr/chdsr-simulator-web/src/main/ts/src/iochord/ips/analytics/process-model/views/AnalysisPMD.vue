<template>
  <div class="sandbox analysis pmd">
    <SettingsBarWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!-- Setting Bar Ribbon -->
      <template slot="ribbon-bar-menu-item">
        <!-- Slots for parameters of each process model discovery algorithms -->
        <PMDHeuristicsRibbonComponent></PMDHeuristicsRibbonComponent>
      </template>

      <!-- Content -->
      <template slot="content">

      </template>

    </SettingsBarWrapperComponent>
  </div>
</template>

<style>
.sandbox.analysis.pmd {
  height: 100%;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import Layout03View from '@/iochord/ips/common/ui/layout/classes/Layout03';
import SettingsBarWrapperComponent from '@/iochord/ips/common/ui/layout/components/SettingsBarWrapperComponent.vue';
import PMDHeuristicsRibbonComponent from '../components/PMDHeuristicsRibbonComponent.vue';

@Component({
  components: {
    SettingsBarWrapperComponent,
    PMDHeuristicsRibbonComponent,
  },
})
export default class AnalysisPMD extends Layout03View {
 public title: string = '';


  public mounted(): void { // implement business logic
   // alert('ding! DD ppang juwa');
  }

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Process Model Discovery');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Process Model Discovery`;
  }
}
</script>
