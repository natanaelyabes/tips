<template>
  <div class="sandbox editor test view">
    <div id="canvas-container" class="editor canvas">
      <div id="canvas"></div>
    </div>
  </div>
</template>

<style>
.sandbox.editor.test.view {
  height: 100%;
}
.sandbox.editor.test.view .editor.canvas {
  width: 100%;
  height: 100%;
}
</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import { GraphConnector } from '../../common/graph/interfaces/GraphConnector';
import { GraphData } from '../../common/graph/interfaces/GraphData';
import { GraphImpl } from '@/iochord/chdsr/common/graph/classes/GraphImpl';
import { GraphPage } from '../../common/graph/interfaces/GraphPage';
import { GraphNode } from '../../common/graph/interfaces/GraphNode';
import { JointGraphConnectorImpl } from '../../common/lib/joint/shapes/chdsr/classes/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '../../common/lib/joint/shapes/chdsr/classes/JointGraphNodeImpl';
import { JointGraphPageImpl } from '../../common/lib/joint/shapes/chdsr/classes/JointGraphPageImpl';
import { SbpnetModelService } from '../../common/service/model/SbpnetModelService';
import { NODE_TYPE } from '../../common/lib/joint/shapes/chdsr/enums/NODE';
import { ARC_TYPE } from '../../common/lib/joint/shapes/chdsr/enums/ARC';


// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// Class
import PageView from '@/iochord/chdsr/common/lib/vue/classes/PageView';

declare const $: any;

@Component
export default class SandboxEditorTest extends PageView {
  private graphData: Graph = new GraphImpl();

  /** @Override */
  public async mounted(): Promise<void> {
    try {
      this.graphData = await SbpnetModelService.getInstance().getExampleModel();

      console.log(this.graphData);

      // Loop the model page
      for (const [key, value] of this.graphData.getPages() as Map<string, GraphPage>) {
        const jointPage: JointGraphPageImpl = new JointGraphPageImpl();
        const canvasWidth: number = $('#canvas-container').innerWidth();
        const canvasHeight: number = $('#canvas-container').innerHeight();
        console.log(canvasHeight);
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
          'blank:pointerdown': () => {
            resetAll(jointPage.getPaper());
          },
          'element:pointerdown': (elementView: joint.dia.ElementView) => {
            resetAll(jointPage.getPaper());
            const currentElement = elementView.model;
            currentElement.attr('border/strokeWidth', 2);
            currentElement.attr('border/stroke', 'blue');
          },

          'element:pointerdblclick': (elementView: joint.dia.ElementView) => {
            resetAll(jointPage.getPaper());
            const currentElement = elementView.model;
            const currentElementType = currentElement.attributes.type;
            currentElement.attr('border/strokeWidth', 2);
            currentElement.attr('border/stroke', 'blue');
          },

          'element:contextmenu': (elementView: joint.dia.ElementView) => {
            const currentElement = elementView.model;
            const currentElementType = currentElement.attributes.type;
            const currentElementNodeId = currentElement.attributes.nodeId;

            currentElement.attr('border/strokeWidth', 2);
            currentElement.attr('border/stroke', 'blue');
          },
        });

        function resetAll(_paper: joint.dia.Paper) {

          /* Reset all elements in the paper */
          const elements = _paper.model.getElements();
          console.log(elements);
          for (let i = 0, ii = elements.length; i < ii; i++) {
            const currentElement = elements[i];
            currentElement.attr('border/strokeWidth', 0);
            currentElement.attr('border/stroke', 'transparent');
          }

          // const links = _paper.model.getLinks();
          // for (let j = 0, jj = links.length; j < jj; j++) {
          //   const currentLink = links[j];
          //   currentLink.attr('line/stroke', 'black');
          //   currentLink.label(0, {
          //     attrs: {
          //       body: {
          //         stroke: 'black',
          //       },
          //     },
          //   });
          // }

        }
      }
    } catch (e) {
      console.error(e);
    }

    this.forceReRender();
  }

  /** @Override */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Sandbox Editor Test');
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
