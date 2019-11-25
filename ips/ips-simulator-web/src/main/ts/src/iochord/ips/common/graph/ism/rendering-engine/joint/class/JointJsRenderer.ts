/** Data Model */
// Interfaces
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphConnector } from '@/iochord/ips/common/graph/ism/interfaces/GraphConnector';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';

// Classes
import { JointGraphConnectorImpl } from '../shapes/class/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '../shapes/class/JointGraphNodeImpl';
import { JointGraphPageImpl } from '../shapes/class/JointGraphPageImpl';

// Utils
import { Anchors } from '../utils/Anchors';

// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/NODE';
import { DATA_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/DATA';
import { ARC_TYPE } from '../shapes/enums/ARC';

/** Joint.js */
// Module
import * as joint from 'jointjs';

// SVG Pan and Zoom
import SvgPanZoom from 'svg-pan-zoom';

// JQuery
import 'jquery';
declare const $: any;

import { TSMap } from 'typescript-map';
import { JointGraphDataImpl } from '../shapes/class/JointGraphDataImpl';


export default class JointJsRenderer {
  public canvasWidth?: number;
  public canvasHeight?: number;

  // Find all node types in the graph
  public nodeTypes: Set<string> = new Set<string>();
  public dataTypes: Set<string> = new Set<string>();

  // Graph data
  public graph?: Graph;

  public activePage?: GraphPage;
  public jointPages: TSMap<string, JointGraphPageImpl> = new TSMap<string, JointGraphPageImpl>();
  public currentSelectedElement?: GraphNode;

  public panAndZoom?: SvgPanZoom.Instance;

  constructor(graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode) {
    this.graph = graph;
    this.activePage = activePage;
    this.currentSelectedElement = currentSelectedElement;

    this.canvasWidth = $('.editor.canvas').innerWidth();
    this.canvasHeight = $('.editor.canvas').innerHeight();

    try {

      // Loop all model pages
      (this.graph.getPages() as TSMap<string, GraphPage>).forEach((page: GraphPage) => {
        const jointPage: JointGraphPageImpl = new JointGraphPageImpl();

        // Set properties of the graph page
        this.setProperties(jointPage, page);

        // Render joint.js paper
        this.renderPage(jointPage);
        this.renderMinimap(jointPage);

        // Render elements and links
        this.renderNodes(jointPage);
        this.renderConnectors(jointPage);

        this.renderData(jointPage);

        // Automatic layout
        this.autoLayoutGraph(jointPage);

        // Center the view
        this.centerGraph(jointPage);
        this.centerMinimap(jointPage);

        // Enable pan and zoom
        this.enableZoomAndPanning(jointPage);

        this.jointPages.set(jointPage.getId() as string, jointPage);
      });
    } catch (e) {
      // console.error(e);
    }
  }

  public getNodeTypes(): Set<string> {
    return this.nodeTypes;
  }

  public getDataTypes(): Set<string> {
    return this.dataTypes;
  }

  public activeJointPage(pageId: string) {
    return this.jointPages.get(pageId as string);
  }

  public setProperties(jointPage: JointGraphPageImpl, currentPage: GraphPage): void {
    jointPage.setId(currentPage.getId() as string);
    jointPage.setLabel(currentPage.getLabel() as string);
    jointPage.setType(currentPage.getType() as string);
    jointPage.setAttributes(currentPage.getAttributes() as TSMap<string, string>);
    jointPage.setGraph(new joint.dia.Graph());
    jointPage.setNodes(currentPage.getNodes() as TSMap<string, GraphNode>);
    jointPage.setConnectors(currentPage.getConnectors() as TSMap<string, GraphConnector>);
    jointPage.setData(currentPage.getData() as TSMap<string, GraphData>);
  }

  public renderPage(jointPage: JointGraphPageImpl): void {

    // Set the paper as the graph container
    jointPage.setPaper(new joint.dia.Paper({
      el: document.getElementById('canvas'),
      model: jointPage.getGraph(),
      width: this.canvasWidth,
      height: this.canvasHeight,
      gridSize: 10,
      drawGrid: true,
      interactive: true,
      defaultRouter: { name: 'normal' },
      defaultAnchor: (endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) => {
        return Anchors.skipEndMagnetPerpendicularAnchor(endView, endMagnet, anchorReference, args);
      },
      defaultConnectionPoint: { name: 'boundary' },
      defaultConnector: { name: 'rounded' },
    } as joint.dia.Paper.Options ));
  }

  public renderMinimap(jointPage: JointGraphPageImpl): void {

    // Set the minimap for each page
    jointPage.setMinimap(new joint.dia.Paper({
      el: document.getElementById('minimap'),
      model: jointPage.getGraph(),
      width: $('#minimap').parent().width(),
      height: 150,
      gridSize: 1,
      interactive: false,
      defaultRouter: { name: 'normal' },
      defaultAnchor: (endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) => {
        return Anchors.skipEndMagnetPerpendicularAnchor(endView, endMagnet, anchorReference, args);
      },
    } as joint.dia.Paper.Options));

    // Scale down minimap
    jointPage.getMinimap().scale(0.1);
  }

  public renderNodes(jointPage: JointGraphPageImpl): void {
    const keys: any = { elementType: 'elementType' };

    // for all nodes
    (jointPage.getNodes() as TSMap<string, GraphNode>).forEach((nodeValue) => {

      const node = new JointGraphNodeImpl();
      node.setId(nodeValue.getId() as string);
      node.setLabel(nodeValue.getLabel() as string);
      node.setType((nodeValue as any)[keys.elementType] as string);
      node.setAttributes(nodeValue.getAttributes() as TSMap<string, string>);
      node.setPosition({ x: 300, y: 250 });
      node.setSize((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].size);
      node.setMarkup((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].markup);
      node.setAttr((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].attr);
      node.setImageIcon((NODE_TYPE as any)[(nodeValue as any)[keys.elementType]].image);

      // TODO: From URL
      // node.setImageIcon('https://imageog.flaticon.com/icons/png/512/62/62470.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF');

      // TODO: Demonstrate the use of custom icon
      // if (nodeValue.getLabel() === 'ATM Service') {
      //   node.setImageIcon(require('@/assets/images/icons/atm-png.png'));
      // } else if (nodeValue.getLabel() === 'Teller Service') {
      //   node.setImageIcon(require('@/assets/images/icons/business-customer-icon.png'));
      // }

      // render node
      node.render(jointPage.getGraph());

      // Add node type to nodeTypes set
      this.nodeTypes.add(node.getType() as string);
    });
  }

  public renderData(jointPage: JointGraphPageImpl): void {
    const keys: any = { elementType: 'elementType' };

    // for all nodes
    (jointPage.getData() as TSMap<string, GraphData>).forEach((dataValue) => {

      const data = new JointGraphDataImpl();
      data.setId(dataValue.getId() as string);
      data.setLabel(dataValue.getLabel() as string);
      data.setType((dataValue as any)[keys.elementType] as string);
      data.setAttributes(dataValue.getAttributes() as TSMap<string, string>);
      data.setPosition({ x: 300, y: 250 });
      data.setSize((DATA_TYPE as any)[(dataValue as any)[keys.elementType]].size);
      data.setMarkup((DATA_TYPE as any)[(dataValue as any)[keys.elementType]].markup);
      data.setAttr((DATA_TYPE as any)[(dataValue as any)[keys.elementType]].attr);
      data.setImageIcon((DATA_TYPE as any)[(dataValue as any)[keys.elementType]].image);

      // render data
      data.render(jointPage.getGraph());

      // Add data type to dataTypes set
      this.dataTypes.add(data.getType() as string);
    });
  }

  public renderConnectors(jointPage: JointGraphPageImpl): void {
    const keys: any = { elementType: 'elementType' };

    // for all connectors
    (jointPage.getConnectors() as TSMap<string, GraphConnector>).forEach((arcValue) => {
      const arc = new JointGraphConnectorImpl();
      arc.setId(arcValue.getId() as string);
      arc.setLabel(arcValue.getLabel() as string);
      arc.setType((arcValue as any)[keys.elementType] as string);
      arc.setAttributes(arcValue.getAttributes() as TSMap<string, string>);
      arc.setSourceRef(arcValue.getSourceRef() as string);
      arc.setTargetRef(arcValue.getTargetRef() as string);
      arc.setAttr(ARC_TYPE.connector.attr);

      // render connector
      arc.render(jointPage.getGraph());
    });
  }

  public autoLayoutGraph(jointPage: JointGraphPageImpl): void {
    joint.layout.DirectedGraph.layout(jointPage.getGraph(), {
      ranker: 'network-simplex',
      rankDir: 'LR',
      edgeSep: 300,
      nodeSep: 200,
      rankSep: 80,
    } as joint.layout.DirectedGraph.LayoutOptions);
  }

  public centerGraph(jointPage: JointGraphPageImpl): void {
    const PageViewportBBox = jointPage.getPaper().viewport.getBBox();
    jointPage.getPaper().translate(
      (this.canvasWidth as number / 2) - (PageViewportBBox.width / 2),
      (this.canvasHeight as number / 2) - (PageViewportBBox.height / 2),
    );
  }

  public centerMinimap(jointPage: JointGraphPageImpl): void {
    const MinimapViewportBBox = jointPage.getMinimap().viewport.getBoundingClientRect();
    jointPage.getMinimap().translate(
      (jointPage.getMinimap().options.width as number / 2) - (MinimapViewportBBox.width / 2),
      (jointPage.getMinimap().options.height as number / 2) - (MinimapViewportBBox.height / 2),
    );
  }

  public enableZoomAndPanning(jointPage: JointGraphPageImpl): void {

    // Enable pan and zoom for minimap
    this.panAndZoom = SvgPanZoom('#minimap svg').disablePan();
    this.panAndZoom.disableDblClickZoom();
    this.panAndZoom.setMinZoom(0);
    this.panAndZoom.setMaxZoom(100);
    this.panAndZoom.zoom(0.8);

    // Enable pan and zoom for canvas
    this.panAndZoom = SvgPanZoom('#canvas svg').disablePan();
    this.panAndZoom.enableControlIcons();
    this.panAndZoom.disableDblClickZoom();
    this.panAndZoom.setMinZoom(0);
    this.panAndZoom.setMaxZoom(100);
    this.panAndZoom.zoom(0.8);
  }
}
