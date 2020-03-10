<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
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
      <template slot="depth-one-menu-item"></template>

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
import ExplorerLayoutView from '@/iochord/ips/common/ui/layout/class/ExplorerLayoutView';
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

/**
 * Data filter component.
 *
 * @export
 * @class DataFilter
 * @extends {ExplorerLayoutView}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class DataFilter extends ExplorerLayoutView {

  /**
   * Title of the data filter view.
   *
   * @type {string}
   * @memberof DataFilter
   */
  public title: string = '';

  /**
   * The content of current component.
   *
   * @type {string}
   * @memberof DataFilter
   */
  public currentContentComponent: string = 'ContentFilterTimeComponent';

  /**
   * Vue mounted lifecycle.
   *
   * @memberof DataFilter
   */
  public mounted(): void { // implement business logic
    this.$root.$on('ebContentComponent', (prm: string) => {
      this.currentContentComponent = prm;
    });
  }

  /**
   * Override browser properties.
   *
   * @override
   * @memberof DataFilter
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: Data Filtering');
  }

  /**
   * Assigns the title of the view.
   *
   * @override
   * @memberof DataFilter
   */
  public setTitle(): void {
    this.title = `Data Filtering`;
  }
}
</script>
