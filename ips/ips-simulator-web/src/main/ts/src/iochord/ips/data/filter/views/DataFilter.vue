<template>
  <div class="sandbox data management">
    <DepthTwoLeftWrapperComponent>
      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Management</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!-- Depth One Left Sidebar Menu Item -->
      <template slot="depth-one-menu-item">
        <!-- <div class="menu"> -->
          <!-- <a href="/#/iochord/ips/sandbox/sandbox-data-management-history" class="item">File history</a>
          <a href="/#/iochord/ips/sandbox/sandbox-data-management-filter" class="item">Data filtering</a>
          <a href="/#/iochord/ips/sandbox/sandbox-data-management-mapping" class="item">Data mapping</a> -->
        <!-- </div> -->
      </template>

      <!-- Depth Two Left Sidebar Menu Item -->
      <template slot="depth-two-menu-item">
        <ItemFilterComponent></ItemFilterComponent>
      </template>

       <!-- Content -->
      <template slot="content">
        <component :is="currentContentComponent"></component>
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
import Layout02View from '@/iochord/ips/common/ui/layout/class/Layout02';
import DepthTwoLeftWrapperComponent from '@/iochord/ips/common/ui/layout/components/DepthTwoLeftWrapperComponent.vue';
import ItemFilterComponent from '../components/ItemFilterComponent.vue';
import ContentFilterTimeComponent from '../components/ContentFilterTimeComponent.vue';
import ContentFilterPerformanceComponent from '../components/ContentFilterPerformanceComponent.vue';
import ContentFilterAttributesComponent from '../components/ContentFilterAttributesComponent.vue';
import ContentFilterFollowerComponent from '../components/ContentFilterFollowerComponent.vue';
import ContentFilterPointsComponent from '../components/ContentFilterPointsComponent.vue';
import ContentFilterMissingImputationComponent from '../components/ContentFilterMissingImputationComponent.vue';

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
    ItemFilterComponent,
    ContentFilterTimeComponent,
    ContentFilterPerformanceComponent,
    ContentFilterAttributesComponent,
    ContentFilterFollowerComponent,
    ContentFilterPointsComponent,
    ContentFilterMissingImputationComponent,
  },
})
export default class DataFilter extends Layout02View {
  public title: string = '';
  public currentContentComponent: string = 'ContentFilterTimeComponent';

  public mounted(): void { // implement business logic
    this.$root.$on('ebContentComponent', (prm: string) => {
      this.currentContentComponent = prm;
    });
  }

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: Data Filtering');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Data Filtering`;
  }
}
</script>
