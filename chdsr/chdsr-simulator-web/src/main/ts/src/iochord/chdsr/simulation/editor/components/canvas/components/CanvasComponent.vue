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
          @changeStartLabel = "changeStartLabelFromChild($event)"
          @changeStartGenerator = "changeStartGeneratorFromChild($event)"
          :startLabel.sync="parentStartLabel"
          :startGenerator.sync="parentStartGenerator"
          v-bind:id="type" v-bind:key="type"
        />
      </template>

      <template v-if="type === 'branch'">
        <BranchNodeModal label="this is branch"
          @changeBranchLabel = "changeBranchLabelFromChild($event)"
          @changeBranchSelectedGate = "changeBranchSelectedGateFromChild($event)"
          @changeBranchSelectedType = "changeBranchSelectedTypeFromChild($event)"
          @changeBranchSelectedRule = "changeBranchSelectedRuleFromChild($event)"
          :branchLabel.sync = "parentBranchLabel"
          :selectedGate.sync = "parentBranchSelectedGate"
          :selectedType.sync = "parentBranchSelectedType"
          :selectedRule.sync = "parentBranchSelectedRule"
          v-bind:id="type" v-bind:key="type"
        />
      </template>

      <template v-if="type === 'activity'">
        <ActivityNodeModal
        @changeActNodeSelectedActivityType = "changeActNodeSelectedActivityTypeFromChild($event)"
        @changeActNodeReport = "changeActNodeReportFromChild($event)"
        @changeActNodeCustomMonitor = "changeActNodeCustomMonitorFromChild($event)"
        @changeActNodeProcessingTime = "changeActNodeProcessingTimeFromChild($event)"
        @changeActNodeParameter1 = "changeActNodeParameter1FromChild($event)"
        @changeActNodeSetupTime = "changeActNodeSetupTimeFromChild($event)"
        @changeActNodeParameter2 = "changeActNodeParameter2FromChild($event)"
        @changeActNodeUnit = "changeActNodeUnitFromChild($event)"
        @changeActNodeQueueLabel = "changeActNodeQueueLabelFromChild($event)"
        @changeActNodeInputType = "changeActNodeInputTypeFromChild($event)"
        @changeActNodeOutputType = "changeActNodeOutputTypeFromChild($event)"
        @changeActNodeCodeSegment = "changeActNodeCodeSegmentFromChild($event)"
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
import { Component, Prop, Vue } from 'vue-property-decorator';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// Classes
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphControlImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphImpl';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/chdsr/classes/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/chdsr/classes/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/chdsr/classes/JointGraphPageImpl';

// Interfaces
import { GraphConnector } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphConnector';
import { GraphData } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphData';
import { GraphElement } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphElement';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';

// Enums
import { NODE_TYPE } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/chdsr/enums/NODE';
import { ARC_TYPE } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/chdsr/enums/ARC';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

// Components
import StartNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/StartNodeModal.vue';
import ActivityNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/ActivityNodeModal.vue';
import BranchNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/BranchNodeModal.vue';
import StopNodeModal from '@/iochord/chdsr/simulation/editor/components/modals/StopNodeModal.vue';
import { GraphStartEventNode } from '../../../../../common/graph/sbpnet/interfaces/components/GraphStartEventNode';

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
})
export default class CanvasComponent extends BaseComponent {
  @Prop() public response?: Graph;

  // Find all node types in the graph
  public nodeTypes: Set<string> = new Set<string>();

  // Graph data
  public graph?: Graph;

  // Active element
  public selectedElement?: GraphElement;

  public parentStartLabel: string = '';
  public parentStartGenerator: string = '';

  public parentBranchLabel: string = '';
  public parentBranchSelectedGate: string = '';
  public parentBranchSelectedType: string = '';
  public parentBranchSelectedRule: string = '';

  public parentActNodeSelectedActivityType: string = '';
  public parentActNodeReport: boolean = false;
  public parentActNodeCustomMonitor: string = '';
  public parentActNodeProcessingTime: string = '';
  public parentActNodeParameter1: string = '';
  public parentActNodeSetupTime: string = '';
  public parentActNodeParameter2: string = '';
  public parentActNodeUnit: string = '';
  public parentActNodeQueueLabel: string = '';
  public parentActNodeInputType: string = '';
  public parentActNodeOutputType: string = '';
  public parentActNodeCodeSegment: string = '';

