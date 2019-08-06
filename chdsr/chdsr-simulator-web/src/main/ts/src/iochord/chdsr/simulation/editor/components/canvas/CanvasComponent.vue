<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="canvas component">
    <div class="editor canvas">
      <div id="canvas"></div>
    </div>

    <!-- Model Modals -->
    <template v-for="type in Array.from(nodeTypes)">
      <template v-if="type === 'start'">
        <StartNodeModal label="test"
          @changeStartLabel = "changeStartLabelFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeStartGenerator = "changeStartGeneratorFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          :startLabel.sync="parentStartLabel"
          :startGenerator.sync="parentStartGenerator"
          v-bind:id="type" v-bind:key="type"
        />
      </template>

      <template v-if="type === 'branch'">
        <BranchNodeModal label="this is branch"
          @changeBranchLabel = "changeBranchLabelFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeBranchSelectedGate = "changeBranchSelectedGateFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeBranchSelectedType = "changeBranchSelectedTypeFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeBranchSelectedRule = "changeBranchSelectedRuleFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          :branchLabel.sync = "parentBranchLabel"
          :selectedGate.sync = "parentBranchSelectedGate"
          :selectedType.sync = "parentBranchSelectedType"
          :selectedRule.sync = "parentBranchSelectedRule"
          v-bind:id="type" v-bind:key="type"
        />
      </template>

      <template v-if="type === 'activity'">
        <ActivityNodeModal
          @changeActNodeSelectedActivityType = "changeActNodeSelectedActivityTypeFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeReport = "changeActNodeReportFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeCustomMonitor = "changeActNodeCustomMonitorFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeProcessingTime = "changeActNodeProcessingTimeFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeParameter1 = "changeActNodeParameter1FromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeSetupTime = "changeActNodeSetupTimeFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeParameter2 = "changeActNodeParameter2FromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeUnit = "changeActNodeUnitFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeQueueLabel = "changeActNodeQueueLabelFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeInputType = "changeActNodeInputTypeFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeOutputType = "changeActNodeOutputTypeFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
          @changeActNodeCodeSegment = "changeActNodeCodeSegmentFromChild($event, graph, activePage, currentSelectedElement, loadGraph())"
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
          @changeStopLabel = "changeStopLabelFromChild($event)"
          @changeStopReport = "changeStopReportFromChild($event)"
          :stopLabel = "parentStopLabel"
          :stopReport = "parentStopReport"
          v-bind:id="type" v-bind:key="type"/>
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
  height: 100%;
}
.canvas.component .editor.canvas {
  width: 100%;
  height: 100%;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Prop, Mixins, Vue } from 'vue-property-decorator';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

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
import ActivityNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/ActivityNodeModalMixin';
import BranchNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/BranchNodeModalMixin';
import StartNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/StartNodeModalMixin';
import StopNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/StopNodeModalMixin';

// JQuery Handler
declare const $: any;


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
  // mixins: [ActivityNodeModalMixin, BranchNodeModalMixin, StartNodeModalMixin, StopNodeModalMixin],
})
export default class CanvasComponent extends Mixins(BaseComponent, ActivityNodeModalMixin, BranchNodeModalMixin, StartNodeModalMixin, StopNodeModalMixin) {
  @Prop() public response?: Graph;

  // Find all node types in the graph
  public nodeTypes: Set<string> = new Set<string>();

  // Graph data
  public graph?: Graph;

  public currentSelectedElement?: GraphNode;
  public activePage?: GraphPage;

  public mounted(): void {
    this.loadGraph();
    this.activePage = this.graph!.getPages()!.get('0');
    this.$forceUpdate();
  }

  public loadGraph(): void {
    try {
      // Deserialize the model
      this.graph = this.response as Graph;

      // we can choose any rendering engine later
      const renderer = new JointJsRenderer(
        this.graph,
        this.activePage as GraphPage,
        this.currentSelectedElement as GraphNode,
      );

      this.nodeTypes = renderer.getNodeTypes();

    } catch (e) {
      console.log(e);
    }
  }
}
</script>
