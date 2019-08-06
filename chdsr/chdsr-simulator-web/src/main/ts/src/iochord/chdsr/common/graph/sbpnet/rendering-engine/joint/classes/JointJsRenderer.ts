/** Data Model */
// Interfaces
import { Graph } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/Graph';
import { GraphConnector } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphConnector';
import { GraphData } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphData';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';
import { GraphStartEventNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/components/GraphStartEventNode';

// Enums
import { NODE_TYPE } from '@/iochord/chdsr/common/graph/sbpnet/rendering-engine/joint/shapes/enums/NODE';


/** Joint.js */
// Module
import * as joint from 'jointjs';
import { Mixins } from 'vue-property-decorator';

// Utils
import { Anchors } from './../utils/Anchors';

// Classes
import { JointGraphConnectorImpl } from '../shapes/classes/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '../shapes/classes/JointGraphNodeImpl';
import { JointGraphPageImpl } from './../shapes/classes/JointGraphPageImpl';

// Enums
import { ARC_TYPE } from '../shapes/enums/ARC';

// Mixins
import ActivityNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/ActivityNodeModalMixin';
import BranchNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/BranchNodeModalMixin';
import StartNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/StartNodeModalMixin';
import StopNodeModalMixin from '@/iochord/chdsr/simulation/editor/mixins/modals/StopNodeModalMixin';

// JQuery
// declare const $: any;
import $ from 'jquery';

export default class JointJsRenderer extends Mixins(ActivityNodeModalMixin, BranchNodeModalMixin, StartNodeModalMixin, StopNodeModalMixin) {
  public canvasWidth?: number;
  public canvasHeight?: number;

  // Find all node types in the graph
  public nodeTypes: Set<string> = new Set<string>();

  // Graph data
  public graph?: Graph;

  public currentSelectedElement?: GraphNode;
  public activePage?: GraphPage;

  constructor(graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode) {
    super();
    this.graph = graph;
    this.activePage = activePage;
    this.currentSelectedElement = currentSelectedElement;

    try {
      // Loop all model pages
      for (const [id, page] of graph.getPages() as Map<string, GraphPage>) {
        const jointPage: JointGraphPageImpl = new JointGraphPageImpl();

        this.canvasWidth = $('.editor.canvas').innerWidth();
        this.canvasHeight = $('.editor.canvas').innerHeight();

        // Set properties of the graph page
        this.setProperties(jointPage, page);

        // Render joint.js paper
        this.renderPage(jointPage);
        this.renderMinimap(jointPage);

        // Render elements and links
        this.renderNodes(jointPage);
        this.renderArcs(jointPage);

        // Automatic layout
        joint.layout.DirectedGraph.layout(jointPage.getGraph(), {
          ranker: 'network-simplex',
          rankDir: 'LR',
          edgeSep: 300,
          nodeSep: 200,
          rankSep: 80,
          // align: 'UL',
        } as joint.layout.DirectedGraph.LayoutOptions);

        // Listen to events
        this.whileListenToEvents(jointPage);
      }
    } catch (e) {
      console.error(e);
    }
  }

  public getNodeTypes(): Set<string> {
    return this.nodeTypes;
  }

  private setProperties(jointPage: JointGraphPageImpl, currentPage: GraphPage): void {
    jointPage.setId(currentPage.getId() as string);
    jointPage.setLabel(currentPage.getLabel() as string);
    jointPage.setType(currentPage.getType() as string);
    jointPage.setAttributes(currentPage.getAttributes() as Map<string, string>);
    jointPage.setGraph(new joint.dia.Graph());
    jointPage.setNodes(currentPage.getNodes() as Map<string, GraphNode>);
    jointPage.setArcs(currentPage.getArcs() as Map<string, GraphConnector>);
    jointPage.setData(currentPage.getData() as Map<string, GraphData>);
  }

  private renderPage(jointPage: JointGraphPageImpl): void {
    // Set the paper as the graph container
    jointPage.setPaper(new joint.dia.Paper({
      el: document.getElementById('canvas'),
      model: jointPage.getGraph(),
      width: this.canvasWidth,
      height: this.canvasHeight,
      gridSize: 10,
      drawGrid: true,
      defaultRouter: { name: 'normal' },
      defaultAnchor: (endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) => {
        return Anchors.skipEndMagnetPerpendicularAnchor(endView, endMagnet, anchorReference, args);
      },
      defaultConnectionPoint: { name: 'boundary' },
      defaultConnector: { name: 'rounded' },
    } as joint.dia.Paper.Options ));

    // Center the view
    const PageViewportBBox = jointPage.getPaper().viewport.getBBox();
    jointPage.getPaper().translate((this.canvasWidth as number / 2) - (PageViewportBBox.width / 2), (this.canvasHeight as number / 2) - (PageViewportBBox.height / 2));
  }

  private renderMinimap(jointPage: JointGraphPageImpl): void {
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
  }

  private renderNodes(jointPage: JointGraphPageImpl): void {
    const keys: any = { elementType: 'elementType' };

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
  }

  private renderArcs(jointPage: JointGraphPageImpl): void {
    const keys: any = { elementType: 'elementType' };

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
  }

  private whileListenToEvents(jointPage: JointGraphPageImpl): void {

    // Helper to reset all elements
    const resetAll = (paper: any) => {

      /* Reset all elements in the paper */
      const elements = paper.model.getElements();
      for (let i = 0, ii = elements.length; i < ii; i++) {
        const currentElement = elements[i];
        currentElement.attr('body/stroke', 'black');
      }
    };

    // Listening to events
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
          ($('#start') as any).modal('show');
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
          ($('#activity') as any).modal('show');
          console.log(this.parentActNodeSelectedActivityType);
        }

        if (currentElementType === 'branch') {
          ($('#branch') as any).modal('show');
        }

        if (currentElementType === 'stop') {
          ($('#stop') as any).modal('show');
          this.parentStopLabel = jointPage.getNodes()!.get(currentElementNodeId)!.getLabel() as string;
          this.parentStopReport = jointPage.getNodes()!.get(currentElementNodeId)!.isReportStatistics() as boolean;
          this.currentSelectedElement = jointPage.getNodes()!.get(currentElementNodeId);
        }
      },
    });
  }
}
