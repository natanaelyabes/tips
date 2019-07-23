<template>
  <div class="sandbox data connection">
    <DepthTwoWrapperComponent>
      <template slot="header-title">
      </template>

      <template slot="header-breadcrumb">
        <router-link to="/iochord/chdsr" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Data Management</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <template slot="depth-one-menu-item">test</template>
      <template slot="depth-two-menu-item">test</template>
      <template slot="content">test</template>
    </DepthTwoWrapperComponent>
  </div>



</template>

<style>
.sandbox.data.connection {
  height: 100%;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import PageLayout from '../../common/ui/layout/classes/PageLayout';
import DepthTwoWrapperComponent from '../components/data-connection-components/DepthTwoWrapperComponent.vue';

@Component({
  components: {
    DepthTwoWrapperComponent
  }
})
export default class SandBoxDataConnection extends PageLayout{
  public title: string = '';

  public mounted(): void { //implement business logic
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
