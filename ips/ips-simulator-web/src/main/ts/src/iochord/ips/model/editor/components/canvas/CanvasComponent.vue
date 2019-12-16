<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="canvas component">
    <div class="editor canvas ui basic segment">

      <!-- Canvas to render graph -->
      <div id="canvas"
        @keydown.esc="handleEscapeButton($event)"
        @mousedown="handleCanvasMouseDown($event)"
        @mousemove="handleCanvasMouseMove($event)"
        @mouseup="handleCanvasMouseUp($event)"/>
      </div>

    <!-- Data Modals -->

    <DataTableModal ref="datatable" id ="datatable" />
    <ObjectTypeDataModal ref="objecttype" id="objecttype"/>
    <FunctionDataModal ref="function" id="function"/>
    <GeneratorDataModal ref="generator" id="generator"/>
    <QueueDataModal ref="queue" id="queue"/>
    <ResourceDataModal ref="resource" id="resource"/>

    <!-- Node Modals -->
    <StartNodeModal ref="start" id="start"/>
    <BranchNodeModal ref="branch" id="branch"/>
    <ActivityNodeModal ref="activity" id="activity"/>
    <StopNodeModal ref="stop" id="stop"/>

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
.canvas.component {
  height: calc(100% - 60px);
}
.canvas.component .editor.canvas {
  width: 100%;
  height: 100%;

  padding: 0;
  margin: 0;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Prop, Mixins, Vue } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// SVG Pan and Zoom
import SvgPanZoom from 'svg-pan-zoom';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphControlImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/ips/common/graph/ism/class/GraphImpl';
import { GraphPageImpl } from '@/iochord/ips/common/graph/ism/class/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import JointJsRenderer from '@/iochord/ips/common/graph/ism/rendering-engine/joint/class/JointJsRenderer';

// Interfaces
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphConnector } from '@/iochord/ips/common/graph/ism/interfaces/GraphConnector';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphElement } from '@/iochord/ips/common/graph/ism/interfaces/GraphElement';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { GraphStartEventNode } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphStartEventNode';
import { Modal } from '@/iochord/ips/model/editor/interfaces/Modal';

// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/NODE';
import { ARC_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/ARC';
import { MODAL_TYPE } from '../../enums/MODAL';

// Components
import StartNodeModal from '@/iochord/ips/model/editor/components/modals/StartNodeModal.vue';
import ActivityNodeModal from '@/iochord/ips/model/editor/components/modals/ActivityNodeModal.vue';
import BranchNodeModal from '@/iochord/ips/model/editor/components/modals/BranchNodeModal.vue';
import StopNodeModal from '@/iochord/ips/model/editor/components/modals/StopNodeModal.vue';
import DataTableModal from '@/iochord/ips/model/editor/components/modals/DataTableModal.vue';
import ObjectTypeDataModal from '@/iochord/ips/model/editor/components/modals/ObjectTypeDataModal.vue';
import ConfigurationModal from '@/iochord/ips/model/editor/components/modals/ConfigurationModal.vue';
import ControlModal from '@/iochord/ips/model/editor/components/modals/ControlModal.vue';
import FunctionDataModal from '@/iochord/ips/model/editor/components/modals/FunctionDataModal.vue';
import GeneratorDataModal from '@/iochord/ips/model/editor/components/modals/GeneratorDataModal.vue';
import QueueDataModal from '@/iochord/ips/model/editor/components/modals/QueueDataModal.vue';
import ResourceDataModal from '@/iochord/ips/model/editor/components/modals/ResourceDataModal.vue';

// Mixins
import CanvasMixin from '../../mixins/editors/CanvasMixin';

// JQuery Handler
declare const $: any;

// Vuex Module
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import EditorState from '../../stores/editors/EditorState';
import { GraphActivityNode } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphActivityNode';
import { ACTIVITY_TYPE } from '@/iochord/ips/common/graph/ism/enums/ACTIVITY';
import { GraphDataResource } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphDataResource';
import { DISTRIBUTION_TYPE } from '@/iochord/ips/common/graph/ism/enums/DISTRIBUTION';
import { TIME_UNIT } from '@/iochord/ips/common/graph/ism/enums/TIME_UNIT';
import { GraphDataQueue } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphDataQueue';
import { GraphBranchNode } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphBranchNode';
import { BRANCH_GATE, BRANCH_TYPE, BRANCH_RULE } from '@/iochord/ips/common/graph/ism/enums/BRANCH';
import { GraphConnectorImpl } from '../../../../common/graph/ism/class/GraphConnectorImpl';
import { TSMap } from 'typescript-map';

// Vuex module instance
const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    ActivityNodeModal,
    BranchNodeModal,
    CanvasComponent,
    StartNodeModal,
    StopNodeModal,
    DataTableModal,
    ObjectTypeDataModal,
    ConfigurationModal,
    ControlModal,
    FunctionDataModal,
    GeneratorDataModal,
    QueueDataModal,
    ResourceDataModal,
  },
})
export default class CanvasComponent extends Mixins(BaseComponent, CanvasMixin) {
  @Prop() public response?: Graph;
  @Prop() public isProcessModel?: boolean;
  @Prop({ default: false }) public isDisabled?: boolean;