  public parentStopLabel: string = '';
  public parentStopReport: boolean = false;

  public currentSelectedElement?: GraphNode;
  public activePage?: GraphPage;

/*
  Start Node functions
  - Send changes from parent to child
  - Retrieve changes from child to parent
*/
  public changeStartLabel(newVal: string): void {
    this.parentStartLabel = newVal;
  }

  public changeStartGenerator(newVal: string): void {
    this.parentStartGenerator = newVal;
  }

  /* Start updated from Child */
  public changeStartLabelFromChild(e: any) {
    this.parentStartLabel = e;
    this.currentSelectedElement!.setLabel(this.parentStartLabel);
    this.graph!.getPages()!
      .get(this.activePage!.getId() as string)!.getNodes()!
      .set(this.currentSelectedElement!.getId() as string, this.currentSelectedElement as GraphNode);
  }

  public changeStartGeneratorFromChild(e: any) {
    this.parentStartGenerator = e;
  }

/*
  Branch Node functions
  - Send changes from parent to child
  - Retrieve changes from child to parent
*/
  public changeBranchLabel(newVal: string): void {
    this.parentBranchLabel = newVal;
  }

  public changeBranchSelectedGate(newVal: string): void {
    this.parentBranchSelectedGate = newVal;
  }

  public changeBranchSelectedType(newVal: string): void {
    this.parentBranchSelectedType = newVal;
  }

  public changeBranchSelectedRule(newVal: string): void {
    this.parentBranchSelectedRule = newVal;
  }

  /* Branch updated from Child */
  public changeBranchLabelFromChild(e: any) {
    this.parentBranchLabel = e;
  }

  public changeBranchSelectedGateFromChild(e: any) {
    this.parentBranchSelectedGate = e;
  }

  public changeBranchSelectedTypeFromChild(e: any) {
    this.parentBranchSelectedType = e;
  }

  public changeBranchSelectedRuleFromChild(e: any) {
    this.parentBranchSelectedRule = e;
  }

/*
  Activity Node functions
  - Send changes from parent to child
  - Retrieve changes from child to parent
*/

  public changeActNodeSelectedActivityType(newVal: string): void {
    this.parentActNodeSelectedActivityType = newVal;
  }

  public changeActNodeReport(newVal: boolean): void {
    this.parentActNodeReport = newVal;
  }

  public changeActNodeCustomMonitor(newVal: string): void {
    this.parentActNodeCustomMonitor = newVal;
  }

  public changeActNodeProcessingTime(newVal: string): void {
    this.parentActNodeSelectedActivityType = newVal;
  }

  public changeActNodeParameter1(newVal: string): void {
    this.parentActNodeParameter1 = newVal;
  }

  public changeActNodeSetupTime(newVal: string): void {
    this.parentActNodeSetupTime = newVal;
  }

  public changeActNodeParameter2(newVal: string): void {
    this.parentActNodeParameter2 = newVal;
  }

  public changeActNodeChangeUnit(newVal: string): void {
    this.parentActNodeUnit = newVal;
  }

  public changeActNodeQueueLabel(newVal: string): void {
    this.parentActNodeQueueLabel = newVal;
  }

  public changeActNodeInputType(newVal: string): void {
    this.parentActNodeInputType = newVal;
  }

  public changeActNodeOutputType(newVal: string): void {
    this.parentActNodeOutputType = newVal;
  }

  public changeActNodeCodeSegment(newVal: string): void {
    this.parentActNodeCodeSegment = newVal;
  }

/* From Child */
  public changeActNodeSelectedActivityTypeFromChild(e: any) {
    this.parentActNodeSelectedActivityType = e;
  }

  public changeActNodeReportFromChild(e: any) {
    this.parentActNodeReport = e;
  }

  public changeActNodeCustomMonitorFromChild(e: any) {
    this.parentActNodeCustomMonitor = e;
  }

  public changeActNodeProcessingTimeFromChild(e: any) {
    this.parentActNodeProcessingTime = e;
  }

  public changeActNodeParameter1FromChild(e: any) {
    this.parentActNodeParameter1 = e;
  }

  public changeActNodeSetupTimeFromChild(e: any) {
    this.parentActNodeSetupTime = e;
  }

  public changeActNodeParameter2FromChild(e: any) {
    this.parentActNodeParameter2 = e;
  }

