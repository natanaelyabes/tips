<template>
  <div class="canvas component">
    <div class="editor canvas">
      <h1>{{parentSelectedGate}}</h1>
      <div id="canvas"></div>
    </div>

    <!-- Model Modals -->
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
          v-bind:id="type" v-bind:key="type" />
      </template>

      <template v-if="type === 'activity'">
        <ActivityNodeModal v-bind:id="type" v-bind:key="type"/>
      </template>

      <template v-if="type === 'stop'">
        <StopNodeModal v-bind:id="type" v-bind:key="type"/>
      </template>
    </template>
  </div>
</template>

<style scoped>
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
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import { GraphControlImpl } from '@/iochord/chdsr/common/graph/classes/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/chdsr/common/graph/classes/GraphImpl';
import { GraphPage } from '@/iochord/chdsr/common/graph/interfaces/GraphPage';
import { GraphPageImpl } from '@/iochord/chdsr/common/graph/classes/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/chdsr/common/lib/joint/shapes/chdsr/classes/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/chdsr/common/lib/joint/shapes/chdsr/classes/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/chdsr/common/lib/joint/shapes/chdsr/classes/JointGraphPageImpl';

// Interfaces
import { GraphConnector } from '@/iochord/chdsr/common/graph/interfaces/GraphConnector';
import { GraphData } from '@/iochord/chdsr/common/graph/interfaces/GraphData';
import { GraphElement } from '@/iochord/chdsr/common/graph/interfaces/GraphElement';
import { GraphNode } from '@/iochord/chdsr/common/graph/interfaces/GraphNode';

// Enums
import { NODE_TYPE } from '@/iochord/chdsr/common/lib/joint/shapes/chdsr/enums/NODE';
import { ARC_TYPE } from '@/iochord/chdsr/common/lib/joint/shapes/chdsr/enums/ARC';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';

// Components
import StartNodeModal from '@/iochord/chdsr/common/kpi/components/modals/StartNodeModal.vue';
import ActivityNodeModal from '@/iochord/chdsr/common/kpi/components/modals/ActivityNodeModal.vue';
import BranchNodeModal from '@/iochord/chdsr/common/kpi/components/modals/BranchNodeModal.vue';
import StopNodeModal from '@/iochord/chdsr/common/kpi/components/modals/StopNodeModal.vue';

// JQuery Handler
declare const $: any;

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

  public parentLabel: string = '';
  public parentSelectedGate: string = '';
  public parentSelectedType: string = '';
  public parentSelectedRule: string = '';

  public changeLabelFromChild(e: any) {
    this.parentLabel = e;
  }

  public selectedGateFromChild(e: any) {
    this.parentSelectedGate = e;
  }

  public selectedTypeFromChild(e: any) {
    this.parentSelectedType = e;
  }

  public selectedRuleFromChild(e: any) {
    this.parentSelectedRule = e;
  }

  public mounted(): void {
    this.loadGraph();
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
        // const MinimapViewportBBox = jointPage.getMinimap().viewport.getBBox();
        const MinimapViewportBBox = {
          width: jointPage.getMinimap().viewport.clientWidth,
          height: jointPage.getMinimap().viewport.clientHeight,
        };

        console.log(MinimapViewportBBox);
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
          'element:contextmenu': (elementView) => {
            const currentElement = elementView.model;
            currentElement.attr('body/stroke', 'red');
            const currentElementType = currentElement.attributes.type;
            const currentElementNodeId = currentElement.attributes.nodeId;
            console.log(currentElementNodeId);

            if (currentElementType === 'start') {
              $('#start').modal('show');
            }

            if (currentElementType === 'activity') {
              $('#activity').modal('show');
            }

            if (currentElementType === 'branch') {
              $('#branch').modal('show');
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
