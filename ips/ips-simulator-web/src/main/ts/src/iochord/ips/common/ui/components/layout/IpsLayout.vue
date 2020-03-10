<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
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
        <router-link tag="a" class="item" to="/iochord/ips/home">Home <i class="home icon"></i></router-link>
        <div class="item">
          <div class="header" style="text-decoration:underline">Data Management</div>
          <div class="menu">
            <router-link tag="a" class="item" to="/iochord/ips/data/connection">Connection / Import</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/data/filter">Filter</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/data/mapping">Mapping</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/data/history">History / Browser</router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Analysis / Algorithm</div>
          <div class="menu">
            <router-link tag="a" class="item" to="/iochord/ips/analytics/process/discovery">Process Model</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/analytics/branch/mining">Branches Mining</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/analytics/resource/mining">Resource Mining</router-link>
            <router-link tag="a" class="item" to="/iochord/ips/analytics/distribution/fitting">Distribution Fitting</router-link>
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
            <router-link tag="a" class="item" to="/iochord/ips/simulation/editor">Editor</router-link>
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
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
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
import { SemanticModulesIsUsed } from '../../semantic-components/SemanticModulesIsUsed';

// Layouts
import IndexLayout from '../../layout/class/IndexLayout';

// Components
import NavigationTopSidebarComponent from '../../semantic-components/navigations/components/NavigationTopSidebarComponent.vue';
import { Graph } from '../../../graph/ism/interfaces/Graph';

// Vuex & Rxjs
import GraphModule from '../../../graph/ism/stores/GraphModule';
import GraphSubject from '../../../graph/ism/rxjs/GraphSubject';

// JQuery Symbol Handler
declare const $: any;

// Retreives vuex module
const graphModule = getModule(GraphModule);

@Component<IpsLayout>({
  components: { NavigationTopSidebarComponent },
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})

/**
 * The index layout for IPS application.
 *
 * @export
 * @class IpsLayout
 * @extends {IndexLayout}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class IpsLayout extends IndexLayout {

  /**
   *
   * The mounted lifecyle of Vue application.
   *
   * @override
   * @returns {Promise<void>}
   * @memberof IpsLayout
   */
  public async mounted(): Promise<void> {
    await this.declareSemanticModules();
  }

  /**
   * To run the jQuery semantic modules.
   *
   * @override
   * @memberof IpsLayout
   */
  public declareSemanticModules(): void {
    this.declareDropdown();
  }

  /**
   * Declare dropdown semantic module.
   *
   * @override
   * @memberof IpsLayout
   */
  public declareDropdown(): void {
    $('.ui.dropdown').dropdown();
  }

  /**
   * Handle logout logic from view.
   *
   * @memberof IpsLayout
   */
  private logout(): void {
    this.$router.push({name: 'iochord-ips-login'});
  }
}
</script>
