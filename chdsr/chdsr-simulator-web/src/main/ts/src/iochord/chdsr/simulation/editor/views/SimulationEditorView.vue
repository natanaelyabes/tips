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
              <i class="big clock outline icon"></i>
              Control
            </a>
            <a class="ui basic button item">
              <i class="big cog icon"></i>
              Configuration
            </a>
          </div>
        </div>
        <div class="item">
          <div class="header">Toolbox</div>
          <div class="menu">
            <a class="ui basic button item">
              <i class="big circle green outline icon"></i>
              Start
            </a>
            <a class="ui basic button item">
              <i class="big circle red icon"></i>
              Stop
            </a>
            <a @click.prevent="fn_create_node($event);" class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/activity.png" alt="" class="ui avatar centered image" />
              </div>
              Activity
            </a>
            <a class="ui basic button item">
              <i style="transform:rotate(45deg);" class="big square outline icon"></i>
              Branches
            </a>
            <div class="ui basic button item">
              <i class="big arrow right icon"></i>
              Connector
            </div>
          </div>
        </div>
        <div class="item">
          <div class="header">Data Toolbox</div>
          <div class="menu">
            <a class="ui basic button item">
              <i class="big table icon"></i>
              Data Table
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/declaration.png" alt="" class="ui avatar centered image" />
              </div>
              Object Type
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/function.png" alt="" class="ui avatar centered image" />
              </div>
              Function / Method
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/resource.png" alt="" class="ui avatar centered image" />
              </div>
              Resources
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/monitor_kpi.png" alt="" class="ui avatar centered image" />
              </div>
              Monitor KPI
            </a>
            <a class="ui basic button item">
              <div class="image-icon">
                <img src="@/assets/images/icons/queue.png" alt="" class="ui avatar centered image" />
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
          <div class="item" :class="{ disabled: editing }">
            <!-- <div class="ui toggle checkbox" :class="{ disabled: editing }">
              <input v-model="animation" type="checkbox" name="public">
              <label v-if="animation"><strong>Turn off animation</strong></label>
              <label v-else><strong>Turn on animation</strong></label>
            </div> -->
          </div>
          <div class="item" :class="{ disabled: editing }"><div class="header"><strong>Simulation Player</strong></div></div>
          <div class="item" :class="{ disabled: editing }">
            <div class="ui basic icon buttons" :class="{ disabled: editing }">
              <a class="ui button"><i class="fast backward icon"></i></a>
              <a class="ui button"><i class="backward icon"></i></a>
              <a class="ui button"><i class="play icon"></i></a>
              <a class="ui button"><i class="stop icon"></i></a>
              <a class="ui button"><i class="forward icon"></i></a>
              <a class="ui button"><i class="fast forward icon"></i></a>
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
          <!-- <div class="corner area">
            <div class="zoom tool">
              <div class="slider-wrapper">
                <div style="float: right; margin-bottom: 1em;">
                  <button class="ui circular icon button">
                    <i class="crosshairs icon"></i>
                  </button>
                </div>
                <div style="float: right; clear: both; margin-bottom: 1em;">
                  <button class="ui circular icon button">
                    <i class="expand icon"></i>
                  </button>
                </div>
                <div style="float: right; clear: both;">
                  <button class="ui circular icon button">
                    <i class="plus icon"></i>
                  </button>
                </div>
                <div class="ui vertical reversed blue slider"></div>
                <div style="float: right;">
                  <button class="ui circular icon button">
                    <i class="minus icon"></i>
                  </button>
                </div>
              </div>
            </div>
            <div id="canvas-minimap"></div>
          </div> -->
        </div>
      </template>

      <template v-if="modelPaneIsOpen" slot="application-right-sidebar-menu-item">
        <div class="ui basic segment" style="width: 260px">
          <h2>Model Pane</h2>
          <!-- <div id="canvas-minimap"></div> -->

        </div>
      </template>
    </ApplicationWrapperComponent>

    <!-- Modals -->
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
              <textarea v-model="model"></textarea>
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
.image-icon .ui.avatar.image {
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

#canvas-minimap {
  cursor: pointer;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  border: 1px solid black;
  transition: all 0.3s cubic-bezier(.25,.8,.25,1);
}

#canvas-minimap:hover {
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
import { Component, Vue } from 'vue-property-decorator';
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

declare const $: any;

@Component({
  components: {
    ApplicationWrapperComponent,
  },
})
export default class EditorView extends Vue implements ApplicationHasWrapper {
  // ApplicationWrapper global variable
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

