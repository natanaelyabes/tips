<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny branches modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Branches</h3>
    </div>
    <div class="scrolling content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Label</div>
            <div class="twelve wide column">
              <input type="text" v-model="label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Gate</div>
            <div class="twelve wide column">
              <select id="branch_txtgate" class="ui search dropdown" v-model="selectedGate">
                <option value="AND">AND</option>
                <option value="XOR">XOR</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Type</div>
            <div class="twelve wide column">
              <select id="branch_txttype" class="ui search dropdown" v-model="selectedType">
                <option value="SPLIT">Split</option>
                <option value="JOIN">Join</option>
              </select>
            </div>
          </div>

          <template v-if="selectedGate && selectedType">
            <!-- Conditions & Rules -->
            <div id="row_branches_rule" class="row">
              <div class="four wide column">Rule</div>
              <div class="twelve wide column">
                <select id="branch_txtrule" class="ui search dropdown" v-model="selectedRule">
                  <option value="DATA">Data</option>
                  <option value="PROBABILITY">Probability</option>
                </select>
              </div>
            </div>

            <!-- Condition table -->
            <div v-if="selectedType === 'SPLIT'" id="row_branches_tbl" class="row">
              <div class="four wide column">If</div>
              <div class="twelve wide column">
                <table class="ui celled compact table">
                  <thead>
                    <tr>
                      <th>Condition</th>
                      <th>Then go to</th>
                    </tr>
                  </thead>
                  <tbody id="tb_add_row">
                    <tr v-for="condition in conditions" :key="condition[0]">
                      <td><input type="text" v-model="condition[1]"></td>
                      <td>{{condition[0]}}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </template>
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
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { GraphBranchNodeImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphBranchNodeImpl';
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { BRANCH_GATE, BRANCH_TYPE, BRANCH_RULE } from '@/iochord/ips/common/graphs/ism/enums/BRANCH';
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';
import { TSMap } from 'typescript-map';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphNodeImpl } from '@/iochord/ips/common/graphs/ism/class/GraphNodeImpl';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';
import { GraphConnectorImpl } from '@/iochord/ips/common/graphs/ism/class/GraphConnectorImpl';
import { GraphConnector } from '@/iochord/ips/common/graphs/ism/interfaces/GraphConnector';
import { NODE_TYPE } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/enums/NODE';

const graphModule = getModule(GraphModule);

declare const $: any;

@Component

