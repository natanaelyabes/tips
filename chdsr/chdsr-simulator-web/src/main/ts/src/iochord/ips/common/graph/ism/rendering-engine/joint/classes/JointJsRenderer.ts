/** Data Model */
// Interfaces
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphConnector } from '@/iochord/ips/common/graph/ism/interfaces/GraphConnector';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';

// Classes
import { JointGraphConnectorImpl } from '../shapes/classes/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '../shapes/classes/JointGraphNodeImpl';
import { JointGraphPageImpl } from '../shapes/classes/JointGraphPageImpl';

// Utils
import { Anchors } from '../utils/Anchors';

// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/NODE';
import { ARC_TYPE } from '../shapes/enums/ARC';

/** Joint.js */
// Module
import * as joint from 'jointjs';

// SVG Pan and Zoom
import SvgPanZoom from 'svg-pan-zoom';

// JQuery
import 'jquery';
declare const $: any;

export default class JointJsRenderer {
  public canvasWidth?: number;
  public canvasHeight?: number;

  // Find all node types in the graph
  public nodeTypes: Set<string> = new Set<string>();

  // Graph data
  public graph?: Graph;

  public activePage?: GraphPage;
  public jointPages: Map<string, JointGraphPageImpl> = new Map<string, JointGraphPageImpl>();
  public currentSelectedElement?: GraphNode;
  public canvasPanAndZoom?: SvgPanZoom.Instance;
  public minimapPanAndZoom?: SvgPanZoom.Instance;

  constructor(graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode) {
    this.graph = graph;
    this.activePage = activePage;
    this.currentSelectedElement = currentSelectedElement;

    this.canvasWidth = $('.editor.canvas').innerWidth();
    this.canvasHeight = $('.editor.canvas').innerHeight();

    try {

      // Loop all model pages
      for (const [id, page] of graph.getPages() as Map<string, GraphPage>) {
        const jointPage: JointGraphPageImpl = new JointGraphPageImpl();

        // Set properties of the graph page
        this.setProperties(jointPage, page);

        // Render joint.js paper
        this.renderPage(jointPage);
        this.renderMinimap(jointPage);

        // Render elements and links
        this.renderNodes(jointPage);
        this.renderArcs(jointPage);

        // Automatic layout
        this.autoLayoutGraph(jointPage);

        // Center the view
        this.centerGraph(jointPage);
        this.centerMinimap(jointPage);

        // Enable zooming and panning
        this.enableZoomAndPanning(jointPage);

        // Set joint pages within an object
        this.jointPages.set(jointPage.getId() as string, jointPage);
      }
    } catch (e) {
      console.error(e);
    }
  }

  public getNodeTypes(): Set<string> {
    return this.nodeTypes;
  }

  public activeJointPage(pageId: string) {
    return this.jointPages.get(pageId as string);
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
    // jointPage.getMinimap().scale(0.2);
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

  private autoLayoutGraph(jointPage: JointGraphPageImpl): void {
    joint.layout.DirectedGraph.layout(jointPage.getGraph(), {
      ranker: 'network-simplex',
      rankDir: 'LR',
      edgeSep: 300,
      nodeSep: 200,
      rankSep: 80,
      // align: 'UL',
    } as joint.layout.DirectedGraph.LayoutOptions);
  }

  private centerGraph(jointPage: JointGraphPageImpl): void {
    const PageViewportBBox = jointPage.getPaper().viewport.getBBox();
    jointPage.getPaper().translate(
      (this.canvasWidth as number / 2) - (PageViewportBBox.width / 2),
      (this.canvasHeight as number / 2) - (PageViewportBBox.height / 2),
    );
  }

  private centerMinimap(jointPage: JointGraphPageImpl): void {
    const MinimapViewportBBox = jointPage.getMinimap().viewport.getBoundingClientRect();
    jointPage.getMinimap().translate(
      (jointPage.getMinimap().options.width as number / 2) - (MinimapViewportBBox.width / 2),
      (jointPage.getMinimap().options.height as number / 2) - (MinimapViewportBBox.height / 2),
    );
  }

  private enableZoomAndPanning(jointPage: JointGraphPageImpl): void {
    // Enable pan and zoom for canvas
    this.canvasPanAndZoom = SvgPanZoom('#canvas svg').disablePan();
    this.canvasPanAndZoom.enableControlIcons();
    this.canvasPanAndZoom.disableDblClickZoom();
    this.canvasPanAndZoom.setMinZoom(0);
    this.canvasPanAndZoom.setMaxZoom(100);
    this.canvasPanAndZoom.zoom(0.8);

    // Enable pan and zoom for minimap
    setTimeout(() => {
      this.minimapPanAndZoom = SvgPanZoom('#minimap svg').disablePan();
      this.minimapPanAndZoom.disableDblClickZoom();
      this.minimapPanAndZoom.setMinZoom(0);
      this.minimapPanAndZoom.setMaxZoom(100);
      this.minimapPanAndZoom.zoom(0.8);
      this.minimapPanAndZoom.setBeforePan((oldPoint: SvgPanZoom.Point, newPoint: SvgPanZoom.Point) => {
        return { x: false, y: false };
      });
    }, 10);

    // While canvas is zoomed
    this.canvasPanAndZoom.setOnZoom((scale) => {
      if (this.minimapPanAndZoom) {
        this.minimapPanAndZoom.zoom(scale);
      }
    });

    // While canvas is panned
    this.canvasPanAndZoom.setOnPan((point: SvgPanZoom.Point) => {
      if (this.minimapPanAndZoom) {
        this.minimapPanAndZoom.pan(point);
      }
    });
  }
}
