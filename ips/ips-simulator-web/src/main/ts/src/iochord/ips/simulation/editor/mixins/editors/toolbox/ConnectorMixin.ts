// Vue & Libraries
import joint from 'jointjs';
import SvgPanZoom from 'svg-pan-zoom';
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Interfaces
import { GraphConnector } from '@/iochord/ips/common/graphs/ism/interfaces/GraphConnector';
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphConnectorImpl';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphConnectorImpl } from '@/iochord/ips/common/graphs/ism/class/GraphConnectorImpl';

// Vuex & Rxjs

/** Graph */
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';
import { ARC_TYPE } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/enums/ARC';
import { NODE_TYPE } from '@/iochord/ips/common/graphs/ism/enums/NODE';
import { GraphNodeImpl } from '@/iochord/ips/common/graphs/ism/class/GraphNodeImpl';

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

/**
 * The connector mixin class
 *
 * @export
 * @class ConnectorMixin
 * @extends {BaseComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class ConnectorMixin extends BaseComponent {

  /**
   * New connector object
   *
   * @type {JointGraphConnectorImpl}
   * @memberof ConnectorMixin
   */
  public newConnector?: JointGraphConnectorImpl;

  /**
   * The source of the connector.
   *
   * @type {GraphNode}
   * @memberof ConnectorMixin
   */
  public source?: GraphNode;

  /**
   * The target of the connector.
   *
   * @type {GraphNode}
   * @memberof ConnectorMixin
   */
  public target?: GraphNode;

  /**
   * Create new connector object.
   *
   * @param {MouseEvent} e
   * @memberof ConnectorMixin
   */
  public createConnector(e: MouseEvent): void {
    document.body.style.cursor = 'crosshair';

    // Create new item
    this.newConnector = new JointGraphConnectorImpl();

    // Set properties for the newly created item
    this.newConnector.setId(`0-${GraphConnectorImpl.instance.size() + Math.ceil(Math.random())}`);
    this.newConnector.setType('connector');
    this.newConnector.setAttr(ARC_TYPE.connector.attr);

    graphModule.setNewItem(this.newConnector as JointGraphConnectorImpl);

    // Disable pan and zoom
    const panAndZoom: SvgPanZoom.Instance = SvgPanZoom('#canvas svg');
    panAndZoom.disablePan();
    panAndZoom.disableZoom();

    // Set current edtiorState to 'drawing mode'
    editorState.setDrawing(true);

    // Listen to keydown event to check esc button
    window.addEventListener('keydown', this.cancelCreateConnector);
  }

  /**
   * Assigns the source node to the connector.
   *
   * @param {JointGraphPageImpl} activePage
   * @param {joint.dia.Element} node
   * @memberof ConnectorMixin
   */
  public setSourceNode(activePage: JointGraphPageImpl, node: joint.dia.Element) {

    // If it is a node instance
    if (node.attributes.nodeId) {
      const source = GraphNodeImpl.instance.get(node.attributes.nodeId);
      const error = (source as any).validateOutputNodes();

      // Rule checking for node
      if (error === null) {

        // Set source node
        this.source = source;

        // Set source ref
        (graphModule.newItem as JointGraphConnectorImpl).setSourceRef(this.source!.getId() as string);

        // Set target ref as blank point
        const blankPointTarget = new joint.g.Point(node.position().x, node.position().y);

        // Render conenctor to the canvas
        (graphModule.newItem as JointGraphConnectorImpl).render(activePage.getGraph(), blankPointTarget);

      } else {

        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'error',
          className: {
            toast: 'ui message',
          },
          message: error.message,
          newestOnTop: true,
        });
      }
    } else {

      // Pop up toast
      ($('body') as any).toast({
        position: 'bottom right',
        class: 'error',
        className: {
          toast: 'ui message',
        },
        message: `Cannot create connector. Source is not an instance of node.`,
        newestOnTop: true,
      });
    }
  }

  /**
   * Draw connector to the canvas.
   *
   * @param {MouseEvent} e
   * @param {JointGraphPageImpl} activePage
   * @memberof ConnectorMixin
   */
  public moveToTargetNode(e: MouseEvent, activePage: JointGraphPageImpl) {
    if (graphModule.newItem && this.source) {

      // Capture svgPoint from MouseEvent
      const svgPoint = (activePage.getPaper().svg as SVGSVGElement).createSVGPoint();
      svgPoint.x = e.offsetX;
      svgPoint.y = e.offsetY;

      // Transform svgPoint to joint.js paper matrix
      const pointTransformed = svgPoint.matrixTransform(activePage.getPaper().viewport.getCTM()!.inverse());

      // Blank point target
      const blankPointTarget = new joint.g.Point(pointTransformed.x, pointTransformed.y);

      (graphModule.newItem as JointGraphConnectorImpl).render(activePage.getGraph(), blankPointTarget);
    }
  }

  /**
   * Assigns target node to the connector.
   *
   * @param {JointGraphPageImpl} activePage
   * @param {joint.dia.Element} node
   * @memberof ConnectorMixin
   */
  public setTargetNode(activePage: JointGraphPageImpl, node: joint.dia.Element) {

    // If it is a node instance
    if (node.attributes.nodeId) {

      const target = GraphNodeImpl.instance.get(node.attributes.nodeId);
      const error = (target as any).validateInputNodes();

      if (error === null) {

        // Reset editor state
        editorState.setDrawing(false);

        // Reset currsor
        document.body.style.cursor = 'default';

        // Set target node
        this.target = target;

        // Set target ref
        (graphModule.newItem as JointGraphConnectorImpl).setTargetRef(this.target.getId() as string);

        // Render newItem
        (graphModule.newItem as JointGraphConnectorImpl).render(activePage.getGraph());

        // Construct newItem according to its type
        const newItem = new GraphConnectorImpl();
        newItem.setId((graphModule.newItem as JointGraphConnectorImpl).getId() as string);
        newItem.setType((graphModule.newItem as JointGraphConnectorImpl).getType() as string);
        newItem.setSourceRef(this.source!.getId() as string);
        newItem.setTargetRef(this.target!.getId() as string);

        // Add node to Vuex GraphModule
        graphModule.addPageConnector(
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

      } else {
        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'error',
          className: {
            toast: 'ui message',
          },
          message: error.message,
          newestOnTop: true,
        });
      }

    } else {

      // Pop up toast
      ($('body') as any).toast({
        position: 'bottom right',
        class: 'error',
        className: {
          toast: 'ui message',
        },
        message: `Cannot create connector. Target is not an instance of node.`,
        newestOnTop: true,
      });
    }
  }

  /**
   * Delete the connector object.
   *
   * @param {JointGraphPageImpl} activePage
   * @param {joint.dia.Element} cell
   * @memberof ConnectorMixin
   */
  public deleteConnector(activePage: JointGraphPageImpl, cell: joint.dia.Element) {

    // Check if cell is a connector object
    if (cell.isLink()) {
      const connectorId = cell.attributes.connectorId;
      const connector = graphModule.pageConnector(activePage as JointGraphPageImpl, connectorId) as GraphConnector;

      // If connector exists
      if (connector) {
        graphModule.deletePageConnector({
          page: activePage as JointGraphPageImpl,
          arc: connector,
        });

        // Update local instance
        GraphConnectorImpl.instance.delete(connectorId as string);

        // Update the rxjs observable
        GraphSubject.update(graphModule.graph);

        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'info',
          className: {
            toast: 'ui message',
          },
          message: `Successfully remove a connector`,
          newestOnTop: true,
        });
      }
    }
  }

  /**
   * Cancel create connector object.
   *
   * @param {KeyboardEvent} e
   * @memberof ConnectorMixin
   */
  public cancelCreateConnector(e: KeyboardEvent): void {

    // If escape key was pressed
    if (e.key === 'Escape') {

      // And if current editor state is drawing
      if (editorState.drawing) {

        // Reset cursor
        document.body.style.cursor = 'default';

        // Set drawing state to false
        editorState.setDrawing(false);

        // Remove link from joint.js canvas
        (graphModule.newItem as JointGraphConnectorImpl).getConnector().remove();

        // Remove link from GraphModule temporary container
        graphModule.setNewItem(null);
        this.source = undefined;
        this.target = undefined;

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