/**
 * Branch node modal.
 *
 * @export
 * @class BranchNodeModal
 * @extends {SemanticComponent}
 * @implements {Modal<JointGraphPageImpl, GraphBranchNodeImpl>}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class BranchNodeModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphBranchNodeImpl> {

  /**
   * The properties of branch node.
   *
   * @private
   * @type {GraphBranchNodeImpl}
   * @memberof BranchNodeModal
   */
  private properties!: GraphBranchNodeImpl;

  /**
   * The graph page object.
   *
   * @private
   * @type {JointGraphPageImpl}
   * @memberof BranchNodeModal
   */
  private page!: JointGraphPageImpl;

  /**
   * The label of branch node.
   *
   * @private
   * @type {string}
   * @memberof BranchNodeModal
   */
  private label: string = '';

  /**
   * The gate type of a branch node.
   *
   * @private
   * @type {BRANCH_GATE}
   * @memberof BranchNodeModal
   */
  private selectedGate: BRANCH_GATE = BRANCH_GATE.AND;

  /**
   * The type of a branch node.
   *
   * @private
   * @type {BRANCH_TYPE}
   * @memberof BranchNodeModal
   */
  private selectedType: BRANCH_TYPE = BRANCH_TYPE.SPLIT;

  /**
   * The rule of a branch node.
   *
   * @private
   * @type {BRANCH_RULE}
   * @memberof BranchNodeModal
   */
  private selectedRule: BRANCH_RULE = BRANCH_RULE.PROBABILITY;

  /**
   * The conditions of branch node.
   *
   * @private
   * @type {string[][]}
   * @memberof BranchNodeModal
   */
  private conditions: string[][] = [];

  /**
   * Assigns the properties of branch node to the field of branch node modal.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphBranchNodeImpl} object
   * @memberof BranchNodeModal
   */
  public populateProperties(page: JointGraphPageImpl, object: GraphBranchNodeImpl): void {

    // Object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.selectedGate = object.getGate() as BRANCH_GATE;
    this.selectedRule = object.getRule() as BRANCH_RULE;
    this.selectedType = object.getBranchType() as BRANCH_TYPE;

    // Populate conditions based on output nodes
    this.setConditions(page, object);

    // Initialize dropdown with default value
    $('#branch_txtgate')
      .dropdown('set selected', this.selectedGate)
      .dropdown({
        onChange: (val: BRANCH_GATE) => {
          this.selectedGate = val;
        },
      })
    ;

    $('#branch_txttype')
      .dropdown('set selected', this.selectedType)
      .dropdown({
        onChange: (val: BRANCH_TYPE) => {
          this.selectedType = val;
        },
      })
    ;

    $('#branch_txtrule')
      .dropdown('set selected', this.selectedRule)
      .dropdown({
        onChange: (val: BRANCH_RULE) => {
          this.selectedRule = val;
        },
      })
    ;
  }

  /**
   * Store the properties into branch node object, commit to the graph vuex module.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphBranchNodeImpl} object
   * @memberof BranchNodeModal
   */
  public saveProperties(page: JointGraphPageImpl, object: GraphBranchNodeImpl): void {
    const nodePageId = (object.getId() as string).split('-')[0];
    const nodePage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(nodePageId);
    const node: GraphBranchNodeImpl = (page.getNodes() as TSMap<string, GraphNode>).get(object.getId() as string) as GraphBranchNodeImpl;

    // Save properties
    node.setLabel(this.label);
    node.setGate(this.selectedGate);
    node.setBranchType(this.selectedType);
    node.setRule(this.selectedRule);

    this.conditions.forEach((condition: string[]) => {
      (node.getConditions() as TSMap<string, string>).set(condition[0], condition[1]);
    });

    if (node.getGate() === BRANCH_GATE.AND && node.getBranchType() === BRANCH_TYPE.JOIN) {
      node.setImageIcon(NODE_TYPE.and_join.image);
    } else if (node.getGate() === BRANCH_GATE.AND && node.getBranchType() === BRANCH_TYPE.SPLIT) {
      node.setImageIcon(NODE_TYPE.and_split.image);
    } else if (node.getGate() === BRANCH_GATE.XOR && node.getBranchType() === BRANCH_TYPE.JOIN) {
      node.setImageIcon(NODE_TYPE.xor_join.image);
    } else if (node.getGate() === BRANCH_GATE.XOR && node.getBranchType() === BRANCH_TYPE.SPLIT) {
      node.setImageIcon(NODE_TYPE.xor_split.image);
    }

    // Change label of the renderer node
    page.getGraph().getCells().map((cell: joint.dia.Cell) => {
      if (cell.attributes.nodeId === object.getId()) {
        cell.attr({
          label: {
            text: this.label,
          },
          image: {
            xlinkHref: node.getImageIcon(),
          },
        });
      }
    });

    // Update local instance
    GraphNodeImpl.instance.set(node.getId() as string, GraphBranchNodeImpl.deserialize(node) as GraphNode);

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

  public setConditions(page: JointGraphPageImpl, object: GraphBranchNodeImpl) {
    const nodePageId = (object.getId() as string).split('-')[0];
    const nodePage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(nodePageId);
    const node: GraphBranchNodeImpl = (page.getNodes() as TSMap<string, GraphNode>).get(object.getId() as string) as GraphBranchNodeImpl;

    if ((node.getConditions() as TSMap<string, string>).size() === 0) {
      this.conditions = [];

      // Get all connectors
      const connectors = GraphConnectorImpl.instance;

      // Get its output nodes
      const outputNodes = connectors.values()
        .filter((connector: GraphConnector) => connector.getSourceRef() === object.getId())
        .map((connector: GraphConnector) => connector.getTargetRef());

      // Set conditions
      outputNodes.forEach((nodeId: string | null) => {
        const goTo = GraphNodeImpl.instance.get(nodeId as string);
        this.conditions.push([`${(goTo.getLabel() as string)}`, '']);
      });

    } else {
      this.conditions = (node.getConditions() as TSMap<string, string>).entries();
    }
  }
}
</script>
