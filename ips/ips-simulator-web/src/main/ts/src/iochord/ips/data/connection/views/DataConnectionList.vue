<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="sandbox data connection">
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
        <a href="/#/iochord/ips/data/connection" class="item active"><i class="list icon"></i>List of connections</a>
        <a href="/#/iochord/ips/data/connection/new" class="item"><i class="database icon"></i>New connection</a>
        <a href="/#/iochord/ips/data/connection/upload" class="item"><i class="upload icon"></i>File upload</a>
      </template>

      <!-- Depth Two Left Sidebar Menu Item -->
      <template slot="depth-two-menu-item">
        <ItemExistedConnectionComponent></ItemExistedConnectionComponent>
      </template>

      <!-- Content -->
      <template slot="content">
        <component :is="currentContentComponent"></component>
      </template>
    </DepthTwoLeftWrapperComponent>
  </div>
</template>

<style>
.sandbox.data.connection {
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
import ContentOraclePropertiesComponent from '../components/ContentOraclePropertiesComponent.vue';
import ContentMssqlPropertiesComponent from '../components/ContentMssqlPropertiesComponent.vue';
import ContentMysqlPropertiesComponent from '../components/ContentMysqlPropertiesComponent.vue';
import ContentHadoopPropertiesComponent from '../components/ContentHadoopPropertiesComponent.vue';
import ContentDataViewComponent from '../components/ContentDataViewComponent.vue';
import ContentDataUploadComponent from '../components/ContentDataUploadComponent.vue';
import ContentQueryComponent from '../components/ContentQueryComponent.vue';
import ContentFileQueryComponent from '../components/ContentFileQueryComponent.vue';
import ItemUploadComponent from '../components/ItemUploadComponent.vue';
import ItemConnectionComponent from '../components/ItemConnectionComponent.vue';
import ItemExistedConnectionComponent from '../components/ItemExistedConnectionComponent.vue';

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
    ContentOraclePropertiesComponent,
    ContentMssqlPropertiesComponent,
    ContentMysqlPropertiesComponent,
    ContentHadoopPropertiesComponent,
    ContentDataViewComponent,
    ContentDataUploadComponent,
    ContentQueryComponent,
    ContentFileQueryComponent,
    ItemUploadComponent,
    ItemConnectionComponent,
    ItemExistedConnectionComponent,
  },
})

/**
 * Data connection list component.
 *
 * @export
 * @class DataConnectionList
 * @extends {ExplorerLayoutView}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class DataConnectionList extends ExplorerLayoutView {

  /**
   * Title of data connection list.
   *
   * @type {string}
   * @memberof DataConnectionList
   */
  public title: string = '';

  /**
   * Current content component.
   *
   * @type {string}
   * @memberof DataConnectionList
   */

  // TODO: Change to LogStatisticsComponent
  public currentContentComponent: string = 'ContentOraclePropertiesComponent';

  /**
   * Vue mounted lifecycle.
   *
   * @memberof DataConnectionList
   */
  public mounted(): void {
    this.$root.$on('ebContentComponent', (prm: string) => {
      this.currentContentComponent = prm;
    });
  }

  /**
   * Override browser properties.
   *
   * @override
   * @memberof DataConnectionList
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: List of connections');
  }

  /**
   * Assigns title to the layout.
   *
   * @memberof DataConnectionList
   */
  public setTitle(): void {
    this.title = `List of connections`;
  }
}
</script>
