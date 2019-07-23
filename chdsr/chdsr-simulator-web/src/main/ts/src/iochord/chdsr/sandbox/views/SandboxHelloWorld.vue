<template>
  <div class="sandbox hello world view">
    <DepthTwoLeftWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/chdsr" tag="a" class="section" style="color: white;text-decoration:underline;">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Sandbox</div>
        <i class="right angle icon divider"></i>
        <div class="active section">Hello World</div>
      </template>

      <template slot="depth-one-menu-item">
        <div class="item">
          <div class="header">Products</div>
          <div class="menu">
            <a class="item">Enterprise</a>
            <a class="item">Consumer</a>
          </div>
        </div>
        <div class="item">
          <div class="header">CMS Solutions</div>
          <div class="menu">
            <a class="item">Rails</a>
            <a class="item">Python</a>
            <a class="item">PHP</a>
          </div>
        </div>
      </template>

      <template slot="depth-two-menu-item">
        <div class="item">
          <div class="header">Products</div>
          <div class="menu">
            <a class="item">Enterprise</a>
            <a class="item">Consumer</a>
          </div>
        </div>
        <div class="item">
          <div class="header">CMS Solutions</div>
          <div class="menu">
            <a class="item">Rails</a>
            <a class="item">Python</a>
            <a class="item">PHP</a>
          </div>
        </div>
      </template>

      <template slot="content">test</template>
    </DepthTwoLeftWrapperComponent>
  </div>
</template>

<style>
.sandbox.hello.world.view {
  height: 100%;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import PageLayout from '../../common/ui/layout/classes/PageLayout';
import DepthTwoLeftWrapperComponent from '../components/data-connection-component-yabes/DepthTwoLeftWrapperComponent.vue';
import NavigationBarComponent from '../../common/ui/semantic-components/navigations/components/NavigationBarComponent.vue';

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
    NavigationBarComponent,
  },
})
export default class SandboxHelloWorld extends PageLayout {

  public mounted(): void {
    // alert('Hello');
  }

  /** @Override */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Hello World');
  }
}
</script>