  public model: string = '{"id":"Example Net","elementType":"sbpnet","attributes":{},"version":"1.0","pages":{"0":{"id":"0","elementType":"page","attributes":{},"data":{"0-0":{"id":"0-0","label":"Customer","elementType":"declaration","attributes":{}},"0-1":{"id":"0-1","label":"Customer MU","elementType":"movingunit","attributes":{},"declaration":{"id":"0-0","label":"Customer","elementType":"declaration","attributes":{}},"expression":"Exp(20","unit":"MINUTES","entitiesPerArrival":0,"maxArrival":100,"firstCreation":0},"0-2":{"id":"0-2","label":"Teller Queue","elementType":"queue","attributes":{},"type":"FIFO","size":35,"shared":false},"0-3":{"id":"0-3","label":"Teller Resource","elementType":"resource","attributes":{}},"0-4":{"id":"0-4","label":"ATM Queue","elementType":"queue","attributes":{},"type":"FIFO","size":-1,"shared":false},"0-5":{"id":"0-5","label":"ATM Resource","elementType":"resource","attributes":{}}},"nodes":{"0-0":{"id":"0-0","elementType":"start","attributes":{},"reportStatistics":false,"movingUnit":{"id":"0-1","label":"Customer MU","elementType":"movingunit","attributes":{},"declaration":{"id":"0-0","label":"Customer","elementType":"declaration","attributes":{}},"expression":"Exp(20","unit":"MINUTES","entitiesPerArrival":0,"maxArrival":100,"firstCreation":0}},"0-1":{"id":"0-1","label":"Teller Service","elementType":"activity","attributes":{},"reportStatistics":false,"resource":{"id":"0-3","label":"Teller Resource","elementType":"resource","attributes":{}},"queue":{"id":"0-2","label":"Teller Queue","elementType":"queue","attributes":{},"type":"FIFO","size":35,"shared":false},"processingTimeExpression":"constant(5,35)","unit":"MINUTES"},"0-2":{"id":"0-2","label":"ATM Service","elementType":"activity","attributes":{},"reportStatistics":false,"resource":{"id":"0-5","label":"ATM Resource","elementType":"resource","attributes":{}},"queue":{"id":"0-4","label":"ATM Queue","elementType":"queue","attributes":{},"type":"FIFO","size":-1,"shared":false},"processingTimeExpression":"constant(5,15)","unit":"MINUTES"},"0-3":{"id":"0-3","elementType":"end","attributes":{},"reportStatistics":false},"0-4":{"id":"0-4","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"},"0-5":{"id":"0-5","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"},"0-6":{"id":"0-6","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_JOIN"},"0-7":{"id":"0-7","elementType":"branch","attributes":{},"reportStatistics":false}},"arcs":{"0-0":{"id":"0-0","attributes":{},"source":{"id":"0-0","elementType":"start","attributes":{},"reportStatistics":false,"movingUnit":{"id":"0-1","label":"Customer MU","elementType":"movingunit","attributes":{},"declaration":{"id":"0-0","label":"Customer","elementType":"declaration","attributes":{}},"expression":"Exp(20","unit":"MINUTES","entitiesPerArrival":0,"maxArrival":100,"firstCreation":0}},"target":{"id":"0-4","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"}},"0-1":{"id":"0-1","attributes":{"condition":"<0.4"},"source":{"id":"0-4","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"},"target":{"id":"0-6","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_JOIN"}},"0-2":{"id":"0-2","attributes":{"condition":">=0.4"},"source":{"id":"0-4","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"},"target":{"id":"0-2","label":"ATM Service","elementType":"activity","attributes":{},"reportStatistics":false,"resource":{"id":"0-5","label":"ATM Resource","elementType":"resource","attributes":{}},"queue":{"id":"0-4","label":"ATM Queue","elementType":"queue","attributes":{},"type":"FIFO","size":-1,"shared":false},"processingTimeExpression":"constant(5,15)","unit":"MINUTES"}},"0-3":{"id":"0-3","attributes":{},"source":{"id":"0-2","label":"ATM Service","elementType":"activity","attributes":{},"reportStatistics":false,"resource":{"id":"0-5","label":"ATM Resource","elementType":"resource","attributes":{}},"queue":{"id":"0-4","label":"ATM Queue","elementType":"queue","attributes":{},"type":"FIFO","size":-1,"shared":false},"processingTimeExpression":"constant(5,15)","unit":"MINUTES"},"target":{"id":"0-5","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"}},"0-4":{"id":"0-4","attributes":{"condition":"<0.3"},"source":{"id":"0-5","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"},"target":{"id":"0-6","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_JOIN"}},"0-5":{"id":"0-5","attributes":{"condition":">=0.3"},"source":{"id":"0-5","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_SPLIT"},"target":{"id":"0-7","elementType":"branch","attributes":{},"reportStatistics":false}},"0-6":{"id":"0-6","attributes":{},"source":{"id":"0-6","elementType":"branch","attributes":{},"reportStatistics":false,"type":"XOR_JOIN"},"target":{"id":"0-1","label":"Teller Service","elementType":"activity","attributes":{},"reportStatistics":false,"resource":{"id":"0-3","label":"Teller Resource","elementType":"resource","attributes":{}},"queue":{"id":"0-2","label":"Teller Queue","elementType":"queue","attributes":{},"type":"FIFO","size":35,"shared":false},"processingTimeExpression":"constant(5,35)","unit":"MINUTES"}},"0-7":{"id":"0-7","attributes":{},"source":{"id":"0-1","label":"Teller Service","elementType":"activity","attributes":{},"reportStatistics":false,"resource":{"id":"0-3","label":"Teller Resource","elementType":"resource","attributes":{}},"queue":{"id":"0-2","label":"Teller Queue","elementType":"queue","attributes":{},"type":"FIFO","size":35,"shared":false},"processingTimeExpression":"constant(5,35)","unit":"MINUTES"},"target":{"id":"0-7","elementType":"branch","attributes":{},"reportStatistics":false}},"0-8":{"id":"0-8","attributes":{},"source":{"id":"0-7","elementType":"branch","attributes":{},"reportStatistics":false},"target":{"id":"0-3","elementType":"end","attributes":{},"reportStatistics":false}}}}},"configurations":{},"data":{}}';

