<template>
  <div class="sandbox data connection">
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
          <a class="item">List of connection</a>
          <a class="item">New connection</a>
          <a class="item">Upload</a>
        <!-- </div> -->
      </template>

      <!-- Depth Two Left Sidebar Menu Item -->
      <template slot="depth-two-menu-item">
        <ItemUploadComponent></ItemUploadComponent>
      </template>

      <!-- Content -->
      <template slot="content">
        <ContentOraclePropertiesComponent></ContentOraclePropertiesComponent>
        <!-- <ContentFileQueryComponent></ContentFileQueryComponent> -->
        <!-- <ContentDataViewComponent></ContentDataViewComponent> -->
        <!-- <ContentDataUploadComponent></ContentDataUploadComponent> -->
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
import Layout02View from '../classes/Layout02';
import DepthTwoLeftWrapperComponent from '../components/data-connection-components/DepthTwoLeftWrapperComponent.vue';
import ContentOraclePropertiesComponent from '../components/data-connection-components/ContentOraclePropertiesComponent.vue';
import ContentMssqlPropertiesComponent from '../components/data-connection-components/ContentMssqlPropertiesComponent.vue';
import ContentMysqlPropertiesComponent from '../components/data-connection-components/ContentMysqlPropertiesComponent.vue';
import ContentHadoopPropertiesComponent from '../components/data-connection-components/ContentHadoopPropertiesComponent.vue';
import ContentDataViewComponent from '../components/data-connection-components/ContentDataViewComponent.vue';
import ContentDataUploadComponent from '../components/data-connection-components/ContentDataUploadComponent.vue';
import ContentQueryComponent from '../components/data-connection-components/ContentQueryComponent.vue';
import ContentFileQueryComponent from '../components/data-connection-components/ContentFileQueryComponent.vue';
import ItemUploadComponent from '../components/data-connection-components/ItemUploadComponent.vue';
import ItemConnectionComponent from '../components/data-connection-components/ItemConnectionComponent.vue';

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
export default class SandBoxDataConnection extends Layout02View {
  public title: string = '';


  public mounted(): void { // implement business logic
   // alert('ding! DD ppang juwa');
  }

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: Connection');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Connection`;
  }
}
</script>
