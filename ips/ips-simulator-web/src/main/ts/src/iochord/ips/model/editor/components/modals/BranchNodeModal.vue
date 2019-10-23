<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
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

          <template v-if="selectedGate !== '' && selectedType !== ''">
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
            <div id="row_branches_tbl" class="row">
              <div class="four wide column">If</div>
              <div class="twelve wide column">
                <table class="ui celled compact table">
                  <thead>
                    <tr>
                      <th>Condition</th>
                      <th>
                        <button id="btn_add_rule" class="ui positive basic button">
                          <i class="plus icon"></i>
                        </button>
                      </th>
                    </tr>
                  </thead>
                  <tbody id="tb_add_row">
                    <tr>
                      <td>Case.loan > 5000 and case.bank = "A"</td>
                      <td>
                        <button id="btn_del_rule" class="ui negative basic button">
                          <i class="close icon"></i>
                        </button>
                      </td>
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
      <div @click="saveProperties(page, properties)" class="ui positive button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { Modal } from '../../interfaces/Modal';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { GraphBranchNodeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphBranchNodeImpl';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { BRANCH_GATE, BRANCH_TYPE, BRANCH_RULE } from '@/iochord/ips/common/graph/ism/enums/BRANCH';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { TSMap } from 'typescript-map';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';

const graphModule = getModule(GraphModule);

declare const $: any;


/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class BranchNodeModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphBranchNodeImpl> {

  // Whole object properties
  private properties!: GraphBranchNodeImpl;

  // Renderer page
  private page!: JointGraphPageImpl;

  // Component properties
  private label: string = '';
  private selectedGate: BRANCH_GATE = BRANCH_GATE.AND;
  private selectedType: BRANCH_TYPE = BRANCH_TYPE.SPLIT;
  private selectedRule: BRANCH_RULE = BRANCH_RULE.PROBABILITY;

  public populateProperties(page: JointGraphPageImpl, object: GraphBranchNodeImpl): void {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.selectedGate = object.getGate() as BRANCH_GATE;
    this.selectedRule = object.getRule() as BRANCH_RULE;
    this.selectedType = object.getBranchType() as BRANCH_TYPE;

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

  public saveProperties(page: JointGraphPageImpl, object: GraphBranchNodeImpl): void {
    const nodePageId = (object.getId() as string).split('-')[0];
    const nodePage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(nodePageId);
    const node: GraphBranchNodeImpl = (page.getNodes() as TSMap<string, GraphNode>).get(object.getId() as string) as GraphBranchNodeImpl;

    // Save properties
    node.setLabel(this.label);
    node.setGate(this.selectedGate);
    node.setBranchType(this.selectedType);
    node.setRule(this.selectedRule);

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
