<template>
  <div class="sandbox data management">
    <DepthTwoLeftWrapperComponent>
      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/chdsr" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Management</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!-- Depth One Left Sidebar Menu Item -->
      <template slot="depth-one-menu-item">
        <!-- <div class="menu"> -->
          <a class="item">File history</a>
          <a class="item">Data filtering</a>
          <a class="item">Data mapping</a>
        <!-- </div> -->
      </template>

      <!-- Depth Two Left Sidebar Menu Item -->
      <template slot="depth-two-menu-item">
        <ItemFilterComponent></ItemFilterComponent>
      </template>

       <!-- Content -->
      <template slot="content">
        <ContentFilterTimeComponent></ContentFilterTimeComponent>
      </template>
    </DepthTwoLeftWrapperComponent>
  </div>
</template>

<style>
.sandbox.data.management {
  height: 100%;
}

a.section {
  color: white!important;
  text-decoration: underline;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import Layout02View from '@/iochord/chdsr/sandbox/riska/SandboxDataConnection/classes/Layout02';
import DepthTwoLeftWrapperComponent from '@/iochord/chdsr/sandbox/riska/SandboxDataConnection/components/DepthTwoLeftWrapperComponent.vue';
import ItemFilterComponent from '../components/ItemFilterComponent.vue';
import ContentHistoryComponent from '../components/ContentHistoryComponent.vue';
import ContentMappingComponent from '../components/ContentMappingComponent.vue';
import ContentFilterTimeComponent from '../components/ContentFilterTimeComponent.vue';
import ContentFilterPerformanceComponent from '../components/ContentFilterPerformanceComponent.vue';
import ContentFilterAttributesComponent from '../components/ContentFilterAttributesComponent.vue';
import ContentFilterFollowerComponent from '../components/ContentFilterFollowerComponent.vue';
import ContentFilterPointsComponent from '../components/ContentFilterPointsComponent.vue';

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
    ItemFilterComponent,
    ContentHistoryComponent,
    ContentMappingComponent,
    ContentFilterTimeComponent,
    ContentFilterPerformanceComponent,
    ContentFilterAttributesComponent,
    ContentFilterFollowerComponent,
    ContentFilterPointsComponent,
  },
})
export default class SandboxDataManagement extends Layout02View {
  public title: string = '';


  public mounted(): void { // implement business logic

  }

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: Management');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Management`;
  }
}
</script>
