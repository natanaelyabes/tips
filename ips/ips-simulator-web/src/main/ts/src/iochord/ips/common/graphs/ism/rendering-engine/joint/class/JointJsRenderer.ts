/** Data Model */
// Interfaces
import { Graph } from '@/iochord/ips/common/graphs/ism/interfaces/Graph';
import { GraphConnector } from '@/iochord/ips/common/graphs/ism/interfaces/GraphConnector';
import { GraphData } from '@/iochord/ips/common/graphs/ism/interfaces/GraphData';
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';

// Classes
import { JointGraphConnectorImpl } from '../shapes/class/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '../shapes/class/JointGraphNodeImpl';
import { JointGraphPageImpl } from '../shapes/class/JointGraphPageImpl';

// Utils
import { Anchors } from '../utils/Anchors';

// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/enums/NODE';
import { DATA_TYPE } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/enums/DATA';
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
import { GraphBranchNodeImpl } from '../../../class/components/GraphBranchNodeImpl';
import { BRANCH_TYPE, BRANCH_RULE, BRANCH_GATE } from '../../../enums/BRANCH';

/**
 * Graph renderer based on Joint.js library.
 *
 * @export
 * @class JointJsRenderer
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default class JointJsRenderer {

  /**
   * The width of canvas with respect to the resolution of screen monitor.
   *
   * @type {number}
   * @memberof JointJsRenderer
   */
  public canvasWidth?: number;

  /**
   * The height of canvas with respect to the resolution of screen monitor.
   *
   * @type {number}
   * @memberof JointJsRenderer
   */
  public canvasHeight?: number;

  /**
   * The types of node identified within the current graph.
   *
   * @type {Set<string>}
   * @memberof JointJsRenderer
   */
  public nodeTypes: Set<string> = new Set<string>();

  /**
   * The types of data node identified within the current graph.
   *
   * @type {Set<string>}
   * @memberof JointJsRenderer
   */
  public dataTypes: Set<string> = new Set<string>();

  /**
   * Graph object
   *
   * @type {Graph}
   * @memberof JointJsRenderer
   */
  public graph?: Graph;

  /**
   * Active page need to be displayed in the canvas.
   *
   * @type {GraphPage}
   * @memberof JointJsRenderer
   */
  public activePage?: GraphPage;

  /**
   * Map of graph pages.
   *
   * @type {TSMap<string, JointGraphPageImpl>}
   * @memberof JointJsRenderer
   */
  public jointPages: TSMap<string, JointGraphPageImpl> = new TSMap<string, JointGraphPageImpl>();

  /**
   * Current selected element, identified by highlighted events.
   *
   * @type {GraphNode}
   * @memberof JointJsRenderer
   */
  public currentSelectedElement?: GraphNode;

  /**
   * Instance of SvgPanZoom.
   *
   * @type {SvgPanZoom.Instance}
   * @memberof JointJsRenderer
   */
  public panAndZoom?: SvgPanZoom.Instance;

  /**
   * Creates an instance of JointJsRenderer.
   *
   * @param {Graph} graph
   * @param {GraphPage} activePage
   * @param {GraphNode} currentSelectedElement
   * @param {boolean} [isProcessModel]
   * @memberof JointJsRenderer
   */
  constructor(graph: Graph, activePage: GraphPage, currentSelectedElement: GraphNode, isProcessModel?: boolean) {
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

        if (!isProcessModel) {
          this.renderData(jointPage);
        }

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

  /**
   * Returns the identified node types within the graph of current active page.
   *
   * @returns {Set<string>}
   * @memberof JointJsRenderer
   */
  public getNodeTypes(): Set<string> {
    return this.nodeTypes;
  }

  /**
   * Returns the identified data node types within the graph of current active page.
   *
   * @returns {Set<string>}
   * @memberof JointJsRenderer
   */
  public getDataTypes(): Set<string> {
    return this.dataTypes;
  }

  /**
   * Current selected active page of the graph.
   *
   * @param {string} pageId
   * @returns
   * @memberof JointJsRenderer
   */
  public activeJointPage(pageId: string) {
    return this.jointPages.get(pageId as string);
  }

  /**
   * Assign the properties of graph page.
   *
   * @param {JointGraphPageImpl} jointPage
   * @param {GraphPage} currentPage
   * @memberof JointJsRenderer
   */
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

  /**
   * Render the graph page to canvas.
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
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

  /**
   * Render the minimap to canvas.
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
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

  /**
   * Render nodes element to canvas.
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
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

      if (node.getType() === 'branch') {
        const gate: BRANCH_GATE = (nodeValue as GraphBranchNodeImpl).getGate() as BRANCH_GATE;
        const type: BRANCH_TYPE = (nodeValue as GraphBranchNodeImpl).getBranchType() as BRANCH_TYPE;

        if (gate === BRANCH_GATE.AND && type === BRANCH_TYPE.JOIN) {
          node.setImageIcon(NODE_TYPE.and_join.image);
        } else if (gate === BRANCH_GATE.AND && type === BRANCH_TYPE.SPLIT) {
          node.setImageIcon(NODE_TYPE.and_split.image);
        } else if (gate === BRANCH_GATE.XOR && type === BRANCH_TYPE.JOIN) {
          node.setImageIcon(NODE_TYPE.xor_join.image);
        } else if (gate === BRANCH_GATE.XOR && type === BRANCH_TYPE.SPLIT) {
          node.setImageIcon(NODE_TYPE.xor_split.image);
        }
      }

      if (node.getType() === 'activity' && nodeValue.getImageIcon() !== '') {
        node.setImageIcon(nodeValue.getImageIcon());
      }

      // render node
      node.render(jointPage.getGraph());

      // Add node type to nodeTypes set
      this.nodeTypes.add(node.getType() as string);
    });
  }

  /**
   * Render data to canvas.
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
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

  /**
   * Render connectors to canvas.
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
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
      arc.setSourceIndex(arcValue.getSourceIndex() as number);
      arc.setTargetIndex(arcValue.getTargetIndex() as number);

      // render connector
      arc.render(jointPage.getGraph());
    });
  }

  /**
   * Order the layout of the graph.
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
  public autoLayoutGraph(jointPage: JointGraphPageImpl): void {
    joint.layout.DirectedGraph.layout(jointPage.getGraph(), {
      ranker: 'network-simplex',
      rankDir: 'LR',
      edgeSep: 300,
      nodeSep: 200,
      rankSep: 80,
    } as joint.layout.DirectedGraph.LayoutOptions);
  }

  /**
   * Position the graph to the center of the canvas
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
  public centerGraph(jointPage: JointGraphPageImpl): void {
    const PageViewportBBox = jointPage.getPaper().viewport.getBBox();
    jointPage.getPaper().translate(
      (this.canvasWidth as number / 2) - (PageViewportBBox.width / 2),
      (this.canvasHeight as number / 2) - (PageViewportBBox.height / 2),
    );
  }

  /**
   * Position the graph to the center of the minimap
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
  public centerMinimap(jointPage: JointGraphPageImpl): void {
    const MinimapViewportBBox = jointPage.getMinimap().viewport.getBoundingClientRect();
    jointPage.getMinimap().translate(
      (jointPage.getMinimap().options.width as number / 2) - (MinimapViewportBBox.width / 2),
      (jointPage.getMinimap().options.height as number / 2) - (MinimapViewportBBox.height / 2),
    );
  }

  /**
   * Enable pan and zoom for minimap and canvas
   *
   * @param {JointGraphPageImpl} jointPage
   * @memberof JointJsRenderer
   */
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