  // Joint.js global variable
  public page!: joint.shapes.chdsr.JointGraphPageModel;
  public graph!: joint.shapes.chdsr.JointGraphModel;
  public minimap!: joint.shapes.chdsr.JointGraphModel;
  public activityPlaceholder: joint.shapes.chdsr.JointActivityModel = new joint.shapes.chdsr.JointActivityModel({
    position: new joint.g.Point(500, 300),
    attrs: {
      '.root': {
        fill: 'white',
      },
      '.label': {
        text: 'Do something',
      },
    },
  } as joint.dia.Element.Attributes);

  public mounted(): void {
    this.$nextTick(() => {
      document.title = `${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME.toUpperCase()} · Simulation Editor: Editor`;
      this.fn_application_set_title();
      this.fn_application_set_breadcrumb();
      this.fn_application_set_title_menu_bar();
      this.fn_application_set_left_side_menu_bar();
      this.fn_application_set_right_side_menu_bar();
      this.fn_application_set_ribbon_menu_item();
      this.fn_application_set_content();
      this.initJoint();
      this.initDropdown();
      this.initSlider();
      this.testGraphDataStruct();
    });
  }

  public updated(): void {
    // window.location.reload();
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

  public testGraphDataStruct(): void {
    axios.post('http://localhost:3000/model/example').then((response: AxiosResponse<any>) => {
      const graph: Graph = GraphImpl.fn_object_deserialize(response.data);
      console.log(`Successfully load CHDSR sample model! CHDSR graph version: ${graph.fn_graph_get_version()}`);
      // graph!.fn_graph_get_pages()!;
    });
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

  public fn_create_node(e: MouseEvent): void {
    const newActivity = this.activityPlaceholder.clone() as joint.shapes.chdsr.JointActivityModel;
    newActivity.addTo(this.page);
  }

  public fn_application_set_title(): void {
    // this.title = 'Wrong Title';
    this.title = `Editor`;
  }
  public fn_application_set_breadcrumb(): void {
    //
  }
  public fn_application_set_title_menu_bar(): void {
    //
  }
  public fn_application_set_left_side_menu_bar(): void {
    // TODO: define the data structure for toolbox here
  }
  public fn_application_set_right_side_menu_bar(): void {
    //
  }
  public fn_application_set_ribbon_menu_item(): void {
    //
  }
  public fn_application_set_content(): void {
    //
  }

  public initJointElements(): void {
    const start: joint.shapes.chdsr.JointStartEventModel = new joint.shapes.chdsr.JointStartEventModel();
    start.position(100, 50);
    start.attr({
      '.root': {
        stroke: '#21ba45',
        fill: 'white',
      },
      '.label': {
        text: 'Start',
      },
    });
    start.addTo(this.page);

    const rect: joint.shapes.chdsr.JointActivityModel = new joint.shapes.chdsr.JointActivityModel();
    rect.position(300, 50);
    rect.attr({
      '.root': {
        fill: 'white',
      },
      '.label': {
        text: 'Say "Hello"',
      },
    });
    rect.addTo(this.page);

    const rect2: joint.shapes.chdsr.JointActivityModel = rect.clone() as joint.shapes.chdsr.JointActivityModel;
    rect2.position(500, 50);
    rect2.attr('.label/text', 'Say "World!"');
    rect2.addTo(this.page);

    const end: joint.shapes.chdsr.JointEndEventModel = new joint.shapes.chdsr.JointEndEventModel();
    end.position(700, 50);
    end.attr({
      '.root': {
        stroke: '#db2828',
        fill: '#db2828',
      },
      '.label': {
        text: 'End',
      },
    });
    end.addTo(this.page);

    const link0: joint.dia.Link = new joint.shapes.chdsr.JointLinkModel();
    const link1: joint.dia.Link = new joint.shapes.chdsr.JointLinkModel();
    const link2: joint.dia.Link = new joint.shapes.chdsr.JointLinkModel();

    link0.source(start);
    link0.target(rect);

    link1.source(rect);
    link1.target(rect2);

    link2.source(rect2);
    link2.target(end);

    link0.addTo(this.page);
    link1.addTo(this.page);
    link2.addTo(this.page);
  }

  public initJoint(): void {
    this.page = new joint.shapes.chdsr.JointGraphPageModel();
    const canvasWidth: number = $('.editor.canvas').innerWidth();
    const canvasHeight: number = $('.editor.canvas').innerHeight();

    const graphBBox = joint.layout.DirectedGraph.layout(this.page, {
      nodeSep: 50,
      edgeSep: 80,
      rankDir: 'TB',
    });

    this.graph = new joint.shapes.chdsr.JointGraphModel({
      el: document.getElementById('canvas'),
      model: this.page,
      width: canvasWidth,
      height: canvasHeight,
      gridSize: 10,
      drawGrid: true,
      defaultAnchor: (endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) => {
        return this.customPerpendicularAnchor(endView, endMagnet, anchorReference, args);
      },
      defaultConnectionPoint: { name: 'boundary' },
      defaultConnector: {
        name: 'normal',
      },
    } as joint.dia.Paper.Options);

    this.minimap = new joint.shapes.chdsr.JointGraphModel({
      // el: document.getElementById('canvas-minimap'),
      el: document.getElementById('canvas-minimap'),
      model: this.page,
      width: canvasWidth * 20 / 100,
      height: canvasHeight * 20 / 100,
      drawGrid: false,
      interactive: false,
      defaultAnchor: (endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) => {
        return this.customPerpendicularAnchor(endView, endMagnet, anchorReference, args);
      },
      defaultConnectionPoint: { name: 'boundary' },
      defaultConnector: {
        name: 'normal',
      },
    });
    this.minimap.scale(0.33334);

    this.graph.on('blank:pointerdown', () => {
      this.fn_graph_reset(this.graph);
    });

    this.graph.on('element:pointerdown', (elementView) => {
      this.fn_graph_reset(this.graph);
      const el = elementView as joint.dia.ElementView;
      const currentElement = el.model;
      currentElement.attr('.root/stroke', 'blue');
    });

    this.graph.on('element:pointerdblclick', (elementView) => {
      const el = elementView as joint.dia.ElementView;
      const currentELement = el.model;

      const link: joint.shapes.chdsr.JointLinkModel = new joint.shapes.chdsr.JointLinkModel();
      link.source(currentELement);
      link.target(new joint.g.Point(currentELement.position().x + 100, currentELement.position().y));
      link.addTo(this.page);
    });

    window.addEventListener('resize', () => {
      this.graph.options.width = innerWidth;
      this.graph.options.height = innerHeight;
    });

    this.initJointElements();
  }

  public fn_graph_reset(paper: joint.shapes.chdsr.JointGraphModel) {
    const elements = paper.model.getElements();
    for (let i = 0; i < elements.length; i++) {
      const curr = elements[i];
      if (curr.attributes.type === 'chdsr.JointStartEventModel') {
        curr.attr('.root/stroke', '#21ba45');
      } else if (curr.attributes.type === 'chdsr.JointEndEventModel') {
        curr.attr('.root/stroke', '#db2828');
        curr.attr('.root/fill', '#db2828');
      } else {
        curr.attr('.root/stroke', 'black');
      }
    }
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
