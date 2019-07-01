<template>
  <div class="editor view">

    <ApplicationWrapperComponent>

      <!-- Header -->
      <!-- <template slot="application-header-title">Editor</template> -->
      <template slot="application-header-breadcrumb">
        <router-link to="/iochord/chdsr" tag="a" class="section">Home</router-link>
        <div class="divider"> / </div>
        <div class="section">Simulation Editor</div>
        <div class="divider"> / </div>
        <div class="active section">{{this.title}}</div>
      </template>

      <!-- Application Left Sidebar Menu Item -->
      <template slot="application-left-sidebar-menu-item">
        <div class="item">
          <div class="header">Control</div>
          <div class="menu">
            <a class="ui basic button item">
              <!-- <i class="big clock outline icon"></i> -->
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

      <!-- Application Ribbon Menu Item -->
      <template slot="application-ribbon-menu-item">
        <div class="item">
          <button v-if="editing" @click="editing = false" class="ui button"><i class="pencil icon"></i> Editing Mode</button>
          <button v-if="!editing" @click="editing = true" class="ui green button"><i class="running icon"></i> Run Simulation Mode</button>
        </div>
        <div class="right menu">
          <div class="item" :class="{ disabled: editing }"><div class="header"><strong>Simulation Player</strong></div></div>
          <div class="item" :class="{ disabled: editing }">
            <div class="ui basic icon buttons" :class="{ disabled: editing }">
              <a class="ui button"><i class="step backward icon"></i></a>
              <a class="ui button"><i class="backward icon"></i></a>
              <a class="ui button"><i class="play icon"></i></a>
              <a class="ui button"><i class="stop icon"></i></a>
              <a class="ui button"><i class="forward icon"></i></a>
              <a class="ui button"><i class="step forward icon"></i></a>
            </div>
          </div>
          <div class="item"><div class="header"><strong>Simulation Data Management</strong></div></div>
          <div class="item">
            <div class="ui basic icon buttons" :class="{ disabled: editing }">
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

      <!-- Application Content -->
      <template slot="application-content">
        <div class="editor canvas">
          <div id="canvas"></div>
        </div>
      </template>

      <template v-if="modelPaneIsOpen" slot="application-right-sidebar-menu-item">
        <div class="ui basic segment" style="width: 260px">
          <h2>Model Pane</h2>
          <div id="minimap"></div>
        </div>
      </template>
    </ApplicationWrapperComponent>

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

    <h1>{{parentSelectedGate}}</h1>

    <!-- Model Modals -->
    console.log(Array.from(nodeTypes));
    <template v-for="type in Array.from(nodeTypes)">
      <template v-if="type === 'start'">
        <StartNodeModal label="test" generator="test" v-bind:id="type" v-bind:key="type"/>
      </template>

      <template v-if="type === 'branch'">
        <BranchNodeModal label="this is branch" 
        @changeLabel="changeLabelFromChild($event)"
        @changeSelectedGate="selectedGateFromChild($event)"
        @changeSelectedType="selectedTypeFromChild($event)"
        @changeSelectedRule="selectedRuleFromChild($event)"
        v-bind:id="type" v-bind:key="type"/>
      </template>

      <template v-if="type === 'activity'">
        <ActivityNodeModal v-bind:id="type" v-bind:key="type"/>
      </template>

      <template v-if="type === 'stop'">
        <StopNodeModal v-bind:id="type" v-bind:key="type"/>
      </template>

    </template>

    {{ Array.from(nodeTypes) }}

  </div>
</template>

