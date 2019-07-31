<template>
  <div class="sandbox analysis branch">
    <LeftBarContentWrapperComponent>
      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/chdsr" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="left-bar-menu-item">
        <!-- <div class="menu"> -->
          <a class="item">Settings</a>
          <a class="item">Overall</a>
        <!-- </div> -->
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentSettingsComponent></ContentSettingsComponent>
        <!-- <ContentSplitComponent></ContentSplitComponent> -->
      </template>
    </LeftBarContentWrapperComponent>
  </div>
</template>

<!--<style scoped>

.ui.basic.segment {
  overflow-y: scroll;
  display: inline-block;
  height: 100%;
  width: 50%;
}
</style>-->

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
import Layout04View from '../../SandboxAnalysisBranch/classes/Layout04';
import LeftBarContentWrapperComponent from '../components/LeftBarContentWrapperComponent.vue';
import ContentSplitComponent from '../components/ContentSplitComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';

@Component({
  components: {
    LeftBarContentWrapperComponent,
    ContentSplitComponent,
    ContentSettingsComponent,
  },
})

export default class SandboxAnalysisBranch extends Layout04View {
  public title: string = '';


  public mounted(): void { // implement business logic
    // alert('ding! DD ppang juwa');
  }

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Branch');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Branch mining`;
  }
}
</script>
