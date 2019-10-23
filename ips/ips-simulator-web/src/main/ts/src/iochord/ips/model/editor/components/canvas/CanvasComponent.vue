<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="canvas component">
    <div class="editor canvas">

      <!-- Canvas to render graph -->
      <div id="canvas"
        @keydown.esc="handleEscapeButton($event)"
        @mousedown="handleCanvasMouseDown($event)"
        @mousemove="handleCanvasMouseMove($event)"
        @mouseup="handleCanvasMouseUp($event)"/>
    </div>

    <!-- Data Modals -->
    <ObjectTypeDataModal ref="objecttype" id="objecttype"/>
    <FunctionDataModal ref="function" id="function"/>
    <GeneratorDataModal ref="generator" id="generator"/>
    <QueueDataModal ref="queue" id="queue"/>
    <ResourceDataModal ref="resource" id="resource"/>
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
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
.canvas.component {
  height: calc(100% - 60px);
}
.canvas.component .editor.canvas {
  width: 100%;
  height: 100%;
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
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphControlImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/ips/common/graph/ism/class/GraphImpl';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphPageImpl } from '@/iochord/ips/common/graph/ism/class/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import JointJsRenderer from '@/iochord/ips/common/graph/ism/rendering-engine/joint/class/JointJsRenderer';

// Interfaces
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

// Vuex module instance
const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
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

  // Pan and zoom
  public canvasPanAndZoom?: SvgPanZoom.Instance;
  public minimapPanAndZoom?: SvgPanZoom.Instance;

  public mounted(): void {
    this.loadGraph();
    this.$forceUpdate();
  }

  public loadGraph(): void {
    try {

      // Deserialize the model
      this.graph = this.response as Graph;

      // TODO: we can choose any rendering engine later

      // TODO: Extend the capability of the renderer.
      //       For example, we dont have to re-render the graph
      //       every time user apply changes to the graph.
      const renderer = new JointJsRenderer(
        this.graph,
        this.activePage as GraphPage,
        this.currentSelectedElement as GraphNode,
      );

      // Get canvasPanAndZoom instance from renderer
      this.canvasPanAndZoom = renderer.canvasPanAndZoom;

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

        // Unhighlight the element from the elementView
        elementView.unhighlight();
      });
    };

    // Helper to remove node
    const removeNode = (currentElement: joint.dia.Element) => {
      return (e: KeyboardEvent) => {

        // Get connected links of current selected nodes (inbound and outbound links)
        const links = jointPage.getGraph().getConnectedLinks(currentElement);

        // If delete key was pressed
        if (e.keyCode === 46) {

          // Remove node
          jointPage.getNodes()!.delete(currentElement.attributes.nodeId);
          currentElement.remove();

          // Remove links that connected to the node as well
          links.forEach((link) => {
            jointPage.getArcs()!.delete(link.attributes.arcId);
            link.remove();
          });

          // Remove event listener from the deleted nodes
          currentElement.off('keydown', removeNode(currentElement), false);
        }
      };
    };

    // Listening to events (Refer to joint.js API docs)
    // (TODO: later each of these event handler must be encapsulated within methods or classes)
    jointPage.getPaper().on({
      'blank:pointerdown': (elementView: joint.dia.ElementView) => {

        // Reset page
        resetAll(jointPage.getPaper());

        // If currently drawing arc
        if (editorState.drawing && this.canvasPanAndZoom) {

          // Disable canvasPanAndZoom
          this.canvasPanAndZoom.disablePan();
          this.canvasPanAndZoom.disableZoom();

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
        } else if (!editorState.drawing && this.canvasPanAndZoom) {

          // Otherwise keep canvasPanAndZoom enabled
          this.canvasPanAndZoom.enablePan();
          this.canvasPanAndZoom.enableZoom();

          // And change its cursor to grabbing (pan mode)
          document.body.style.cursor = 'grabbing';
        }
      },
      'element:pointerup blank:pointerup': (elementView: joint.dia.ElementView) => {

        // Reset page
        resetAll(jointPage.getPaper());

        // Disable pan
        (this.canvasPanAndZoom as SvgPanZoom.Instance).disablePan();

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
        if (editorState.drawing) {

          // User cannot drag anything
          jointPage.getPaper().setInteractivity(false);

          // If source node is not set
          if (!this.source) {

            // Set source node
            this.setSourceNode(this.activePage as JointGraphPageImpl, currentElement);
          } else if (this.source && !this.target) {

            // Otherwise set it as target node
            this.setTargetNode(this.activePage as JointGraphPageImpl, currentElement);
          }
        } else {

          // Otherwise set paper interactivity to true (user can drag everything)
          jointPage.getPaper().setInteractivity(true);
        }
      },
      'element:pointerclick': (elementView: joint.dia.ElementView) => {

        // Reset page
        resetAll(jointPage.getPaper());

        // Highlight current element
        const currentElement = elementView.model;
        currentElement.findView(jointPage.getPaper()).highlight();
      },
      'element:pointerdblclick': (elementView: joint.dia.ElementView) => {

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
          property = graphModule.pageNode(jointPage, currentElementId);
        } else if (currentElementCategory === 'data') {
          currentElementId = currentElement.attributes.dataId.split('-')[2];
          property = graphModule.pageDatum(jointPage, currentElementId);
        }

        // Populate modal with element properties
        (this.$refs[currentElementType] as Modal<JointGraphPageImpl, typeof property>).populateProperties(jointPage, property);

        // Show modal
        $(`#${currentElementType}`).modal('setting', 'transition', 'fade up').modal('show');

        // Highlight current element
        currentElement.findView(jointPage.getPaper()).highlight();
      },
      'cell:highlight': (elementView: joint.dia.ElementView) => {

        // Get current element
        const currentElement = elementView.model;

        // If delete key was pressed, remove node
        currentElement.on('keydown', removeNode(currentElement), false);
      },
      'cell:unhighlight': (elementView: joint.dia.ElementView) => {

        // Get current element
        const currentElement = elementView.model;

        // When cell is unhighlighted, remove the event listener for removing nodes
        currentElement.off('keydown', removeNode(currentElement), false);
      },
    });
  }
}
</script>