  public panAndZoom?: SvgPanZoom.Instance;
  public highlightedElement: TSMap<string, joint.dia.Element> = new TSMap<string, joint.dia.Element>();
  public isShiftKey: boolean = false;

  public mounted(): void {
    this.loadGraph();
    this.$forceUpdate();
  }

  public loadGraph(): void {
    try {

      // Deserialize the model
      this.graph = this.response as Graph;

      // TODO: we can choose any rendering engine later
      const renderer = new JointJsRenderer(
        this.graph,
        this.activePage as GraphPage,
        this.currentSelectedElement as GraphNode,
        this.isProcessModel as boolean,
      );

      // Get panAndZoom instance from renderer
      this.panAndZoom = renderer.panAndZoom;

      // 'Listening to events' can only be done after all components were rendered
      renderer.jointPages.forEach((jointPage: JointGraphPageImpl) => {
        this.whileListenToEvents(jointPage);
      });

      // Set active page to the first page of the graph
      if (this.graph) {
        this.activePage = this.graph.getPages()!.get('0');
      }

      // Assign Joint.js page as an active page
      if (this.activePage) {
        this.activePage = renderer.activeJointPage(this.activePage.getId() as string) as JointGraphPageImpl;
      }
    } catch (e) {
      // console.error(e);
    }
  }

