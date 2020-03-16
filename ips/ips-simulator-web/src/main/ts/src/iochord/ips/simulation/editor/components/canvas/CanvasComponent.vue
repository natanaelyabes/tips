<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="canvas component">
    <div class="editor canvas ui basic segment">

      <!-- Canvas to render graph -->
      <div id="canvas"
        @keydown.esc = "handleEscapeButton($event)"
        @mousedown = "handleCanvasMouseDown($event)"
        @mousemove = "handleCanvasMouseMove($event)"
        @mouseup   = "handleCanvasMouseUp($event)" />
    </div>

    <!-- Data Modals -->
    <DataTableModal ref="datatable" id ="datatable" />
    <ObjectTypeDataModal ref="objecttype" id="objecttype"/>
    <FunctionDataModal ref="function" id="function"/>
    <GeneratorDataModal ref="generator" id="generator"/>
    <QueueDataModal ref="queue" id="queue"/>
    <ResourceDataModal ref="resource" id="resource"/>

    <!-- Node Modals -->
    <StartNodeModal ref="start" id="start"/>
    <BranchNodeModal ref="branch" id="branch"/>
    <ActivityNodeModal ref="activity" id="activity"/>
    <StopNodeModal ref="stop" id="stop"/>
    <DeleteModal ref="delete" id="delete"/>
  </div>
</template>

<style scoped>
.canvas.component {
  height: calc(100% - 60px);
}
.canvas.component .editor.canvas {
  width: 100%;
  height: 100%;

  padding: 0;
  margin: 0;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Prop, Mixins, Vue } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// SVG Pan and Zoom
import SvgPanZoom from 'svg-pan-zoom';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphControlImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/ips/common/graphs/ism/class/GraphImpl';
import { GraphPageImpl } from '@/iochord/ips/common/graphs/ism/class/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import JointJsRenderer from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/class/JointJsRenderer';

// Interfaces
import { Graph } from '@/iochord/ips/common/graphs/ism/interfaces/Graph';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphConnector } from '@/iochord/ips/common/graphs/ism/interfaces/GraphConnector';
import { GraphData } from '@/iochord/ips/common/graphs/ism/interfaces/GraphData';
import { GraphElement } from '@/iochord/ips/common/graphs/ism/interfaces/GraphElement';
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';
import { GraphStartEventNode } from '@/iochord/ips/common/graphs/ism/interfaces/components/GraphStartEventNode';
import { Modal } from '@/iochord/ips/simulation/editor/interfaces/Modal';

// Enums
import { NODE_TYPE } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/enums/NODE';
import { ARC_TYPE } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/enums/ARC';
import { MODAL_TYPE } from '../../enums/MODAL';

// Components
import StartNodeModal from '@/iochord/ips/simulation/editor/components/modals/StartNodeModal.vue';
import ActivityNodeModal from '@/iochord/ips/simulation/editor/components/modals/ActivityNodeModal.vue';
import BranchNodeModal from '@/iochord/ips/simulation/editor/components/modals/BranchNodeModal.vue';
import StopNodeModal from '@/iochord/ips/simulation/editor/components/modals/StopNodeModal.vue';
import DataTableModal from '@/iochord/ips/simulation/editor/components/modals/DataTableModal.vue';
import ObjectTypeDataModal from '@/iochord/ips/simulation/editor/components/modals/ObjectTypeDataModal.vue';
import ConfigurationModal from '@/iochord/ips/simulation/editor/components/modals/ConfigurationModal.vue';
import ControlModal from '@/iochord/ips/simulation/editor/components/modals/ControlModal.vue';
import FunctionDataModal from '@/iochord/ips/simulation/editor/components/modals/FunctionDataModal.vue';
import GeneratorDataModal from '@/iochord/ips/simulation/editor/components/modals/GeneratorDataModal.vue';
import QueueDataModal from '@/iochord/ips/simulation/editor/components/modals/QueueDataModal.vue';
import ResourceDataModal from '@/iochord/ips/simulation/editor/components/modals/ResourceDataModal.vue';
import DeleteModal from '@/iochord/ips/simulation/editor/components/modals/DeleteModal.vue';

// Mixins
import CanvasMixin from '../../mixins/editors/CanvasMixin';

// JQuery Handler
declare const $: any;