<style scoped>
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
/* Done */
.editor.view {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.editor.view .editor.canvas {
  width: 100%;
  height: 100%;
}

.pusher {
  overflow-y: hidden;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue, Watch } from 'vue-property-decorator';
import { setTimeout } from 'timers';
import axios, { AxiosResponse } from 'axios';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';
import '@/iochord/chdsr/common/lib/joint/shapes/chdsr/index';

// Interfaces
import { ApplicationHasWrapper } from '@/iochord/chdsr/common/ui/application/interfaces/ApplicationHasWrapper';
import { HasBreadcrumb } from '@/iochord/chdsr/common/ui/semantic/breadcrumbs/interfaces/HasBreadcrumb';

// Components
import ApplicationWrapperComponent from '@/iochord/chdsr/common/ui/application/components/ApplicationWrapperComponent.vue';
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';
import { GraphControlImpl } from '../../../common/graph/classes/components/GraphControlImpl';
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import { GraphImpl } from '@/iochord/chdsr/common/graph/classes/GraphImpl';
import { GraphPageImpl } from '../../../common/graph/classes/GraphPageImpl';
import { GraphPage } from '../../../common/graph/interfaces/GraphPage';
import { JointGraphPageImpl } from '../../../common/lib/joint/shapes/chdsr/classes/JointGraphPageImpl';
import { JointGraphNodeImpl } from '../../../common/lib/joint/shapes/chdsr/classes/JointGraphNodeImpl';
import { NODE_TYPE } from '../../../common/lib/joint/shapes/chdsr/enums/NODE';
import { JointGraphConnectorImpl } from '../../../common/lib/joint/shapes/chdsr/classes/JointGraphConnectorImpl';
import { GraphElement } from '../../../common/graph/interfaces/GraphElement';
import { ARC_TYPE } from '../../../common/lib/joint/shapes/chdsr/enums/ARC';
import { GraphNode } from '../../../common/graph/interfaces/GraphNode';
import { GraphConnector } from '../../../common/graph/interfaces/GraphConnector';
import { GraphData } from '../../../common/graph/interfaces/GraphData';

// Modals
import StartNodeModal from '../../../common/kpi/components/modals/StartNodeModal.vue';
import BranchNodeModal from '../../../common/kpi/components/modals/BranchNodeModal.vue';
import ActivityNodeModal from '../../../common/kpi/components/modals/ActivityNodeModal.vue';
import StopNodeModal from '../../../common/kpi/components/modals/StopNodeModal.vue';

declare const $: any;

@Component({
  components: {
    ApplicationWrapperComponent,
    StartNodeModal,
    BranchNodeModal,
    ActivityNodeModal,
    StopNodeModal
  },
})
export default class SimulationEditorView extends Vue implements ApplicationHasWrapper {
  public title: string = '';
  public breadcrumbs!: HasBreadcrumb[];
  public titleMenuBarItems: any;
  public leftMenuBarItems: any;
  public rightMenuBarItems: any;
  public ribbonMenuItems: any;
  public content: any;
  public processModel: any;
  public animation: boolean = false;
  public editing: boolean = true;
  public modelPaneIsOpen: boolean = true;

  public parentLabel: string = '';
  public parentSelectedGate: string = '';
  public parentSelectedType: string = '';
  public parentSelectedRule: string = '';

  // Joint.js global variable
  public graphPage: JointGraphPageImpl = new JointGraphPageImpl();

  // Find all node types in the graph
  public nodeTypes: Set<string> = new Set<string>();

  public changeLabelFromChild(e: any){
    this.parentLabel = e;
  }

  public selectedGateFromChild(e: any) {
    //console.log("Console Parent: "+e);
    this.parentSelectedGate = e;
  }

  public selectedTypeFromChild(e: any) {
    this.parentSelectedType = e;
  }

  public selectedRuleFromChild(e: any) {
    this.parentSelectedRule = e;
  }

  public async mounted(): Promise<void> {
    document.title = `${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME.toUpperCase()} · Simulation Editor: Editor`;
    await this.testGraphDataStruct();

    // Application options
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setLeftMenuSidebar();
    this.setRightMenuSidebar();
    this.setRibbonMenuItem();
    this.setContent();
    this.initDropdown();
    this.initSlider();
  }

  public toggleModelPane(): void {
    if (this.modelPaneIsOpen) {
      this.modelPaneIsOpen = false;
      $('#canvas').width($('.editor.canvas').innerWidth() + 260);

    } else {
      this.modelPaneIsOpen = true;
      $('#canvas').width($('.editor.canvas').innerWidth() - 260);
    }
  }

  public async testGraphDataStruct(): Promise<void> {
    try {

      // Load the model
      const response = await axios.get('http://192.168.11.154:3000/chdsr/api/v1/model/example');
      //const response = await axios.get('http://164.125.62.132:3001/chdsr/api/v1/model/example');

      console.log(response);

      // Deserialize the model
      const graph: Graph = GraphImpl.deserialize(response.data) as Graph;

      // Loop the model page
      for (const [key, value] of graph.getPages() as Map<string, GraphPage>) {
        const jointPage: JointGraphPageImpl = new JointGraphPageImpl();
        const canvasWidth: number = $('.editor.canvas').innerWidth();
        const canvasHeight: number = $('.editor.canvas').innerHeight();
        const keys: any = {
          elementType: 'elementType',
        };

        jointPage.setId(value.getId() as string);
        jointPage.setLabel(value.getLabel() as string);
        jointPage.setType(value.getType() as string);
        jointPage.setAttributes(value.getAttributes() as Map<string, string>);
        jointPage.setGraph(new joint.dia.Graph());
        jointPage.setNodes(value.getNodes() as Map<string, GraphNode>);
        jointPage.setArcs(value.getArcs() as Map<string, GraphConnector>);
        jointPage.setData(value.getData() as Map<string, GraphData>);

        // Set the paper as the graph container
        jointPage.setPaper(new joint.dia.Paper({
          el: document.getElementById('canvas'),
          model: jointPage.getGraph(),
          width: canvasWidth,
          height: canvasHeight,
          gridSize: 10,
          drawGrid: true,
          defaultRouter: {
            name: 'normal',
          },
          defaultAnchor: (endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) => {
            return this.customPerpendicularAnchor(endView, endMagnet, anchorReference, args);
          },
          defaultConnectionPoint: { name: 'boundary' },
          defaultConnector: {
            name: 'rounded',
          },
        } as joint.dia.Paper.Options ));

        // Set the minimap for each page
        jointPage.setMinimap(new joint.dia.Paper({
          el: document.getElementById('minimap'),
          model: jointPage.getGraph(),
          width: $('#minimap').parent().width(),
          height: 150,
          gridSize: 1,
        } as joint.dia.Paper.Options ));
        jointPage.getMinimap().scale(0.2);
        jointPage.getMinimap().translate($('#minimap').width() / 20, jointPage.getMinimap().options.height as number / 6);

        // for all nodes
        for (const [nodeKey, nodeValue] of jointPage.getNodes() as Map<string, GraphNode>) {
          const node = new JointGraphNodeImpl();

          node.setId(nodeValue.getId() as string);
          node.setLabel(nodeValue.getLabel() as string);
          node.setType((nodeValue as any)[keys.elementType] as string);
          node.setAttributes(nodeValue.getAttributes() as Map<string, string>);
          node.setPosition({ x: 300, y: 250 });
          node.setSize((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].size);
          node.setMarkup((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].markup);
          node.setAttr((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].attr);
          node.setImageIcon((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].image);

          // Demonstrate the use of custom icon
          if (nodeValue.getLabel() === 'ATM Service') {
            node.setImageIcon(require('@/assets/images/icons/atm-png.png'));
          } else if (nodeValue.getLabel() === 'Teller Service') {
            node.setImageIcon(require('@/assets/images/icons/business-customer-icon.png'));
          }

          // render node
          node.render(jointPage.getGraph());

          this.nodeTypes.add(node.getType() as string);
        }

        // for all connectors
        for (const [arcKey, arcValue] of jointPage.getArcs() as Map<string, GraphConnector>) {
          const arc = new JointGraphConnectorImpl();
          arc.setId(arcValue.getId() as string);
          arc.setLabel(arcValue.getLabel() as string);
          arc.setType((arcValue as any)[keys.elementType] as string);
          arc.setAttributes(arcValue.getAttributes() as Map<string, string>);
          arc.setSource(arcValue.getSource() as JointGraphNodeImpl);
          arc.setTarget(arcValue.getTarget() as JointGraphNodeImpl);
          arc.setAttr(ARC_TYPE.connector.attr);

          // render connector
          arc.render(jointPage.getGraph());
        }

        // Automatic layout
        joint.layout.DirectedGraph.layout(jointPage.getGraph(), {
          ranker: 'network-simplex',
          rankDir: 'LR',
          edgeSep: 300,
          nodeSep: 200,
          rankSep: 80,
          // align: 'UL',
        } as joint.layout.DirectedGraph.LayoutOptions);

        // Center the view
        jointPage.getPaper().translate(canvasWidth / 10, canvasHeight / 5);

        jointPage.getPaper().on({
          'element:contextmenu': (elementView) => {
            const currentElement = elementView.model;
            currentElement.attr('body/stroke', 'red');
            const currentElementType = currentElement.attributes.type;

            if (currentElementType === 'start') {
              $('#start').modal('show');
            }

            if (currentElementType === 'branch'){
              $('#branch').modal('show');
            }

            if (currentElementType === 'activity') {
              $('#activity').modal('show');
            }

            if (currentElementType === 'stop') {
              $('#stop').modal('show');
            }
          },
        });

        function resetAll(_paper: any) {

          /* Reset all elements in the paper */
          const elements = _paper.model.getElements();
          for (let i = 0, ii = elements.length; i < ii; i++) {
            const currentElement = elements[i];
            currentElement.attr('body/stroke', 'black');
          }

          const links = _paper.model.getLinks();
          for (let j = 0, jj = links.length; j < jj; j++) {
            const currentLink = links[j];
            currentLink.attr('line/stroke', 'black');
            currentLink.label(0, {
              attrs: {
                body: {
                  stroke: 'black',
                },
              },
            });
          }

        }

      }

      console.log(graph);
    } catch (e) {
      console.log(e);
    }
  }

  public showUploadFileModal(): void {
    $('.ui.modal').modal('show');
  }

  public initDropdown(): void {
    setTimeout(() => {
      $('.application.title-menu.bar.component .ui.dropdown').dropdown({
          on: 'hover',
      });
    }, 10);
  }

  public initSlider(): void {
    $('.ui.slider').slider({
      min: 0,
      max: 100,
      start: 50,
    });
  }

  public createNode(e: MouseEvent): void {
    alert('Not implemented yet!');
  }

  public setTitle(): void {
    this.title = `Editor`;
  }

  public setBreadcrumb(): void {
    //
  }
  public setTitleMenubar(): void {
    //
  }
  public setLeftMenuSidebar(): void {
    // TODO: define the data structure for toolbox here
  }
  public setRightMenuSidebar(): void {
    //
  }
  public setRibbonMenuItem(): void {
    //
  }
  public setContent(): void {
    //
  }

  /*
   * @author: clientIO
   * @url: https://github.com/clientIO/joint/blob/master/plugins/anchors/joint.anchors.js
   */
  public customPerpendicularAnchor(endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) {
    const angle: number = endView.model.angle();
    // const bbox: joint.g.Rect = endView.getNodeBBox(endMagnet);                           // instead of targeting endMagnet
    const bbox: joint.g.Rect = endView.getNodeBBox(endMagnet.childNodes[0] as SVGElement);  // We target it's child (rect)
    const anchor: joint.g.Point = bbox.center();
    const topLeft: joint.g.Point = bbox.origin();
    const bottomRight: joint.g.Point = bbox.corner();

    let padding = args.padding;
    if (!isFinite(padding)) { padding = 0; }

    if ((topLeft.y + padding) <= anchorReference.y && anchorReference.y <= (bottomRight.y - padding)) {
        const dy = (anchorReference.y - anchor.y);
        anchor.x += (angle === 0 || angle === 180) ? 0 : dy * 1 / Math.tan(joint.g.toRad(angle));
        anchor.y += dy;
    } else if ((topLeft.x + padding) <= anchorReference.x && anchorReference.x <= (bottomRight.x - padding)) {
        const dx = (anchorReference.x - anchor.x);
        anchor.y += (angle === 90 || angle === 270) ? 0 : dx * Math.tan(joint.g.toRad(angle));
        anchor.x += dx;
    }
    return anchor;
  }
}
</script>
