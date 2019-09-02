// Vue & Libraries
import joint from 'jointjs';
import SvgPanZoom from 'svg-pan-zoom';
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Interfaces
import { GraphConnector } from '@/iochord/ips/common/graph/ism/interfaces/GraphConnector';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/classes/JointGraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/classes/JointGraphConnectorImpl';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';
import { GraphConnectorImpl } from '@/iochord/ips/common/graph/ism/classes/GraphConnectorImpl';

// Vuex & Rxjs

/** Graph */
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';
import { ARC_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/ARC';

// Fetch module from stores
const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

@Component({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class ConnectorMixin extends BaseComponent {
  public newConnector?: JointGraphConnectorImpl;
  public source?: GraphNode;
  public target?: GraphNode;

  public createConnector(e: MouseEvent): void {
    document.body.style.cursor = 'crosshair';

    // Create new item
    this.newConnector = new JointGraphConnectorImpl();

    // Set properties for the newly created item
    this.newConnector.setId(`0-${GraphConnectorImpl.instance.size}`);
    this.newConnector.setType('connector');
    this.newConnector.setAttr(ARC_TYPE.connector.attr);

    graphModule.setNewItem(this.newConnector as JointGraphConnectorImpl);

    // Disable pan and zoom
    const panAndZoom: SvgPanZoom.Instance = SvgPanZoom('#canvas svg');
    panAndZoom.disablePan();
    panAndZoom.disableZoom();

    editorState.setDrawing(true);

    // Listen to keydown event to check esc button
    window.addEventListener('keydown', this.cancelCreateConnector);
  }

  public setSourceNode(activePage: JointGraphPageImpl, node: joint.dia.Element) {
    const nodeId = (node.attributes.nodeId as string).split('-')[2];
    this.source = graphModule.pageNode(activePage, nodeId) as GraphNode;

    (graphModule.newItem as JointGraphConnectorImpl).setSource(this.source);
    const blankPointTarget = new joint.g.Point(node.position().x, node.position().y);

    (graphModule.newItem as JointGraphConnectorImpl).render(activePage.getGraph(), blankPointTarget);
  }

  public moveToTargetNode(e: MouseEvent, activePage: JointGraphPageImpl) {
    if (graphModule.newItem && this.source) {

      /** Capture svgPoint from MouseEvent */
      const svgPoint = (activePage.getPaper().svg as SVGSVGElement).createSVGPoint();
      svgPoint.x = e.offsetX;
      svgPoint.y = e.offsetY;

      /** Transform svgPoint to joint.js paper matrix */
      const pointTransformed = svgPoint.matrixTransform(activePage.getPaper().viewport.getCTM()!.inverse());

      const blankPointTarget = new joint.g.Point(pointTransformed.x, pointTransformed.y);

      (graphModule.newItem as JointGraphConnectorImpl).render(activePage.getGraph(), blankPointTarget);
    }
  }

  public setTargetNode(activePage: JointGraphPageImpl, node: joint.dia.Element) {
    editorState.setDrawing(false);
    document.body.style.cursor = 'default';

    const nodeId = (node.attributes.nodeId as string).split('-')[2];
    this.target = graphModule.pageNode(activePage, nodeId) as GraphNode;

    (graphModule.newItem as JointGraphConnectorImpl).setTarget(this.target);

    // Render newItem
    (graphModule.newItem as JointGraphConnectorImpl).render(activePage.getGraph());

    // Construct newItem according to its type
    const newItem = new GraphConnectorImpl();
    newItem.setId((graphModule.newItem as JointGraphConnectorImpl).getId() as string);
    newItem.setType((graphModule.newItem as JointGraphConnectorImpl).getType() as string);
    newItem.setSource(this.source as GraphNode);
    newItem.setTarget(this.target as GraphNode);

    // Add node to Vuex GraphModule
    graphModule.addPageArc(
      {
        page: activePage,
        arc: newItem as GraphConnector,
      },
    );

    // Update local instance
    GraphConnectorImpl.instance.set(newItem.getId() as string, newItem);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Set container to null
    graphModule.setNewItem(null);
    this.source = undefined;
    this.target = undefined;
    this.newConnector = undefined;

    // Pop up toast
    ($('body') as any).toast({
      position: 'bottom right',
      class: 'success',
      className: {
        toast: 'ui message',
      },
      showIcon: 'blue long arrow alternate right',
      message: `${(newItem as GraphConnector).getId()} has been created.`,
      newestOnTop: true,
    });

    // Enable the toolbar again
    $('.sidebar.component .ui.basic.button.item').removeClass('disabled');

    // Remove event listener
    window.removeEventListener('keydown', this.cancelCreateConnector);
  }


  public cancelCreateConnector(e: KeyboardEvent): void {
    if (e.key === 'Escape') {
      if (editorState.drawing) {
        document.body.style.cursor = 'default';

        // Set drawing state to false
        editorState.setDrawing(false);

        // Remove link from joint.js canvas
        (graphModule.newItem as JointGraphConnectorImpl).getConnector().remove();

        // Remove link from GraphModule temporary container
        graphModule.setNewItem(null);

        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'info',
          className: {
            toast: 'ui message',
          },
          message: `Canceling connector creation.`,
          newestOnTop: true,
        });

        // Enable the toolbar again
        $('.sidebar.component .ui.basic.button.item').removeClass('disabled');

        // Remove event listener
        window.removeEventListener('keydown', this.cancelCreateConnector);
      }
    }
  }
}
