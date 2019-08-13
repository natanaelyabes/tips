<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="editor view">

    <WrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/chdsr" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Simulation Editor</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
      </template>

      <!--  Left Sidebar Menu Item -->
      <template slot="left-sidebar-menu-item">
        <ControlPaletteComponent />
        <ToolboxPaletteComponent />
        <DataPaletteComponent />
      </template>

      <!--  Ribbon Menu Item -->
      <template slot="ribbon-menu-item">
        <!-- Left menu item -->
        <SimulationPlayerComponent />

        <!-- Right menu item -->
        <div id="ribbon-right-menu" class="right menu">
          <SimulationDataManagementComponent />
        </div>
      </template>

      <!-- Content -->
      <template slot="content">
        <CanvasComponent :key="reRenderKey" v-bind:response="graphData" />
      </template>

      <template slot="right-sidebar-menu-item">
        <!-- TODO: Minimap component --->
        <div class="ui basic segment" style="width: 260px">
          <h2>Model Pane</h2>
          <div id="minimap"></div>
        </div>
      </template>
    </WrapperComponent>
  </div>
</template>

<style scoped>
/**
 *
 * @package chdsr
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

.corner.area {
  position: absolute;
  bottom: 0;
  right: 0;
  margin: 20px;
}

.corner.area .zoom.tool {
  margin-bottom: 2em;
}

.corner.area .slider-wrapper {
  height: 200px;
  position: relative;
  right: 0;
  margin-bottom: 14em;
}

.corner.area .slider-wrapper .ui.vertical.slider {
  clear: both;
  padding: 2em 0;
  margin-left: auto;
  right: 11px;
}

#minimap {
  cursor: pointer;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  border: 1px solid black;
  transition: all 0.3s cubic-bezier(.25,.8,.25,1);
  width: 100%;
  height: 100%;
}

#minimap:hover {
  box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
}

@media screen and (max-width: 1440px) {
  #ribbon-right-menu {
    margin-left: 50px!important;
    margin-right: 110px!important;
  }
}
</style>

<style>
/**
 *
 * @package chdsr
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
import Layout01View from '@/iochord/chdsr/common/ui/layout/classes/Layout01';
import { GraphImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphImpl';

// Interfaces
import { Layout01HasWrapper } from '@/iochord/chdsr/common/ui/layout/interfaces/Layout01HasWrapper';
import { BrowserHasProperties } from '@/iochord/chdsr/common/browser/interfaces/BrowserHasProperties';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic-components/SemanticModulesIsUsed';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Services
import { SbpnetModelService } from '@/iochord/chdsr/common/service/model/SbpnetModelService';

// Components
import WrapperComponent from '@/iochord/chdsr/common/ui/layout/components/WrapperComponent.vue';
import ControlPaletteComponent from '@/iochord/chdsr/simulation/editor/components/palette/ControlPaletteComponent.vue';
import ToolboxPaletteComponent from '@/iochord/chdsr/simulation/editor/components/palette/ToolboxPaletteComponent.vue';
import DataPaletteComponent from '@/iochord/chdsr/simulation/editor/components/palette/DataPaletteComponent.vue';
import SimulationPlayerComponent from '../components/ribbon/SimulationPlayerComponent.vue';
import SimulationDataManagementComponent from '../components/ribbon/SimulationDataManagementComponent.vue';

// Vuex & rxjs
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';

// Async component must be lazily load
const CanvasComponent = () => import('@/iochord/chdsr/simulation/editor/components/canvas/CanvasComponent.vue');

// Vuex module
const graphModule = getModule(GraphModule);

declare const $: any;

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component<SimulationEditorView>({
  components: {
    WrapperComponent,
    CanvasComponent,
    ControlPaletteComponent,
    ToolboxPaletteComponent,
    DataPaletteComponent,
    SimulationPlayerComponent,
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
  public processModel: any;
  public animation: boolean = false;
  public editing: boolean = true;

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

      // Fetch graph to Vuex state
      await graphModule.fetchGraph();

      // Update rxjs subject
      GraphSubject.update(graphModule.graph);

      console.log(graphModule.graph);

      this.$observables.graph.subscribe((graph: Graph) => {
        console.log(graph);
        graphModule.setGraph(graph);
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

  public get graphData(): Graph | undefined {
    return graphModule.graph;
  }
}
</script>
