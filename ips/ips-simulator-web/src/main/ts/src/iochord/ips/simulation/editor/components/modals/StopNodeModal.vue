<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny stop modal">
    <i class="close icon"></i>
    <div id="stop_modal_title" class="header">
      <h3 class="ui green header">Stop</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" v-model="label" id="stop_txt_label">
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="actions">
      <div @click = "saveProperties(page, properties)" class="ui positive button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { Modal } from '../../interfaces/Modal';
import { GraphStopEventNodeImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphStopEventNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { TSMap } from 'typescript-map';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';
import { GraphNodeImpl } from '@/iochord/ips/common/graphs/ism/class/GraphNodeImpl';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';

declare const $: any;

// Vuex
const graphModule = getModule(GraphModule);

/**
 * The stop node modal.
 *
 * @export
 * @class StopNodeModal
 * @extends {SemanticComponent}
 * @implements {Modal<JointGraphPageImpl, GraphStopEventNodeImpl>}
 */
@Component

export default class StopNodeModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphStopEventNodeImpl> {

  /**
   * The stop event node object.
   *
   * @private
   * @type {GraphStopEventNodeImpl}
   * @memberof StopNodeModal
   */
  private properties!: GraphStopEventNodeImpl;

  /**
   * The graph page object.
   *
   * @private
   * @type {JointGraphPageImpl}
   * @memberof StopNodeModal
   */
  private page!: JointGraphPageImpl;

  /**
   * The label of the stop event node.
   *
   * @private
   * @type {string}
   * @memberof StopNodeModal
   */
  private label: string = '';

  /**
   * Indicates that the statistics of stop modal is being reported. False otherwise.
   *
   * @private
   * @type {boolean}
   * @memberof StopNodeModal
   */
  private report: boolean = false;

  /**
   * Assigns the properties of stop event node to the stop event node modal properties.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphStopEventNodeImpl} object
   * @memberof StopNodeModal
   */
  public populateProperties(page: JointGraphPageImpl, object: GraphStopEventNodeImpl): void {

    // Object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.report = object.isReportStatistics() as boolean;
  }

  /**
   * Store the properties into stop event node object, commit to vuex store.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphStopEventNodeImpl} object
   * @memberof StopNodeModal
   */
  public saveProperties(page: JointGraphPageImpl, object: GraphStopEventNodeImpl): void {
    const nodePageId = (object.getId() as string).split('-')[0];
    const nodePage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(nodePageId);
    const node: GraphStopEventNodeImpl = (page.getNodes() as TSMap<string, GraphNode>).get(object.getId() as string) as GraphStopEventNodeImpl;

    // Save properties
    node.setLabel(this.label);
    node.setReportStatistics(this.report);

    // Change label of the renderer node
    page.getGraph().getCells().map((cell: joint.dia.Cell) => {
      if (cell.attributes.nodeId === object.getId()) {
        cell.attr({
          label: {
            text: this.label,
          },
        });
      }
    });

    // Update local instance
    GraphNodeImpl.instance.set(node.getId() as string, GraphStopEventNodeImpl.deserialize(node) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Pop up toast
    ($('body') as any).toast({
      position: 'bottom right',
      class: 'info',
      className: { toast: 'ui message' },
      message: `${object.getId()} properties have been saved`,
      newestOnTop: true,
    });
  }
}
</script>
