<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="sandbox data management">
    <WrapperComponent>

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
        <ContentHistoryComponent></ContentHistoryComponent>
      </template>
    </WrapperComponent>
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
import WrapperComponent from '@/iochord/ips/common/ui/layout/components/WrapperComponent.vue';
import ContentHistoryComponent from '../components/ContentHistoryComponent.vue';

@Component({
  components: {
    WrapperComponent,
    ContentHistoryComponent,
  },
})
/**
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default class DataHistory extends ExplorerLayoutView {
  public title: string = '';

  /** @override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Management: Data History');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Data History`;
  }
}
</script>
