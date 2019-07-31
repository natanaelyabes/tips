<!--
  @package chdsr
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
            <router-link tag="a" class="item" to="#">Mapping</router-link>
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
/**
 *
 * @package chdsr
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

// Interfaces
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic-components/SemanticModulesIsUsed';

// Components
import NavigationTopSidebarComponent from '@/iochord/chdsr/common/ui/semantic-components/navigations/components/NavigationTopSidebarComponent.vue';
import IndexLayout from '@/iochord/chdsr/common/ui/layout/classes/IndexLayout';
import { getModule } from 'vuex-module-decorators';
import GraphModule from './common/graph/sbpnet/stores/GraphModule';
import GraphSubject from './common/graph/sbpnet/rxjs/GraphSubject';
import { Graph } from './common/graph/sbpnet/interfaces/Graph';

// JQuery Symbol Handler
declare const $: any;


const graphModule = getModule(GraphModule);

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component<ChdsrIndexView>({
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
export default class ChdsrIndexView extends IndexLayout {
  /** @Override */
  public async mounted(): Promise<void> {
    await this.declareSemanticModules();

    // Fetch graph to Vuex state
    await graphModule.fetchGraph();

    // Update rxjs subject
    GraphSubject.update(graphModule.graph);

    // Listen to changes
    this.$observables.graph.subscribe((graph: Graph) => {
      graphModule.setGraph(graph);
    });
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
