<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="ui tiny start modal">
    <i class="close icon"></i>
    <div id="start_modal_title" class="header">
      <h3 class="ui green header">Start</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">
              Label
            </div>
            <div class="thirteen wide column">
              <input type="text" v-model="label" id="start_txt_label" />
            </div>
          </div>
          <div class="row">
            <div class="three wide column">Generator</div>
            <div class="thirteen wide column">
              <select v-model="generator" id="start_txtgen" class="ui fluid search dropdown">
                <option v-for="generator in generators" :key="generator.getId()" :value="generator.getId()">{{generator.getLabel()}} ({{generator.getId()}})</option>
              </select>
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

<style>
/**
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { GraphPageImpl } from '@/iochord/ips/common/graphs/ism/class/GraphPageImpl';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphData } from '@/iochord/ips/common/graphs/ism/interfaces/GraphData';
import { Modal } from '../../interfaces/Modal';
import { GraphStartEventNodeImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphStartEventNodeImpl';
import { GraphNodeImpl } from '@/iochord/ips/common/graphs/ism/class/GraphNodeImpl';

// JQuery
declare const $: any;

import { TSMap } from 'typescript-map';
import { GraphNode } from '../../../../common/graphs/ism/interfaces/GraphNode';
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';

// Vuex
const graphModule = getModule(GraphModule);

@Component

/**
 * The start node modal.
 *
 * @export
 * @class StartNodeModal
 * @extends {SemanticComponent}
 * @implements {Modal<JointGraphPageImpl, GraphStartEventNodeImpl>}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class StartNodeModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphStartEventNodeImpl> {

  /**
   * The graph start event node object.
   *
   * @private
   * @type {GraphStartEventNodeImpl}
   * @memberof StartNodeModal
   */
  private properties!: GraphStartEventNodeImpl;

  /**
   * The graph page object.
   *
   * @private
   * @type {JointGraphPageImpl}
   * @memberof StartNodeModal
   */
  private page!: JointGraphPageImpl;

  /**
   * The label of start event node.
   *
   * @private
   * @memberof StartNodeModal
   */
  private label = '';

  /**
   * The generator data object as string reference.
   *
   * @private
   * @memberof StartNodeModal
   */
  private generator = '';

  /**
   * Assigns the properties of start event node to the start event node modal properties.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphStartEventNodeImpl} object
   * @memberof StartNodeModal
   */
  public populateProperties(page: JointGraphPageImpl, object: GraphStartEventNodeImpl): void {

    $('#start_txtgen').dropdown({ clearable: true });

    // Object properties
    this.properties = object;

    // Page Renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.generator = object.getGeneratorRef() as string;

    // Initialize dropdown with default value
    $('#start_txtgen')
      .dropdown('set selected', this.generator)
      .dropdown({
        onChange: (val: string) => {
          this.generator = val;
        },
      })
    ;

  }

  /**
   * Store the properties into start event node object, commit to vuex store.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphStartEventNodeImpl} object
   * @memberof StartNodeModal
   */
  public saveProperties(page: JointGraphPageImpl, object: GraphStartEventNodeImpl): void {
    const nodePageId = (object.getId() as string).split('-')[0];
    const nodePage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(nodePageId);
    const node: GraphStartEventNodeImpl = (page.getNodes() as TSMap<string, GraphNode>).get(object.getId() as string) as GraphStartEventNodeImpl;

    // Save properties
    node.setLabel(this.label);
    node.setGeneratorRef(this.generator);

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
    GraphNodeImpl.instance.set(node.getId() as string, GraphStartEventNodeImpl.deserialize(node) as GraphNode);

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

  /**
   * Returns the list of generators.
   *
   * @readonly
   * @type {GraphData[]}
   * @memberof StartNodeModal
   */
  public get generators(): GraphData[] {
    let generators;
    try {
      const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
      const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
      generators = nodeData.values().filter((value: GraphData) => value.getType() === 'generator');
      return generators;
    } catch (e) {
      generators = e;
    }
    return generators;
  }
}
</script>