  public changeActNodeUnitFromChild(e: any) {
    this.parentActNodeUnit = e;
  }

  public changeActNodeQueueLabelFromChild(e: any) {
    this.parentActNodeQueueLabel = e;
  }

  public changeActNodeInputTypeFromChild(e: any) {
    this.parentActNodeInputType = e;
  }
  public changeActNodeOutputTypeFromChild(e: any) {
    this.parentActNodeOutputType = e;
  }
  public changeActNodeCodeSegmentFromChild(e: any) {
    this.parentActNodeCodeSegment = e;
  }

/*
  Stop Node functions
  - Send changes from parent to child
  - Retrieve changes from child to parent
*/

  public changeStopLabel(newVal: string): void {
    this.parentStopLabel = newVal;
  }

  public changeStopReport(newVal: boolean): void {
    this.parentStopReport = newVal;
  }

  /* Stop updated from Child */
  public changeStopLabelFromChild(e: any) {
    this.parentStopLabel = e;
    this.currentSelectedElement!.setLabel(this.parentStopLabel);
    this.graph!.getPages()!
      .get(this.activePage!.getId() as string)!.getNodes()!
      .set(this.currentSelectedElement!.getId() as string, this.currentSelectedElement as GraphNode);
  }

  public changeStopReportFromChild(e: any) {
    this.parentStopReport = e;
    this.currentSelectedElement!.setReportStatistics(this.parentStopReport);
    this.graph!.getPages()!
      .get(this.activePage!.getId() as string)!.getNodes()!
      .set(this.currentSelectedElement!.getId() as string, this.currentSelectedElement as GraphNode);
  }

  public mounted(): void {
    this.loadGraph();
    this.activePage = this.graph!.getPages()!.get('0');
    this.$forceUpdate();
  }

  public loadGraph(): void {
    try {
      // Deserialize the model
      this.graph = this.response as Graph;

      // Loop the model page
      for (const [key, value] of this.graph.getPages() as Map<string, GraphPage>) {
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
        } as joint.dia.Paper.Options));

        // Scale down minimap
        jointPage.getMinimap().scale(0.2);

        // Center the minimap
        const MinimapViewportBBox = jointPage.getMinimap().viewport.getBBox();
        jointPage.getMinimap().translate(
          (jointPage.getMinimap().options.width as number / 4) - (MinimapViewportBBox.width / 2),
          (jointPage.getMinimap().options.height as number / 2) - (MinimapViewportBBox.height / 2),
        );

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

          // Add node type to nodeTypes set
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
        const PageViewportBBox = jointPage.getPaper().viewport.getBBox();
        jointPage.getPaper().translate((canvasWidth / 2) - (PageViewportBBox.width / 2), (canvasHeight / 2) - (PageViewportBBox.height / 2));

        jointPage.getPaper().on({
          'element:pointerclick': (elementView: joint.dia.ElementView) => {
            console.log(elementView);
          },
          'element:pointerdblclick': (elementView: joint.dia.ElementView) => {
            const currentElement = elementView.model;
            const currentElementType = currentElement.attributes.type;

            if (currentElementType === 'start') {
              alert('Masuk double klik');
              console.log('sebelum ' + this.parentStartLabel);
              this.changeStartLabel('From Double Click - Start Label');
              console.log(this.parentStartLabel);
            }
          },

          'element:contextmenu': (elementView: joint.dia.ElementView) => {
            const currentElement = elementView.model;
            currentElement.attr('body/stroke', 'red');
            const currentElementType = currentElement.attributes.type;
            const currentElementNodeId = currentElement.attributes.nodeId;

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
              console.log(this.parentActNodeSelectedActivityType);
            }

            if (currentElementType === 'branch') {
              $('#branch').modal('show');
            }

            if (currentElementType === 'stop') {
              $('#stop').modal('show');
              this.parentStopLabel = jointPage.getNodes()!.get(currentElementNodeId)!.getLabel() as string;
              this.parentStopReport = jointPage.getNodes()!.get(currentElementNodeId)!.isReportStatistics() as boolean;
              this.currentSelectedElement = jointPage.getNodes()!.get(currentElementNodeId);
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
    } catch (e) {
      console.log(e);
    }
  }

  /*
   * @author: clientIO
   * @url: https://github.com/clientIO/joint/blob/master/plugins/anchors/joint.anchors.js
   */
  private customPerpendicularAnchor(endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) {
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
