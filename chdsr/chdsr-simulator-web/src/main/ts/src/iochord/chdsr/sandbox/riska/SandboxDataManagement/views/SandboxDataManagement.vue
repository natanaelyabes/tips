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
        
      </template>

       <!-- Content -->
      <template slot="content">
      </template>
    </DepthTwoLeftWrapperComponent>
  </div>
</template>

<style>
.sandbox.data.management {
  height: 100%;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import Layout02View from '@/iochord/chdsr/sandbox/riska/SandboxDataConnection/classes/Layout02';
import DepthTwoLeftWrapperComponent from '@/iochord/chdsr/sandbox/riska/SandboxDataConnection/components/DepthTwoLeftWrapperComponent.vue';

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
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