  private whileListenToEvents(jointPage: JointGraphPageImpl): void {

    // Helper to reset all elements
    const resetAll = (paper: joint.dia.Paper) => {

      // Get all elements within graph
      jointPage.getGraph().getElements().forEach((element: joint.dia.Element) => {

        // For each element, find its elementView
        const elementView = paper.findViewByModel(element);

        if (!this.isShiftKey) {
          elementView.unhighlight();
        }
      });
    };

    document.addEventListener('keydown', (e) => {
      switch (e.which) {
        // Backspace & Delete
        case 8: case 46:
          
          this.highlightedElement.forEach((element) => removeNode(element)); 
          break;
        // Shift
        case 16: this.isShiftKey = e.shiftKey; break;
      }
    });

    document.addEventListener('keyup', (e) => {
      this.isShiftKey = false;
    });

    // Helper to remove node
    const removeNode = (currentElement: joint.dia.Element) => {
      // Get connected links of current selected nodes (inbound and outbound links)
      const links = jointPage.getGraph().getConnectedLinks(currentElement);

      // Remove node
      jointPage.getNodes()!.delete(currentElement.attributes.nodeId);
      currentElement.remove();

      // Remove links that connected to the node as well
      links.forEach((link) => {
        (jointPage.getConnectors() as TSMap<string, GraphConnector>).delete(link.attributes.arcId);
        link.remove();
      });
    };

    // Listening to events (Refer to joint.js API docs)
    // (TODO: later each of these event handler must be encapsulated within methods or classes)
    jointPage.getGraph().on({
      remove: (cell: joint.dia.Element) => {
        this.deleteConnector(this.activePage as JointGraphPageImpl, cell);
        this.deleteNode(this.activePage as JointGraphPageImpl, cell);
        this.deleteData(this.activePage as JointGraphPageImpl, cell);

        this.source = undefined;
        this.target = undefined;
      },
    });

    jointPage.getPaper().on({
      'blank:pointerdown': (elementView: joint.dia.ElementView) => {

        // Reset page
        resetAll(jointPage.getPaper());

        // If currently drawing arc
        if (editorState.drawing && this.panAndZoom) {

          // Disable panAndZoom
          this.panAndZoom.disablePan();
          this.panAndZoom.disableZoom();

          // Pop error toast to the screen
          $('body').toast({
            position: 'bottom right',
            class: 'error',
            className: { toast: 'ui message' },
            message: 'Select any node to draw connector.',
            newestOnTop: true,
          });

          // Change crosshair
          document.body.style.cursor = 'crosshair';
        } else if (!editorState.drawing && this.panAndZoom) {

          // Otherwise keep panAndZoom enabled
          this.panAndZoom.enablePan();
          this.panAndZoom.enableZoom();

          // And change its cursor to grabbing (pan mode)
          document.body.style.cursor = 'grabbing';
        }
      },
      'element:pointerup blank:pointerup': (elementView: joint.dia.ElementView) => {

        // Disable pan
        (this.panAndZoom as SvgPanZoom.Instance).disablePan();

        // If currently drawing arc
        if (editorState.drawing) {

          // Set the cursor to crosshair
          document.body.style.cursor = 'crosshair';
        } else {

          // Otherwise to default cursor
          document.body.style.cursor = 'default';
        }
      },
      'element:mouseover': (elementView: joint.dia.ElementView) => {

        // If currently drawing arc
        if (editorState.drawing) {

          // Reset page
          resetAll(jointPage.getPaper());

          // Highlight current element
          const currentElement = elementView.model;
          currentElement.findView(jointPage.getPaper()).highlight();
        }
      },
      'element:mouseout': (elementView: joint.dia.ElementView) => {

        // If currently drawing arc
        if (editorState.drawing) {

          // Reset page
          resetAll(jointPage.getPaper());

          // Set cursor to crosshair
          document.body.style.cursor = 'crosshair';
        } else {

          // Otherwise set cursor to default
          document.body.style.cursor = 'default';
        }
      },
      'element:pointerdown': (elementView: joint.dia.ElementView) => {

        // Reset page
        resetAll(jointPage.getPaper());

        // Highlight current element
        const currentElement = elementView.model;
        currentElement.findView(jointPage.getPaper()).highlight();

        // When drawing a connector
        while (editorState.drawing) {

          // User cannot drag anything
          jointPage.getPaper().setInteractivity(false);

          // If source node is not set
          if (!this.source) {

            // Set source node
            this.setSourceNode(this.activePage as JointGraphPageImpl, currentElement);
            break;
          }

          if (this.source && !this.target) {

            // Otherwise set it as target node
            this.setTargetNode(this.activePage as JointGraphPageImpl, currentElement);
            break;
          }

        }

        // Otherwise set paper interactivity to true (user can drag everything)
        jointPage.getPaper().setInteractivity(true);
      },
      'element:pointerclick': (elementView: joint.dia.ElementView) => {

        // Reset page
        resetAll(jointPage.getPaper());

        // Highlight current element
        const currentElement = elementView.model;
        currentElement.findView(jointPage.getPaper()).highlight();
      },
      'element:pointerdblclick': (elementView: joint.dia.ElementView) => {

        if (this.isDisabled) {
          return;
        }

        // Reset page
        resetAll(jointPage.getPaper());

        // Get current element and its properties
        const currentElement = elementView.model;
        const currentElementType: string = currentElement.attributes.type;
        const currentElementCategory: string = currentElement.attributes.category;

        // Get node property
        let property;
        let currentElementId: string;

        // Get element properties
        if (currentElementCategory === 'node') {
          currentElementId = currentElement.attributes.nodeId.split('-')[2];
          property = graphModule.pageNode(jointPage, currentElement.attributes.nodeId);
        } else if (currentElementCategory === 'data') {
          currentElementId = currentElement.attributes.dataId.split('-')[2];
          property = graphModule.pageDatum(jointPage, currentElement.attributes.dataId);
        }

        if (!this.isProcessModel) {
          // Populate modal with element properties
          (this.$refs[currentElementType] as Modal<JointGraphPageImpl, typeof property>).populateProperties(jointPage, property);

          // Show modal
          $(`#${currentElementType}`).modal('setting', 'transition', 'fade up').modal('show');
        }

        // Highlight current element
        // currentElement.findView(jointPage.getPaper()).highlight();
      },
      'cell:highlight': (elementView: joint.dia.ElementView) => {

        // Get current element
        const currentElement = elementView.model;
        this.highlightedElement.set(currentElement.id as string, currentElement);
      },
      'cell:unhighlight': (elementView: joint.dia.ElementView) => {

        // Get current element
        const currentElement = elementView.model;
        this.highlightedElement.delete(currentElement.id as string);
      },
    });
  }
}
</script>
