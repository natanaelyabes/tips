<template>
  <div class="sandbox editor test view">

    <div id="palette" class="ui vertical labeled icon menu">
      <a class="item" @mousedown="createItem('start', $event)">
        <i class="green outline circle icon"></i>
        Start
      </a>
      <a class="item" @mousedown="createItem('stop', $event)">
        <i class="red circle icon"></i>
        Stop
      </a>
      <a class="item" @mousedown="createItem('activity', $event)">
        <i class="blue outline square icon"></i>
        Activity
      </a>
      <a class="item" @mousedown="createItem('branch', $event)">
        <i style="transform: rotate(45deg)" class="blue outline square icon"></i>
        Branch
      </a>
    </div>

    <div id="canvas-container" class="editor canvas">
      <div id="canvas" tabindex="0"
        @keydown.esc="cancelCreateItem($event)"
        @mousedown="handleCanvasMouseDown($event)"
        @mousemove="handleCanvasMouseMove($event)"
        @mouseup="handleCanvasMouseUp($event)">
      </div>
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

.sandbox.editor #palette {
  position: absolute;
  z-index: 9999;
  top: 50%;
  transform: translateY(-50%);
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Interfaces
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { GraphConnector } from '@/iochord/ips/common/graph/ism/interfaces/GraphConnector';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';

// Services
import { IsmModelService } from '@/iochord/ips/common/service/model/IsmModelService';

// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/NODE';
import { ARC_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/ARC';
import * as NODE_ENUMS from '@/iochord/ips/common/graph/ism/enums/NODE';

// Classes
import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';
import { GraphActivityNodeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphActivityNodeImpl';
import { GraphImpl } from '@/iochord/ips/common/graph/ism/class/GraphImpl';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';

// Vuex module
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';

// RxJs subject
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

declare const $: any;

enum NODE {
  activity,
  start,
  stop,
  branch,
  monitor,
}

const graphModule = getModule(GraphModule);

@Component<SandboxEditorTest>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class SandboxEditorTest extends PageLayout {
  private graphData: Graph = new GraphImpl();
  private jointPages: Map<string, JointGraphPageImpl> = new Map<string, JointGraphPageImpl>();

  private newItem: any = null;
  private newSelector: JointGraphNodeImpl | null = null;

  private startPoint: { x: number, y: number, direction?: 'left' | 'top' | 'right' | 'bottom' | 'top-left' | 'top-right' | 'bottom-right' | 'bottom-left' | undefined } = { x: 0, y: 0 };

  private activePage?: string;

  /** Current implementation only allows state to be handled seperately */
  private dragging: boolean = false;

  /** Later, a global state must be initialized to represent the latest state of CanvasComponent */
  // private state: 'dragging' | 'moving'

  public createSelector(e: MouseEvent): void {
    /** Local variable initialization */
    const keys: any = {
      elementType: 'elementType',
    };

    /** Create new item container */
    this.newSelector = new JointGraphNodeImpl();
    this.newSelector.setType('selector');
    this.newSelector.setAttr({
      background: {
        fill: 'rgba(0,0,0,0.5)',
      },
    });

    /** Capture svgPoint from MouseEvent */
    const svgPoint = (this.jointPages.get(this.activePage as string)!.getPaper().svg as SVGSVGElement).createSVGPoint();
    svgPoint.x = e.offsetX;
    svgPoint.y = e.offsetY;

    /** Transform svgPoint to joint.js paper matrix */
    const pointTransformed = svgPoint.matrixTransform(this.jointPages.get(this.activePage as string)!.getPaper().viewport.getCTM()!.inverse());

    this.startPoint.x = pointTransformed.x;
    this.startPoint.y = pointTransformed.y;

    this.dragging = true;
  }

  public moveSelector(e: MouseEvent): void {
    if (this.dragging && this.newSelector !== null) {

      /** Capture svgPoint from MouseEvent */
      const svgPoint = (this.jointPages.get(this.activePage as string)!.getPaper().svg as SVGSVGElement).createSVGPoint();
      svgPoint.x = e.offsetX;
      svgPoint.y = e.offsetY;

      /** Transform svgPoint to joint.js paper matrix */
      const pointTransformed = svgPoint.matrixTransform(this.jointPages.get(this.activePage as string)!.getPaper().viewport.getCTM()!.inverse());

      this.newSelector.setSize({
        width: pointTransformed.x - this.startPoint.x < 0 ? 0 : pointTransformed.x - this.startPoint.x,
        height: pointTransformed.y - this.startPoint.y < 0 ? 0 : pointTransformed.y - this.startPoint.y,
      });

      /** Set position according to the transformed point captured from MouseEvent */
      this.newSelector.setPosition({
        x: this.startPoint.x,
        y: this.startPoint.y,
      });

      this.newSelector.render(this.jointPages.get(this.activePage as string)!.getGraph());
    }
  }

  public destroySelector(e: MouseEvent): void {
    this.dragging = false;
    if (this.newSelector !== null) {
      this.newSelector.getNode().remove();
      this.newSelector = new JointGraphNodeImpl();
    }
  }

  public createItem(type: NODE, e: MouseEvent): void {

    /** Local variable initialization */
    const keys: any = {
      elementType: 'elementType',
    };

    /** Create new item container */
    this.newItem = new JointGraphNodeImpl();

    /** Set properties for the newly created item */
    (this.newItem as JointGraphNodeImpl).setId(`0-${type}-${GraphNodeImpl.instance.size}`);
    (this.newItem as JointGraphNodeImpl).setType(type.toString());
    (this.newItem as JointGraphNodeImpl).setSize((NODE_TYPE as any)[type].size);
    (this.newItem as JointGraphNodeImpl).setMarkup((NODE_TYPE as any)[type].markup);
    (this.newItem as JointGraphNodeImpl).setAttr((NODE_TYPE as any)[type].attr);
    (this.newItem as JointGraphNodeImpl).setImageIcon((NODE_TYPE as any)[type].image);

    /** No need to set label for start and stop node */
    if (!(type.toString() === 'start' || type.toString() === 'stop')) {
      (this.newItem as JointGraphNodeImpl).setLabel(`New Node ${GraphNodeImpl.instance.size}`);
    }

    /** Set dragging state to true */
    this.dragging = true;
  }

  public moveItem(e: MouseEvent): void {

    /**
     * Only move item when current state is dragging and
     * newItem container has been created and initialized with default value
     */
    if (this.dragging && this.newItem !== null) {

      /** Capture svgPoint from MouseEvent */
      const svgPoint = (this.jointPages.get(this.activePage as string)!.getPaper().svg as SVGSVGElement).createSVGPoint();
      svgPoint.x = e.offsetX;
      svgPoint.y = e.offsetY;

      /** Transform svgPoint to joint.js paper matrix */
      const pointTransformed = svgPoint.matrixTransform(this.jointPages.get(this.activePage as string)!.getPaper().viewport.getCTM()!.inverse());

      /** Set position according to the transformed point captured from MouseEvent */
      (this.newItem as JointGraphNodeImpl).setPosition({
        x: pointTransformed.x,
        y: pointTransformed.y,
      });

      /** Render newItem */
      (this.newItem as JointGraphNodeImpl).render(this.jointPages.get(this.activePage as string)!.getGraph());

      /** Listen to keydown event to check esc button */
      window.addEventListener('keydown', this.cancelCreateItem);
    }
  }

  public cancelCreateItem(e: KeyboardEvent): any {

    /** If esc key was pressed */
    if (e.key === 'Escape') {

      /**
       * And if current state is dragging and
       * newItem container has been created and initialized
       */
      if (this.dragging && (this.newItem as JointGraphNodeImpl) !== null) {

        /** Set state drag to false */
        this.dragging = false;

        /** Remove node from joint.js canvas */
        (this.newItem as JointGraphNodeImpl).getNode().remove();

        /** Set newItem container to null */
        this.newItem = null;
      }
    }
  }

  public saveItem(e: MouseEvent): void {

    /** Set drag state to false */
    this.dragging = false;

    /** If newItem container contains data */
    if (this.newItem !== null) {

      /** Add newItem to graphModule */
      graphModule.addPageNode(
        {
          page: graphModule.graph.getPages()!.get(this.activePage as string) as GraphPage,
          node: this.newItem,
        },
      );

      GraphSubject.update(graphModule.graph);

      /** Add newItem to GraphNodeInstance */
      GraphNodeImpl.instance.set(this.newItem.getId() as string, (NODE_ENUMS.NODE_TYPE as any)[this.newItem.getType() as string].deserialize(this.newItem));

      /** Set newItem container variable to null */
      this.newItem = null;

      /** Remove keyboard event listener for newItem */
      window.removeEventListener('keydown', this.cancelCreateItem);
    }
  }

  public handleCanvasMouseDown(e: MouseEvent): void {
    // this.destroySelector(e);
    // this.createSelector(e);
  }

  public handleCanvasMouseMove(e: MouseEvent): void {
    this.moveItem(e);
    // this.moveSelector(e);
  }

  public handleCanvasMouseUp(e: MouseEvent): void {
    this.saveItem(e);
    // this.destroySelector(e);
  }

  /** @Override */
  public async mounted(): Promise<void> {
    try {

      this.$observables.graph.subscribe((graph: Graph) => {
        graphModule.setGraph(graph);
      });

      // Loop the model page
      for (const [key, value] of graphModule.graph.getPages() as Map<string, GraphPage>) {
        const jointPage: JointGraphPageImpl = new JointGraphPageImpl();
        const canvasWidth: number = $('#canvas-container').innerWidth();
        const canvasHeight: number = $('#canvas-container').innerHeight();

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

        // set active page
        this.activePage = jointPage.getId() as string;

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
            currentElement.findView(jointPage.getPaper()).highlight();
          },
          'element:pointerdblclick': (elementView: joint.dia.ElementView) => {
            resetAll(jointPage.getPaper());
            const currentElement = elementView.model;
            const currentElementType = currentElement.attributes.type;
            currentElement.findView(jointPage.getPaper()).highlight();
          },
          'element:contextmenu': (elementView: joint.dia.ElementView) => {
            resetAll(jointPage.getPaper());
            const currentElement = elementView.model;
            const currentElementType = currentElement.attributes.type;
            const currentElementNodeId = currentElement.attributes.nodeId;
            currentElement.findView(jointPage.getPaper()).highlight();
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

        this.jointPages.set(jointPage.getId() as string, jointPage);

        function resetAll(_paper: joint.dia.Paper) {
          /** Reset all elements in the paper */
          _paper.findViewsInArea(jointPage.getPaper().getArea()).forEach((cell) => { cell.unhighlight(); });
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