// Vuex Module
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import EditorState from '../../stores/editors/EditorState';
import { GraphActivityNode } from '@/iochord/ips/common/graphs/ism/interfaces/components/GraphActivityNode';
import { ACTIVITY_TYPE } from '@/iochord/ips/common/graphs/ism/enums/ACTIVITY';
import { GraphDataResource } from '@/iochord/ips/common/graphs/ism/interfaces/components/GraphDataResource';
import { DISTRIBUTION_TYPE } from '@/iochord/ips/common/graphs/ism/enums/DISTRIBUTION';
import { TIME_UNIT } from '@/iochord/ips/common/graphs/ism/enums/TIME_UNIT';
import { GraphDataQueue } from '@/iochord/ips/common/graphs/ism/interfaces/components/GraphDataQueue';
import { GraphBranchNode } from '@/iochord/ips/common/graphs/ism/interfaces/components/GraphBranchNode';
import { BRANCH_GATE, BRANCH_TYPE, BRANCH_RULE } from '@/iochord/ips/common/graphs/ism/enums/BRANCH';
import { GraphConnectorImpl } from '../../../../common/graphs/ism/class/GraphConnectorImpl';
import { TSMap } from 'typescript-map';
import { DeletableModal } from '../../interfaces/DeletableModal';

// Vuex module instance
const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

@Component({
  components: {
    ActivityNodeModal,
    BranchNodeModal,
    CanvasComponent,
    StartNodeModal,
    StopNodeModal,
    DataTableModal,
    ObjectTypeDataModal,
    ConfigurationModal,
    ControlModal,
    FunctionDataModal,
    GeneratorDataModal,
    QueueDataModal,
    ResourceDataModal,
    DeleteModal,
  },
})

