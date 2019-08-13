<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="canvas component">
    <div class="editor canvas">

      <!-- TODO: Develop mouse event handler for canvas -->
      <div id="canvas"
        @keydown.esc="cancelCreateItem($event)"
        @mousedown="handleCanvasMouseDown($event)"
        @mousemove="handleCanvasMouseMove($event)"
        @mouseup="handleCanvasMouseUp($event)" />
    </div>

    <!-- Model Modals -->
    <template v-for="type in Array.from(nodeTypes)">
      <template v-if="type === 'start'">
        <StartNodeModal label="test"
          @changeStartLabel = "changeStartLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeStartGenerator = "changeStartGeneratorFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :startLabel.sync="parentStartLabel"
          :startGenerator.sync="parentStartGenerator"
          v-bind:id="type" v-bind:key="type" />
      </template>

      <template v-if="type === 'branch'">
        <BranchNodeModal label="this is branch"
          @changeBranchLabel = "changeBranchLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeBranchSelectedGate = "changeBranchSelectedGateFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeBranchSelectedType = "changeBranchSelectedTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeBranchSelectedRule = "changeBranchSelectedRuleFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :branchLabel.sync = "parentBranchLabel"
          :selectedGate.sync = "parentBranchSelectedGate"
          :selectedType.sync = "parentBranchSelectedType"
          :selectedRule.sync = "parentBranchSelectedRule"
          v-bind:id="type" v-bind:key="type" />
      </template>

      <template v-if="type === 'activity'">
        <ActivityNodeModal
          @changeActNodeSelectedActivityType = "changeActNodeSelectedActivityTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeReport = "changeActNodeReportFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeCustomMonitor = "changeActNodeCustomMonitorFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeProcessingTime = "changeActNodeProcessingTimeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeParameter1 = "changeActNodeParameter1FromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeSetupTime = "changeActNodeSetupTimeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeParameter2 = "changeActNodeParameter2FromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeUnit = "changeActNodeUnitFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeQueueLabel = "changeActNodeQueueLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeInputType = "changeActNodeInputTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeOutputType = "changeActNodeOutputTypeFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeActNodeCodeSegment = "changeActNodeCodeSegmentFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :actNodeSelectedActivityType = "parentActNodeSelectedActivityType"
          :actNodeReport = "parentActNodeReport"
          :actNodeCustomMonitor = "parentActNodeCustomMonitor"
          :actNodeProcessingTime = "parentActNodeProcessingTime"
          :actNodeParameter1 = "parentActNodeParameter1"
          :actNodeSetupTime = "parentActNodeSetupTime"
          :actNodeParameter2 = "parentActNodeParameter2"
          :actNodeUnit = "parentActNodeUnit"
          :actNodeQueueLabel = "parentActNodeQueueLabel"
          :actNodeInputType = "parentActNodeInputType"
          :actNodeOutputType = "parentActNodeOutputType"
          :actNodeCodeSegment = "parentActNodeCodeSegment"
          v-bind:id="type" v-bind:key="type"/>
      </template>

      <template v-if="type === 'stop'">
        <StopNodeModal
          @changeStopLabel = "changeStopLabelFromChild($event, activePage, currentSelectedElement, loadGraph)"
          @changeStopReport = "changeStopReportFromChild($event, activePage, currentSelectedElement, loadGraph)"
          :stopLabel = "parentStopLabel"
          :stopReport = "parentStopReport"
          v-bind:id="type" v-bind:key="type" />
      </template>
    </template>
  </div>
</template>

