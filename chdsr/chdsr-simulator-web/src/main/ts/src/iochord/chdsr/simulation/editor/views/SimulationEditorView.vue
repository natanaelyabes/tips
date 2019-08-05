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
        <div class="item">
          <div class="header">Control</div>
          <div class="menu">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/control/control.png" alt="" class="ui centered image" />
              </div>
              Control
            </a>
            <a class="ui basic button item">
              <!-- <i class="big cog icon"></i> -->
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/control/configuration.png" alt="" class="ui centered image" />
              </div>
              Configuration
            </a>
          </div>
        </div>
        <div class="item">
          <div class="header">Toolbox</div>
          <div class="menu">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_start.png" alt="" class="ui centered image" />
              </div>
              Start
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_stop.png" alt="" class="ui centered image" />
              </div>
              Stop
            </a>
            <a @click.prevent="fn_create_node($event);" class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_activity.png" alt="" class="ui centered image" />
              </div>
              Activity
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_branch.png" alt="" class="ui centered image" />
              </div>
              Branch
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/toolbox/toolbox_connector.png" alt="" class="ui centered image" />
              </div>
              Connector
            </a>
          </div>
        </div>
        <div class="item">
          <div class="header">Data Toolbox</div>
          <div class="menu">
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/datatable.png" alt="" class="ui centered image" />
              </div>
              Data Table
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/generator.png" alt="" class="ui centered image" />
              </div>
              Generator
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/object_type.png" alt="" class="ui centered image" />
              </div>
              Object Type
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/function.png" alt="" class="ui centered image" />
              </div>
              Function / Method
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/resources.png" alt="" class="ui centered image" />
              </div>
              Resources
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/kpi.png" alt="" class="ui centered image" />
              </div>
              Monitor KPI
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/simulation_editor_icon/data_toolbox/queue.png" alt="" class="ui centered image" />
              </div>
              Queue
            </a>
          </div>
        </div>
      </template>

      <!--  Ribbon Menu Item -->
      <template slot="ribbon-menu-item">
        <div class="item"><div class="header"><strong>Simulation Player</strong></div></div>
        <div class="item">
          <div class="ui basic icon buttons">
            <a class="ui button"><i class="step backward icon"></i></a>
            <a class="ui button"><i class="backward icon"></i></a>
            <a class="ui button"><i class="play icon"></i></a>
            <a class="ui button"><i class="stop icon"></i></a>
            <a class="ui button"><i class="forward icon"></i></a>
            <a class="ui button"><i class="step forward icon"></i></a>
          </div>
        </div>
        <div class="right menu">
          <div class="item"><div class="header"><strong>Simulation Data Management</strong></div></div>
          <div class="item">
            <div class="ui basic icon buttons">
              <a class="ui button" title="Save model"><i class="save icon"></i></a>
              <a class="ui button" title="Upload model" @click="showUploadFileModal"><i class="upload icon"></i></a>
              <a class="ui button" title="Download model"><i class="download icon"></i></a>
              <a class="ui button" title="Show report"><i class="file outline alternate icon"></i></a>
            </div>
          </div>
          <div class="item">
            <div @click="toggleModelPane()" class="ui basic icon button" title="Open model pane"><i class="sidebar icon"></i></div>
          </div>
        </div>
      </template>

      <!--  Content -->
      <template slot="content">
        <template v-if="graphData !== {}">
          <CanvasComponent :key="reRenderKey" v-bind:response="graphData" />
        </template>
      </template>

      <template slot="right-sidebar-menu-item">
        <div class="ui basic segment" style="width: 260px">
          <h2>Model Pane</h2>
          <div id="minimap"></div>
        </div>
      </template>
    </WrapperComponent>

    <!-- Upload Modals -->
    <div class="ui upload file modal">
      <i class="close icon"></i>
      <div class="header">
        Upload Model
      </div>
      <div class="content">
        <div class="description">
          <div class="ui header">Upload a json file by paste it in the form below.</div>
          <p>This is an experimental feature.</p>
          <div class="ui form">
            <div style="width:100%" class="ui labeled input">
              <textarea></textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui positive right labeled icon button">
          Upload
          <i class="upload icon"></i>
        </div>
        <div class="ui black deny button">
          Cancel
        </div>
      </div>
    </div>
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

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// Classes
import Layout01View from '@/iochord/chdsr/common/ui/layout/classes/Layout01';

// Interfaces
import { Layout01HasWrapper } from '@/iochord/chdsr/common/ui/layout/interfaces/Layout01HasWrapper';
import { BrowserHasProperties } from '@/iochord/chdsr/common/browser/interfaces/BrowserHasProperties';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic-components/SemanticModulesIsUsed';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Components
import WrapperComponent from '@/iochord/chdsr/common/ui/layout/components/WrapperComponent.vue';
import { SbpnetModelService } from '@/iochord/chdsr/common/service/model/SbpnetModelService';
import { GraphImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphImpl';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import GraphSubject from '../../../common/graph/sbpnet/rxjs/GraphSubject';

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
  public modelPaneIsOpen: boolean = true;

  /** @Override */
  public async mounted(): Promise<void> {
    try {
      this.$observables.graph.subscribe((graph: Graph) => {
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

  public get graphData(): Graph {
    return graphModule.graph;
  }

  /** @Override */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Simulation Editor: Editor');
  }

  /** @Override */
  public setTitle(): void {
    this.title = `Editor`;
  }

  private showUploadFileModal(): void {
    $('.ui.modal').modal('show');
  }

  private toggleModelPane(): void {
    if (this.modelPaneIsOpen) {
      this.modelPaneIsOpen = false;
      $('#canvas').width($('.editor.canvas').innerWidth() + 260);
    } else {
      this.modelPaneIsOpen = true;
      $('#canvas').width($('.editor.canvas').innerWidth() - 260);
    }
  }
}
</script>
