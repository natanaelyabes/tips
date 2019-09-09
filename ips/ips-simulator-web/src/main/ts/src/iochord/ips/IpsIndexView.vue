<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
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
      <div slot="sidebar-menu">
        <router-link tag="a" class="item" to="/iochord/ips">Home <i class="home icon"></i></router-link>
        <!-- <router-link tag="a" class="item" to="/iochord/ips/sandbox">Sandbox <i class="inbox icon"></i></router-link> -->
        <div class="item">
          <div class="header" style="text-decoration:underline">Data Management</div>
          <div class="menu">
            <!-- <router-link tag="a" class="item" to="#">Uploagd / Open</router-link> -->
            <router-link tag="a" class="item" to="/iochord/ips/data-connection">Connection / Import</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/data-filter">Filter</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/data-mapping">Mapping</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/data-history">History / Browser</router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Analysis / Algorithm</div>
          <div class="menu">
            <router-link tag="a" class="item" to="/iochord/ips/analysis-process-model">Process Model</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/analysis-branches-mining">Branches Mining</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/analysis-resource-mining">Resource Mining</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/analysis-distribution-fitting">Distribution Fitting</router-link>
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
            <router-link tag="a" class="item" to="/iochord/ips/simulation-editor">Editor</router-link>
            <router-link tag="a" class="item" to="#">Report</router-link>
            <router-link tag="a" class="item" to="#">Export</router-link>
          </div>
        </div>
      </div>
    </NavigationTopSidebarComponent>
  </div>
</template>

<style>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.index.view {
  height: 100%;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Interfaces
import { SemanticModulesIsUsed } from '@/iochord/ips/common/ui/semantic-components/SemanticModulesIsUsed';

// Layouts
import IndexLayout from '@/iochord/ips/common/ui/layout/class/IndexLayout';

// Components
import NavigationTopSidebarComponent from '@/iochord/ips/common/ui/semantic-components/navigations/components/NavigationTopSidebarComponent.vue';
import { Graph } from './common/graph/ism/interfaces/Graph';

// Vuex & Rxjs
import GraphModule from './common/graph/ism/stores/GraphModule';
import GraphSubject from './common/graph/ism/rxjs/GraphSubject';

// JQuery Symbol Handler
declare const $: any;


const graphModule = getModule(GraphModule);

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component<IpsIndexView>({
  components: {
    NavigationTopSidebarComponent,
  },
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class IpsIndexView extends IndexLayout {

  /** @Override */
  public async mounted(): Promise<void> {
    await this.declareSemanticModules();
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
    this.$router.push({name: 'iochord-ips-login'});
  }
}
</script>
