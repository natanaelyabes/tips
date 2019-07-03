<template>
  <div class="index view">
    <NavigationTopSidebarComponent>
      <template slot="top-right-menu-item">
        <!-- Right menu item -->
        <div class="right menu">
          <div class="ui dropdown item">
            <img class="ui avatar image" src="@/assets/images/avatars/steve.jpg">
              Administrator
            <i class="dropdown icon"></i>
            <div class="menu">
              <a class="item" v-on:click="logout()"><i class="power icon"></i> Logout</a>
            </div>
          </div>
        </div>
      </template>
      <div slot="app-logo">
        <router-link to="/iochord" tag="a" class="logo item">
          <img id="app-logo" class="ui centered small image" src="@/assets/images/logos/iochord_logo_transparent_color.png" alt="main logo">
        </router-link>
      </div>
      <!--  -->
      <div slot="sidebar-menu">
        <router-link tag="a" class="item" to="/iochord/chdsr">Home <i class="home icon"></i></router-link>
        <router-link tag="a" class="item" to="/iochord/chdsr/sandbox">Sandbox <i class="inbox icon"></i></router-link>
        <div class="item">
          <div class="header" style="text-decoration:underline">Data Management</div>
          <div class="menu">
            <router-link tag="a" class="item" to="#">Upload / Open</router-link>
            <router-link tag="a" class="item" to="#">Connection</router-link>
            <router-link tag="a" class="item" to="#">History / Browser</router-link>
            <router-link tag="a" class="item" to="#">Filter</router-link>
            <router-link tag="a" class="item" to="#">Mapping></router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Analysis / Algorithm</div>
          <div class="menu">
            <router-link tag="a" class="item" to="#">Process Model</router-link>
            <router-link tag="a" class="item" to="#">Branches Mining</router-link>
            <router-link tag="a" class="item" to="#">Resource Mining</router-link>
            <router-link tag="a" class="item" to="#">Distribution Fitting</router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Simulation Generation</div>
          <div class="menu">
            <router-link tag="a" class="item" to="#">Simulation Discovery</router-link>
            <router-link tag="a" class="item" to="#">Hybrid Simulation</router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Simulation Editor</div>
          <div class="menu">
            <router-link tag="a" class="item" to="/iochord/chdsr/simulation-editor">Editor</router-link>
            <router-link tag="a" class="item" to="#">Report</router-link>
            <router-link tag="a" class="item" to="#">Export</router-link>
          </div>
        </div>
      </div>
    </NavigationTopSidebarComponent>
  </div>
</template>

<style>
.index.view {
  height: 100%;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Interfaces
import { VueHasLifeCycle } from '@/iochord/chdsr/common/lib/vue/interfaces/VueHasLifeCycle';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic/SemanticModulesIsUsed';

// Components
import NavigationTopSidebarComponent from '@/iochord/chdsr/common/ui/semantic/navigations/components/NavigationTopSidebarComponent.vue';
import IndexView from '@/iochord/chdsr/common/lib/vue/classes/IndexView';

// JQuery Symbol Handler
declare const $: any;

@Component({
  components: {
    NavigationTopSidebarComponent,
  },
})
export default class Index extends IndexView {
  /** @Override */
  public mounted(): void {
    this.declareSemanticModules();
  }

  /** @Override */
  public declareSemanticModules(): void {
    this.declareDropdown();
  }

  /** @Override */
  public declareDropdown(): void {
    $('.ui.dropdown').dropdown();
  }

  /**
   * Handle logout logic from view
   *
   * @memberof Index
   */
  private logout(): void {
    this.$router.push({name: 'iochord-chdsr-login'});
  }
}
</script>
