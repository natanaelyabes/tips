<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
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

      <!-- Content -->
      <template slot="content">
        <ContentMappingComponent ref="mapping" id="mapping"></ContentMappingComponent>
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
import ContentMappingComponent from '../components/ContentMappingComponent.vue';
import MappingService from '../services/MappingService';
import IMappingResource from '../interfaces/IMappingResource';
import MappingResource from '../models/MappingResource';
import MappingModule from '../stores/MappingModule';
import { getModule } from 'vuex-module-decorators';

// Vuex module
const mappingModule = getModule(MappingModule);

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
    ContentMappingComponent,
  },
})

/**
 * The content mapping component.
 *
 * @export
 * @class ContentMappingComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class DataMapping extends ExplorerLayoutView {
  public title: string = '';

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: Data Mapping');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Data Mapping`;
  }
}
</script>