<style scoped>
/**
 *
 * @package chdsr
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
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphControlImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphImpl';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphPageImpl';
import JointJsRenderer from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/classes/JointJsRenderer';

// Interfaces
import { GraphConnector } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphConnector';
import { GraphData } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphData';
import { GraphElement } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphElement';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';
import { GraphStartEventNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/components/GraphStartEventNode';

// Enums
import { NODE_TYPE } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/enums/NODE';
import { ARC_TYPE } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/enums/ARC';

// Components
import StartNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/StartNodeModal.vue';
import ActivityNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/ActivityNodeModal.vue';
import BranchNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/BranchNodeModal.vue';
import StopNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/StopNodeModal.vue';

// Mixins
import ModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/ModalMixin';
import CanvasMixin from '@/iochord/chdsr/simulation/editor/mixins/editors/CanvasMixin';


// JQuery Handler
declare const $: any;

// Vuex Module
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
const graphModule = getModule(GraphModule);

/**
 *
 * @package chdsr
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
    // this.adjustCanvasHeight();
    this.loadGraph();
    this.$forceUpdate();
  }

  public adjustCanvasHeight(): void {
    let canvasComponentHeight = ($('.canvas.component') as JQuery<HTMLElement>).height() as number;
    canvasComponentHeight = canvasComponentHeight - 60;
    $('.canvas.component').height(canvasComponentHeight);
  }

  public loadGraph(): void {
    try {
      // Deserialize the model
      this.graph = this.response as Graph;

      // TODO: we can choose any rendering engine later

      // TODO:
      // Extend the capability of the renderer.
      // For example, we dont have to rerender the graph
      // every time user apply changes to the graph.
      const renderer = new JointJsRenderer(
        this.graph,
        this.activePage as GraphPage,
        this.currentSelectedElement as GraphNode,
      );

      // Get node types that need to be rendered in the canvas
      this.nodeTypes = renderer.getNodeTypes();

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
      paper.findViewsInArea(
        jointPage.getPaper().getArea()).forEach((cell: joint.dia.ElementView) => {
          cell.unhighlight();
        },
      );
    };

    // Listening to events (TODO: later each of these event handler must be encapsulated within methods or classes)
    jointPage.getPaper().on({
      'blank:pointerdown': (elementView: joint.dia.ElementView) => {
        resetAll(jointPage.getPaper());
        (this.panAndZoom as SvgPanZoom.Instance).enablePan();
      },
      'element:pointerup blank:pointerup': (elementView: joint.dia.ElementView) => {
        (this.panAndZoom as SvgPanZoom.Instance).disablePan();
      },
      'element:pointerdown': (elementView: joint.dia.ElementView) => {
        resetAll(jointPage.getPaper());
        const currentElement = elementView.model;
        currentElement.findView(jointPage.getPaper()).highlight();
      },
      'element:pointerclick': (elementView: joint.dia.ElementView) => {
        resetAll(jointPage.getPaper());
        const currentElement = elementView.model;
        currentElement.findView(jointPage.getPaper()).highlight();
      },
      'element:pointerdblclick': (elementView: joint.dia.ElementView) => {
        resetAll(jointPage.getPaper());
        const currentElement = elementView.model;
        const currentElementType = currentElement.attributes.type;
        const currentElementNodeId = currentElement.attributes.nodeId;

        currentElement.findView(jointPage.getPaper()).highlight();

        if (currentElementType === 'start') {
          $('#start').modal('show');
          this.parentStartLabel = jointPage.getNodes()!.get(currentElementNodeId)!.getLabel() as string;
          this.parentStartGenerator =
            (jointPage.getNodes()!.get(currentElementNodeId)! as GraphStartEventNode)
              .getGenerator()!.getLabel() as string +
              ' - ' +
            (jointPage.getNodes()!.get(currentElementNodeId)! as GraphStartEventNode)
              .getGenerator()!.getDistributionType() as string;
          this.currentSelectedElement = jointPage.getNodes()!.get(currentElementNodeId);
        }

        if (currentElementType === 'activity') {
          $('#activity').modal('show');
        }

        if (currentElementType === 'branch') {
          ($('#branch') as any).modal('show');
        }

        if (currentElementType === 'stop') {
          $('#stop').modal('show');
          this.parentStopLabel = jointPage.getNodes()!.get(currentElementNodeId)!.getLabel() as string;
          this.parentStopReport = jointPage.getNodes()!.get(currentElementNodeId)!.isReportStatistics() as boolean;
          this.currentSelectedElement = jointPage.getNodes()!.get(currentElementNodeId);
        }
      },
      'cell:highlight': (elementView: joint.dia.ElementView) => {
        const currentElement = elementView.model;
        const links = jointPage.getGraph().getConnectedLinks(currentElement);

        window.addEventListener('keydown', (e: KeyboardEvent) => {
          if (e.keyCode === 46) {

            // Remove node
            jointPage.getNodes()!.delete(currentElement.attributes.nodeId);
            currentElement.remove();

            // Remove link
            links.forEach((link) => {
              jointPage.getArcs()!.delete(link.attributes.arcId);
              link.remove();
            });
          }
        });
      },
    });
  }
}
</script>
