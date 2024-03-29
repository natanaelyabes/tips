<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="sandbox uploadfile data connection">
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
        <a href="/#/iochord/ips/data/connection" class="item"><i class="list icon"></i>List of connections</a>
        <a href="/#/iochord/ips/data/connection/new" class="item"><i class="database icon"></i>New connection</a>
        <a href="/#/iochord/ips/data/connection/upload" class="item active"><i class="upload icon"></i>File upload</a>
      </template>

      <!-- Depth Two Left Sidebar Menu Item -->
      <template slot="depth-two-menu-item">
        <ItemUploadComponent></ItemUploadComponent>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentDataUploadComponent></ContentDataUploadComponent>
      </template>
    </DepthTwoLeftWrapperComponent>
  </div>
</template>

<style>
.sandbox.uploadfile.data.connection {
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
  },
})

/**
 * Data connection upload view.
 *
 * @export
 * @class DataConnectionNew
 * @extends {ExplorerLayoutView}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class DataConnectionUpload extends ExplorerLayoutView {

  /**
   * The title of data connection upload.
   *
   * @type {string}
   * @memberof DataConnectionUpload
   */
  public title: string = '';

  /**
   * Override the browser properties.
   *
   * @memberof DataConnectionUpload
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: File upload');
  }

  /**
   * Assigns title to the data connection upload.
   *
   * @memberof DataConnectionUpload
   */
  public setTitle(): void {
    this.title = `Upload`;
  }
}
</script>
