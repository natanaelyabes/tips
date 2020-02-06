<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="editor view">

    <!-- WrapperComponent -->
    <WrapperComponent>

      <!-- Content -->
      <template slot="content">
        <CanvasComponent :isProcessModel="true" :isDisabled="false" :key="reRenderKey" v-bind:response="graphData" style="min-height: 75vh;" />
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

.canvas.component {
  height: calc(100% - 160px)!important;
}

.canvas.component .editor.canvas {
  width: 100%;
  height: 100%;
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
import AppLayoutView from '@/iochord/ips/common/ui/layout/class/AppLayoutView';
import { GraphImpl } from '@/iochord/ips/common/graph/ism/class/GraphImpl';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';
import { GraphConnectorImpl } from '@/iochord/ips/common/graph/ism/class/GraphConnectorImpl';
import { GraphPageImpl } from '@/iochord/ips/common/graph/ism/class/GraphPageImpl';

// Interfaces
import { AppLayout } from '@/iochord/ips/common/ui/layout/interfaces/AppLayout';
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
import NodePaletteComponent from '../components/palette/NodePaletteComponent.vue';
import DataPaletteComponent from '../components/palette/DataPaletteComponent.vue';
import SimulationPlayerComponent from '../components/ribbon/SimulationPlayerComponent.vue';
import SimulationDataManagementComponent from '../components/ribbon/SimulationDataManagementComponent.vue';

// Vuex & rxjs
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

import CanvasComponent from '../components/canvas/CanvasComponent.vue';
import MinimapComponent from '../components/minimap/MinimapComponent.vue';

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
@Component({
  components: {
    WrapperComponent,
    CanvasComponent,
    MinimapComponent,
    ControlPaletteComponent,
    NodePaletteComponent,
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
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class ModelViewer extends AppLayoutView {

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
        await graphModule.loadGraph();

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
        console.log(JSON.stringify(graph));
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