/**
 * The canvas component.
 *
 * @export
 * @class CanvasComponent
 * @extends {Mixins(BaseComponent, CanvasMixin)}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class CanvasComponent extends Mixins(BaseComponent, CanvasMixin) {

  /**
   * Response from the web service as the graph JSON object.
   *
   * @type {Graph}
   * @memberof CanvasComponent
   */
  @Prop() public response?: Graph;

  /**
   * Boolean variable to evaluate if the graph object is a process model. False otherwise.
   *
   * @type {boolean}
   * @memberof CanvasComponent
   */
  @Prop() public isProcessModel?: boolean;

  /**
   * Boolean flag to enable/disable canvas.
   *
   * @type {boolean}
   * @memberof CanvasComponent
   */
  @Prop({ default: false }) public isDisabled?: boolean;

  /**
   * SVG Pan and Zoom instance.
   *
   * @type {SvgPanZoom.Instance}
   * @memberof CanvasComponent
   */
  public panAndZoom?: SvgPanZoom.Instance;

  /**
   * List of highlighted elements.
   *
   * @type {TSMap<string, joint.dia.Element>}
   * @memberof CanvasComponent
   */
  public highlightedElement: TSMap<string, joint.dia.Element> = new TSMap<string, joint.dia.Element>();

  /**
   * Boolean variable to flag whether a shift key is being pressed. False otherwise.
   *
   * @type {boolean}
   * @memberof CanvasComponent
   */
  public isShiftKey: boolean = false;

  /**
   * Vue mounted lifecycle.
   *
   * @memberof CanvasComponent
   */
  public mounted(): void {
    this.loadGraph();
    this.$forceUpdate();
  }

  /**
   * Load graph into canvas.
   *
   * @memberof CanvasComponent
   */
  public loadGraph(): void {
    try {
      this.graph = this.response as Graph;

      const renderer = new JointJsRenderer(
        this.graph,
        this.activePage as GraphPage,
        this.currentSelectedElement as GraphNode,
        this.isProcessModel as boolean,
      );

      this.panAndZoom = renderer.panAndZoom;

      renderer.jointPages.forEach((jointPage: JointGraphPageImpl) => {
        this.whileListenToEvents(jointPage);
      });

      if (this.graph)
        this.activePage = this.graph.getPages()!.get('0');

      if (this.activePage)
        this.activePage = renderer.activeJointPage(
          this.activePage.getId() as string) as JointGraphPageImpl;

    } catch (e) {
      // console.error(e);
    }
  }

  /**
   * Listen to Joint.js canvas events.
   *
   * @private
   * @param {JointGraphPageImpl} jointPage
   * @memberof CanvasComponent
   */
  private whileListenToEvents(jointPage: JointGraphPageImpl): void {

    const resetAll = (paper: joint.dia.Paper) => {
      jointPage.getGraph().getElements().forEach((element: joint.dia.Element) => {
        const elementView = paper.findViewByModel(element);
        if (!this.isShiftKey)
          elementView.unhighlight();
      });
    };

    document.addEventListener('keydown', (e) => {
      switch (e.which) {
        case 8: case 46:
          if (this.highlightedElement.length > 0) {
            this.highlightedElement.forEach((element) => {
              (this.$refs['delete'] as DeletableModal).populateNode({
                id: element.attributes.nodeId || element.attributes.dataId,
                label: element.attributes.attrs.label.text,
                type: element.attributes.type,
                category: element.attributes.nodeId ? 'node' : 'data',
              });
            });
            $(`#delete`).modal('setting', 'transition', 'fade up')
              .modal({
                onApprove: () => { this.highlightedElement.forEach((element) => removeNode(element)); },
                onDeny:    () => { (this.$refs['delete'] as DeletableModal).reset(); }})
              .modal('show');
          }
          break;
        case 16: this.isShiftKey = e.shiftKey; break;
      }
    });

    document.addEventListener('keyup', (e) =>
      this.isShiftKey = false);

    const removeNode = (currentElement: joint.dia.Element) => {
      const links = jointPage.getGraph().getConnectedLinks(currentElement);
      jointPage.getNodes()!.delete(currentElement.attributes.nodeId);
      currentElement.remove();
      links.forEach((link) => {
        (jointPage.getConnectors() as TSMap<string, GraphConnector>).delete(link.attributes.connectorId);
        link.remove();
      });
    };

    jointPage.getGraph().on({
      remove: (cell: joint.dia.Element) => {
        this.deleteConnector(this.activePage as JointGraphPageImpl, cell);
        this.deleteNode(this.activePage as JointGraphPageImpl, cell);
        this.deleteData(this.activePage as JointGraphPageImpl, cell);
        this.source = undefined;
        this.target = undefined;
      },
    });

    jointPage.getPaper().on({
      'blank:pointerdown': (elementView: joint.dia.ElementView) => {
        resetAll(jointPage.getPaper());
        if (editorState.drawing && this.panAndZoom) {
          this.panAndZoom.disablePan();
          this.panAndZoom.disableZoom();
          $('body').toast({
            position: 'bottom right',
            class: 'error',
            className: { toast: 'ui message' },
            message: 'Select any node to draw connector.',
            newestOnTop: true,
          });
          document.body.style.cursor = 'crosshair';
        } else if (!editorState.drawing && this.panAndZoom) {
          this.panAndZoom.enablePan();
          this.panAndZoom.enableZoom();
          document.body.style.cursor = 'grabbing';
        }
      },

      'element:pointerup blank:pointerup': (elementView: joint.dia.ElementView) => {
        (this.panAndZoom as SvgPanZoom.Instance).disablePan();
        if (editorState.drawing) {
          document.body.style.cursor = 'crosshair';
        } else {
          document.body.style.cursor = 'default';
        }
      },

      'element:mouseover': (elementView: joint.dia.ElementView) => {
        if (editorState.drawing) {
          resetAll(jointPage.getPaper());
          const currentElement = elementView.model;
          currentElement.findView(jointPage.getPaper()).highlight();
        }
      },

      'element:mouseout': (elementView: joint.dia.ElementView) => {
        if (editorState.drawing) {
          resetAll(jointPage.getPaper());
          document.body.style.cursor = 'crosshair';
        } else {
          document.body.style.cursor = 'default';
        }
      },

      'element:pointerdown': (elementView: joint.dia.ElementView) => {
        resetAll(jointPage.getPaper());
        const currentElement = elementView.model;
        currentElement.findView(jointPage.getPaper()).highlight();
        while (editorState.drawing) {
          jointPage.getPaper().setInteractivity(false);
          if (!this.source) {
            this.setSourceNode(this.activePage as JointGraphPageImpl, currentElement);
            break;
          }

          if (this.source && !this.target) {
            this.setTargetNode(this.activePage as JointGraphPageImpl, currentElement);
            break;
          }
        }
        jointPage.getPaper().setInteractivity(true);
      },

      'element:pointerclick': (elementView: joint.dia.ElementView) => {
        resetAll(jointPage.getPaper());
        const currentElement = elementView.model;
        currentElement.findView(jointPage.getPaper()).highlight();
      },

      'element:pointerdblclick': (elementView: joint.dia.ElementView) => {
        if (this.isDisabled) return;

        resetAll(jointPage.getPaper());

        const currentElement = elementView.model;
        const currentElementType: string = currentElement.attributes.type;
        const currentElementCategory: string = currentElement.attributes.category;

        let property;
        let currentElementId: string;

        if (currentElementCategory === 'node') {
          currentElementId = currentElement.attributes.nodeId.split('-')[2];
          property = graphModule.pageNode(jointPage, currentElement.attributes.nodeId);
        } else if (currentElementCategory === 'data') {
          currentElementId = currentElement.attributes.dataId.split('-')[2];
          property = graphModule.pageDatum(jointPage, currentElement.attributes.dataId);
        }

        if (!this.isProcessModel) {
          (this.$refs[currentElementType] as Modal<JointGraphPageImpl, typeof property>).populateProperties(jointPage, property);
          $(`#${currentElementType}`).modal('setting', 'transition', 'fade up').modal('show');
        }
      },

      'cell:highlight': (elementView: joint.dia.ElementView) => {
        const currentElement = elementView.model;
        this.highlightedElement.set(currentElement.id as string, currentElement);
      },

      'cell:unhighlight': (elementView: joint.dia.ElementView) => {
        const currentElement = elementView.model;
        this.highlightedElement.delete(currentElement.id as string);
      },
    });
  }
}
</script>
