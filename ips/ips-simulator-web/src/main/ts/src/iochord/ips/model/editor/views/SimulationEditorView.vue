<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="editor view">

    <!-- WrapperComponent -->
    <WrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Simulation Editor</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!--  Left Sidebar Menu Item -->
      <template slot="left-sidebar-menu-item">
        <ControlPaletteComponent :isDisabled="isDisabled" />
        <ToolboxPaletteComponent :isDisabled="isDisabled" />
        <DataPaletteComponent :isDisabled="isDisabled" />
      </template>

      <!--  Ribbon Menu Item -->
      <template slot="ribbon-menu-item">

        <!-- Left menu item -->
        <SimulationDataManagementComponent
          :isDisabled="isDisabled"
          @create="modelCreate"
          @example="modelLoadExample" />

        <!-- Right menu item -->
        <div id="ribbon-player-menu" class="right menu">
          <SimulationPlayerComponent
            :isPlaying="isDisabled"
            @play="isDisabled = true; loadNPlay();"
            @stop="isDisabled = false" />
        </div>

        <div id="ribbon-layout-menu" class="right menu">
          <SimulationLayoutComponent
            @autolayout="modelAutoLayout"
            :isDisabled="isDisabled" />
        </div>
      </template>

      <!-- Content -->
      <template slot="content">
        <CanvasComponent :isDisabled="isDisabled" :key="reRenderKey" v-bind:response="graphData" />
      </template>

      <template slot="right-sidebar-menu-item">
        <MinimapComponent v-bind:response="graphData" />
      </template>
    </WrapperComponent>
  </div>
</template>

<style scoped>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.image-icon .ui.image {
  border-radius: 0;
  margin-bottom: .5em;
}

i.big.icon {
  margin-bottom: .25em!important;
}

@media screen and (max-width: 1440px) {
  #ribbon-player-menu {
    margin-left: 50px!important;
    margin-right: 110px!important;
  }
}
</style>

<style>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.editor.view {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.breadcrumb.component a.section {
  color: white;
  text-decoration: underline;
}

.pusher {
  overflow-y: hidden;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue, Watch } from 'vue-property-decorator';
import axios, { AxiosResponse, AxiosPromise } from 'axios';
import { getModule } from 'vuex-module-decorators';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// Classes
import Layout01View from '@/iochord/ips/common/ui/layout/class/Layout01';
import { GraphImpl } from '@/iochord/ips/common/graph/ism/class/GraphImpl';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';
import { GraphConnectorImpl } from '@/iochord/ips/common/graph/ism/class/GraphConnectorImpl';
import { GraphPageImpl } from '@/iochord/ips/common/graph/ism/class/GraphPageImpl';

// Interfaces
import { Layout01HasWrapper } from '@/iochord/ips/common/ui/layout/interfaces/Layout01HasWrapper';
import { BrowserHasProperties } from '@/iochord/ips/common/browser/interfaces/BrowserHasProperties';
import { SemanticModulesIsUsed } from '@/iochord/ips/common/ui/semantic-components/SemanticModulesIsUsed';
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Services
import { IsmModelService } from '@/iochord/ips/common/service/model/IsmModelService';

// Components
import WrapperComponent from '@/iochord/ips/common/ui/layout/components/WrapperComponent.vue';
import ControlPaletteComponent from '../components/palette/ControlPaletteComponent.vue';
import ToolboxPaletteComponent from '../components/palette/ToolboxPaletteComponent.vue';
import DataPaletteComponent from '../components/palette/DataPaletteComponent.vue';
import SimulationPlayerComponent from '../components/ribbon/SimulationPlayerComponent.vue';
import SimulationLayoutComponent from '../components/ribbon/SimulationLayoutComponent.vue';
import SimulationDataManagementComponent from '../components/ribbon/SimulationDataManagementComponent.vue';
import CanvasComponent from '../components/canvas/CanvasComponent.vue';
import MinimapComponent from '../components/minimap/MinimapComponent.vue';

// Vuex & rxjs
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

import { IsmSimulatorService } from '@/iochord/ips/common/service/simulator/IsmSimulatorService';

// Vuex module
const graphModule = getModule(GraphModule);

declare const $: any;

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component<SimulationEditorView>({
  components: {
    WrapperComponent,
    CanvasComponent,
    MinimapComponent,
    ControlPaletteComponent,
    ToolboxPaletteComponent,
    DataPaletteComponent,
    SimulationPlayerComponent,
    SimulationLayoutComponent,
    SimulationDataManagementComponent,
  },
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class SimulationEditorView extends Layout01View {

  public isDisabled: boolean = false;

  /** @Override */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Simulation Editor: Editor');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Editor`;
  }

  /** @Override */
  public async mounted(): Promise<void> {
    try {

      // TODO: Refractor to singleton pattern
      if (graphModule.graph.getVersion === undefined) {

        // Fetch graph to Vuex state
        await this.modelCreate();

        // Print to stdout
        console.log(graphModule.graph);
      }

      // Update rxjs subject
      GraphSubject.update(graphModule.graph);

      // When graph is updated
      this.$observables.graph.subscribe((graph: Graph) => {

        // Update its Vuex state
        graphModule.setGraph(graph);

        // Print to stdout
        console.log(graph);
      });
    } catch (e) {
      console.error(e);
    }

    // Force re-render page component
    this.forceReRender();

    // Always reload component when resizing content
    window.addEventListener('resize', () => {
      this.forceReRender();
    });
  }

  public async loadNPlay() {
    const cpnscala = await IsmSimulatorService.getInstance().postLoadNPlay(graphModule.graph);
  }

  public async modelCreate() {
    await graphModule.newGraph();
    this.forceReRender();
  }

  public async modelLoadExample() {
    await graphModule.loadExampleGraph();
    this.forceReRender();
  }

  public modelAutoLayout() {
    this.forceReRender();
  }

  public get graphData(): Graph | undefined {
    return graphModule.graph;
  }
}
</script>
