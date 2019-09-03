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
        @mouseup="handleCanvasMouseUp($event)" />
    </div>

    <!-- Node Modals -->
    <template v-for="type in Array.from(nodeTypes)">

      <!-- If start node was clicked -->
      <template v-if="type === 'start'">

        <!-- Show start modal -->
        <StartNodeModal label="test"
          @changeStartLabel="changeStartLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeStartGenerator="changeStartGeneratorFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :startLabel.sync="parentStartLabel"
          :startGenerator.sync="parentStartGenerator"
          v-bind:id="type" v-bind:key="type" />
      </template>

      <!-- If branch node was clicked -->
      <template v-if="type === 'branch'">

        <!-- Show branch modal -->
        <BranchNodeModal label="this is branch"
          @changeBranchLabel="changeBranchLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeBranchSelectedGate="changeBranchSelectedGateFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeBranchSelectedType="changeBranchSelectedTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeBranchSelectedRule="changeBranchSelectedRuleFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :branchLabel.sync="parentBranchLabel"
          :selectedGate.sync="parentBranchSelectedGate"
          :selectedType.sync="parentBranchSelectedType"
          :selectedRule.sync="parentBranchSelectedRule"
          v-bind:id="type" v-bind:key="type" />
      </template>

      <!-- If activity node was clicked -->
      <template v-if="type === 'activity'">

        <!-- Show activity modal -->
        <ActivityNodeModal
          @changeActNodeSelectedActivityType="changeActNodeSelectedActivityTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeReport="changeActNodeReportFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeCustomMonitor="changeActNodeCustomMonitorFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeProcessingTime="changeActNodeProcessingTimeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeParameter1="changeActNodeParameter1FromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeSetupTime="changeActNodeSetupTimeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeParameter2="changeActNodeParameter2FromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeUnit="changeActNodeUnitFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeQueueLabel="changeActNodeQueueLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeInputType="changeActNodeInputTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeOutputType="changeActNodeOutputTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeCodeSegment="changeActNodeCodeSegmentFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :actNodeSelectedActivityType="parentActNodeSelectedActivityType"
          :actNodeReport="parentActNodeReport"
          :actNodeCustomMonitor="parentActNodeCustomMonitor"
          :actNodeProcessingTime="parentActNodeProcessingTime"
          :actNodeParameter1="parentActNodeParameter1"
          :actNodeSetupTime="parentActNodeSetupTime"
          :actNodeParameter2="parentActNodeParameter2"
          :actNodeUnit="parentActNodeUnit"
          :actNodeQueueLabel="parentActNodeQueueLabel"
          :actNodeInputType="parentActNodeInputType"
          :actNodeOutputType="parentActNodeOutputType"
          :actNodeCodeSegment="parentActNodeCodeSegment"
          v-bind:id="type" v-bind:key="type"/>
      </template>

      <!-- If stop node was clicked -->
      <template v-if="type === 'stop'">

        <!-- Show stop modal -->
        <StopNodeModal
          @changeStopLabel="changeStopLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeStopReport="changeStopReportFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :stopLabel="parentStopLabel"
          :stopReport="parentStopReport"
          v-bind:id="type" v-bind:key="type" />
      </template>
    </template>
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
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphControlImpl } from '@/iochord/ips/common/graph/ism/classes/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/ips/common/graph/ism/classes/GraphImpl';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphPageImpl } from '@/iochord/ips/common/graph/ism/classes/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/classes/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/classes/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/classes/JointGraphPageImpl';
import JointJsRenderer from '@/iochord/ips/common/graph/ism/rendering-engine/joint/classes/JointJsRenderer';

// Interfaces
import { GraphConnector } from '@/iochord/ips/common/graph/ism/interfaces/GraphConnector';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphElement } from '@/iochord/ips/common/graph/ism/interfaces/GraphElement';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { GraphStartEventNode } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphStartEventNode';

// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/NODE';
import { ARC_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/ARC';

// Components
import StartNodeModal from '@/iochord/ips/simulation/editor/components/modals/StartNodeModal.vue';
import ActivityNodeModal from '@/iochord/ips/simulation/editor/components/modals/ActivityNodeModal.vue';
import BranchNodeModal from '@/iochord/ips/simulation/editor/components/modals/BranchNodeModal.vue';
import StopNodeModal from '@/iochord/ips/simulation/editor/components/modals/StopNodeModal.vue';

// Mixins
import ModalMixin from '@/iochord/ips/simulation/editor/mixins/modals/ModalMixin';
import CanvasMixin from '@/iochord/ips/simulation/editor/mixins/editors/CanvasMixin';

// JQuery Handler
declare const $: any;

// Vuex Module
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import EditorState from '../../stores/editors/EditorState';

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
  },
})
export default class CanvasComponent extends Mixins(BaseComponent, ModalMixin, CanvasMixin) {
  @Prop() public response?: Graph;

  public panAndZoom?: SvgPanZoom.Instance;

  public mounted(): void {
    this.loadGraph();
    this.$forceUpdate();
  }

  public loadGraph(): void {
    try {

      // Deserialize the model
      this.graph = this.response as Graph;

      // TODO: we can choose any rendering engine later

      // TODO:
      // Extend the capability of the renderer.
      // For example, we dont have to re-render the graph
      // every time user apply changes to the graph.
      const renderer = new JointJsRenderer(
        this.graph,
        this.activePage as GraphPage,
        this.currentSelectedElement as GraphNode,
      );

      // Get node types that need to be rendered in the canvas
      this.nodeTypes = renderer.getNodeTypes();

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
      console.error(e);
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
        if (editorState.drawing && this.panAndZoom) {

          // Disable panAndZoom
          this.panAndZoom.disablePan();
          this.panAndZoom.disableZoom();

          // Pop error toast to the screen
          $('body').toast({
            position: 'bottom right',
            class: 'error',
            className: {
              toast: 'ui message',
            },
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

        // Reset page
        resetAll(jointPage.getPaper());

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
        const currentElementType = currentElement.attributes.type;
        const currentElementNodeId = currentElement.attributes.nodeId;

        // Highlight current element
        currentElement.findView(jointPage.getPaper()).highlight();

        // If current clicked element is a start node
        if (currentElementType === 'start') {

          // Show start modal
          $('#start').modal('show');

          // Populate node properties to the modal
          this.parentStartLabel = jointPage.getNodes()!.get(currentElementNodeId)!.getLabel() as string;
          this.parentStartGenerator =
            (jointPage.getNodes()!.get(currentElementNodeId)! as GraphStartEventNode)
              .getGenerator()!.getLabel() as string +
              ' - ' +
            (jointPage.getNodes()!.get(currentElementNodeId)! as GraphStartEventNode)
              .getGenerator()!.getDistributionType() as string;

          // Set current clicked node as current selected element
          this.currentSelectedElement = jointPage.getNodes()!.get(currentElementNodeId);
        }

        // If current clicked element is an activity node
        if (currentElementType === 'activity') {

          // Show activity modal
          $('#activity').modal('show');
        }

        // If current clicked element is a branch node
        if (currentElementType === 'branch') {

          // Show branch modal
          ($('#branch') as any).modal('show');
        }

        // If current clicked element is a stop node
        if (currentElementType === 'stop') {

          // Show stop modal
          $('#stop').modal('show');

          // Populate node properties to the modal
          this.parentStopLabel = jointPage.getNodes()!.get(currentElementNodeId)!.getLabel() as string;
          this.parentStopReport = jointPage.getNodes()!.get(currentElementNodeId)!.isReportStatistics() as boolean;

          // Set current clicked node as current selected element
          this.currentSelectedElement = jointPage.getNodes()!.get(currentElementNodeId);
        }
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
