// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import SvgPanZoom from 'svg-pan-zoom';

// Interfaces
import { JointGraphPageImpl } from './../../../../../common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphPageImpl';
import { JointGraphConnectorImpl } from './../../../../../common/graph/sbpnet/rendering-engine/joint/shapes/classes/JointGraphConnectorImpl';

// Classes
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

// Vuex & Rxjs

/** Graph */
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';
import { GraphConnectorImpl } from '@/iochord/chdsr/common/graph/sbpnet/classes/GraphConnectorImpl';
import { GraphNode } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphNode';

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
  public source?: GraphNode;
  public target?: GraphNode;

  public createConnector(e: MouseEvent): void {
    document.body.style.cursor = 'crosshair';

    // Create new item
    const newItem = new JointGraphConnectorImpl();

    // Set properties for the newly created item
    newItem.setId(`0-${GraphConnectorImpl.instance.size}`);
    newItem.setType('connector');

    graphModule.setNewItem(newItem as JointGraphConnectorImpl);

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
  }

  public setTargetNode(node: joint.dia.Element) {
    console.log(node);
  }

  public cancelCreateConnector(e: KeyboardEvent): void {
    if (e.key === 'Escape') {
      if (editorState.drawing) {
        document.body.style.cursor = 'auto';
        editorState.setDrawing(false);
        $('.sidebar.component .ui.basic.button.item').removeClass('disabled');
        window.removeEventListener('keydown', this.cancelCreateConnector);
      }
    }
  }
}
