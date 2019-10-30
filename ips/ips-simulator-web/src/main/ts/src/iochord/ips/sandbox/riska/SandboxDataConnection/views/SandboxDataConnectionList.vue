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
        <!-- <div class="menu"> -->
          <a href="/#/iochord/ips/sandbox/sandbox-data-connection-list" class="item">List of connection</a>
          <a href="/#/iochord/ips/sandbox/sandbox-data-connection-new" class="item">New connection</a>
          <a href="/#/iochord/ips/sandbox/sandbox-data-connection-upload" class="item">Import</a>
        <!-- </div> -->
      </template>

      <!-- Depth Two Left Sidebar Menu Item -->
      <template slot="depth-two-menu-item">
        <ItemExistedConnectionComponent></ItemExistedConnectionComponent>
      </template>

      <!-- Content -->
      <template slot="content">
        <component :is="currentContentComponent">
        </component>
        <!-- <ContentMysqlPropertiesComponent></ContentMysqlPropertiesComponent> -->
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
import Layout02View from '@/iochord/ips/sandbox/riska/SandboxDataConnection/class/Layout02';
import DepthTwoLeftWrapperComponent from '../components/DepthTwoLeftWrapperComponent.vue';
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
export default class SandboxDataConnectionList extends Layout02View {
  public title: string = '';
  public currentContentComponent: string = 'ContentOraclePropertiesComponent';

  public mounted(): void {
    this.$root.$on('ebContentComponent', (prm: string) => {
      this.currentContentComponent = prm;
    });
  }

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: List of Connections');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `List Connection`;
  }
}
</script>
