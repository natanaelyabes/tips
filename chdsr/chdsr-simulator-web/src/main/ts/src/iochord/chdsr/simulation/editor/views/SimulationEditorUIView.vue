<template>
  <div class="simulation editor test view">
    <div class="ui basic segment">
      <h1>Simulation Editor Test</h1>
      <div id="mypaper"></div>
    </div>

    <!-- Modal for start node -->
    <StartNodeModal/>

    <!-- Modal for stop node -->
    <div class="ui tiny stop modal">
      <i class="close icon"></i>
      <div id="stop_modal_title" class="header">
        <h3 class="ui green header">
          Stop
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="three wide column">
                Label
              </div>
              <div class="thirteen wide column">
                <input type="text" id="stop_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <div class="inline field">
                  <input type="checkbox" class="hidden" />
                  <label>Report statistics</label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Modal for activity -->
    <div class="ui tiny activity modal">
      <i class="close icon"></i>
      <div id="activity_modal_title" class="header">
        <h3 class="ui green header">
          Activity
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="three wide column">
                Label
              </div>
              <div class="thirteen wide column">
                <input type="text" id="activity_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <div class="ui top attached tabular menu">
                  <a class="active item" data-tab="basic">Basic</a>
                  <a class="item" data-tab="processing">Processing</a>
                  <a class="item" data-tab="advanced">Advanced</a>
                </div>
                <!-- basic tab -->
                <div class="ui bottom attached active tab segment" data-tab="basic">
                  <div class="ui grid">
                    <div class="row">
                      <div class="four wide column">
                        Activity Type
                      </div>
                      <div class="twelve wide column">
                        <select class="ui dropdown" v-model="selectedActivityType">
                          <option value="standard">Standard</option>
                          <option value="concurrent">Concurrent Batch Process</option>
                          <option value="split">Split Module Process</option>
                        </select>
                      </div>
                    </div>
                    <div id="basic-standard-sm-1" class="row" style="visibility:visible">
                      <div class="four wide column">
                        Resources
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="resources_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div id="basic-cbp-sm-1" class="row" style="visibility:hidden">
                      <div class="four wide column">
                        Process Criteria
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="pc_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div id="basic-cbp-sm-2" class="row" style="visibility:hidden">
                      <div class="four wide column">
                        Resources
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="resources_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div id="basic-cbp-sm-3" class="row" style="visibility:hidden">
                      <div class="four wide column">
                        Limit
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="limit_txt_label" />
                      </div>
                    </div>
                    <div id="basic-split-sm-1" class="row" style="visibility:hidden">
                      <div class="four wide column">
                        Split Criteria
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="split_criteria_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div id="basic-split-sm-2" class="sixteen wide column" style="visibility:hidden">
                      <div class="inline field">
                        <input type="checkbox" class="hidden" />
                        <label>Report scrap</label>
                      </div>
                    </div>
                    <div id="basic-split-sm-3" class="row" style="visibility:hidden">
                      <div class="four wide column">
                        Resources
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="resources_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div id="basic-split-sm-4" class="row" style="visibility:hidden">
                      <div class="four wide column">
                        Split Criteria
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="split_criteria2_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div class="sixteen wide column">
                      <div class="inline field">
                        <input type="checkbox" class="hidden" />
                        <label>Report statistics</label>
                      </div>
                    </div>
                    <div class="row">
                      <div class="four wide column">
                        Custom Monitor
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="cm_type_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- processing tab -->
                <div class="ui bottom attached tab segment" data-tab="processing">
                  <div class="ui grid">
                    <div class="row">
                      <div class="three wide column">
                        Processing Time
                      </div>
                      <div class="thirteen wide column">
                        <input type="text" id="processing_time_txt_label" />
                      </div>
                    </div>
                    <div class="row">
                      <div class="three wide column">
                        Parameter
                      </div>
                      <div class="thirteen wide column">
                        <input type="text" id="parameter_pt_txt_label" />
                      </div>
                    </div>
                    <div class="row">
                      <div class="three wide column">
                        Setup Time
                      </div>
                      <div class="thirteen wide column">
                        <input type="text" id="setup_time_txt_label" />
                      </div>
                    </div>
                    <div class="row">
                      <div class="three wide column">
                        Parameter
                      </div>
                      <div class="thirteen wide column">
                        <input type="text" id="parameter_st_txt_label" />
                      </div>
                    </div>
                    <div class="row">
                      <div class="three wide column">
                        Unit
                      </div>
                      <div class="thirteen wide column">
                        <input type="text" id="unit_txt_label" />
                      </div>
                    </div>
                    <div class="row">
                      <div class="four wide column">
                        Queue label
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="ql_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- advanced tab -->
                <div class="ui bottom attached tab segment" data-tab="advanced">
                  <div class="ui grid">
                    <div class="row">
                      <div class="four wide column">
                        Input Type
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="input_type_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div class="row">
                      <div class="four wide column">
                        Output Type
                      </div>
                      <div class="nine wide column">
                        <input type="text" id="input_type_txt_label" />
                      </div>
                      <div class="three wide column">
                        <button class="ui button">...</button>
                      </div>
                    </div>
                    <div class="row">
                      <div class="sixteen wide column">
                        <div class="field">
                          <label>Code Segment</label>
                          <textarea id="code_segment"></textarea>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Object Type -->
    <div class="ui tiny object_type modal">
      <i class="close icon"></i>
      <div id="x_title" class="header">
        <h3 class="ui green header">
          Object Type
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="three wide column">
                Name
              </div>
              <div class="thirteen wide column">
                <input type="text" id="object_type_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <h4>Type</h4>
              </div>
            </div>
            <div class="row">
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
              <div class="two wide column">
                <button class="ui button">...</button>
              </div>
              <div class="two wide column">
                <button class="ui positive basic button">
                  <i class="plus icon"></i>
                </button>
              </div>
            </div>
            <div class="row">
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
              <div class="two wide column">
                <button class="ui button">...</button>
              </div>
              <div class="two wide column">
                <button class="ui negative basic button">
                  <i class="close icon"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Generator -->
    <div class="ui tiny generator modal">
      <i class="close icon"></i>
      <div id="x_title" class="ui red header">
        <h3 class="ui green header">
          Generator
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="three wide column">
                Label
              </div>
              <div class="thirteen wide column">
                <input type="text" id="generator_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <h4>Object</h4>
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Data
              </div>
              <div class="nine wide column">
                <input type="text" id="x_txt_label" />
              </div>
              <div class="three wide column">
                <button class="ui button">...</button>
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <h4>Arrival Rate</h4>
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Distribution Type
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Expression
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Unit
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Entities per Arrival
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Max Arrivals
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                First Creation
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Function -->
    <div class="ui tiny function modal">
      <i class="close icon"></i>
      <div id="x_title" class="header">
        <h3 class="ui green header">
          Function
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="four wide column">
                Name
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Input Parameter
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <h4>Code Segment</h4>
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <textarea></textarea>
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Return Parameter
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Queue -->
    <div class="ui tiny queue modal">
      <i class="close icon"></i>
      <div id="x_title" class="header">
        <h3 class="ui green header">
          Queue
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="four wide column">
                Label
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Queue Type
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                <div class="ui checkbox">
                  <input type="checkbox" class="hidden">
                  <label>Shared buffer</label>
                </div>
              </div>
              <div class="twelve wide column"></div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <h4>Type</h4>
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                <div class="ui radio checkbox">
                  <input type="radio" name="xrb" checked="" class="hidden">
                  <label>Single</label>
                </div>
              </div>
              <div class="twelve wide column"></div>
            </div>
            <div class="row">
              <div class="four wide column">
                <div class="ui radio checkbox">
                  Queue Size
                </div>
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <div class="ui radio checkbox">
                  <input type="radio" name="xrb" checked="" class="hidden">
                  <label>Dedicated queue to resources</label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="sixteen wide column">
                <table class="ui celled compact table">
                  <thead>
                    <tr>
                      <th>Sub Queue ID</th>
                      <th>Queue Size</th>
                      <th>Resource ID</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td>
                        <button class="ui negative basic button">
                          <i class="close icon"></i>
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Resource -->
    <div class="ui tiny resource modal">
      <i class="close icon"></i>
      <div id="x_title" class="header">
        <h3 class="ui green header">
          Resource
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="four wide column">
                Group ID
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="six wide column">
                <div class="ui checkbox">
                  <input type="checkbox" class="hidden">
                  <label>Import from table</label>
                </div>
              </div>
              <div class="ten wide column">
                <button class="ui button">...</button>
              </div>
            </div>
            <div class="row">
              <table class="ui celled compact table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Quantity</th>
                    <th>Capacity</th>
                    <th>Available Time</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                      <button class="ui negative basic button">
                        <i class="close icon"></i>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Branches -->
    <div class="ui tiny branches modal">
      <i class="close icon"></i>
      <div id="x_title" class="header">
        <h3 class="ui green header">Branches</h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="four wide column">
                Label
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Gate
              </div>
              <div class="twelve wide column">
                <select class="ui dropdown" v-model="selectedGate">
                  <option value="">Gate</option>
                  <option value="and">AND</option>
                  <option value="xor">XOR</option>
                </select>
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Type
              </div>
              <div class="twelve wide column">
                <select class="ui dropdown" v-model="selectedSJ">
                  <option value="">Gate</option>
                  <option value="split">Split</option>
                  <option value="join">Join</option>
                </select>
              </div>
            </div>
            <div id="row_branches_rule" class="row" style="visibility:hidden">
              <div class="four wide column">
                Rule
              </div>
              <div class="twelve wide column">
                <select class="ui dropdown" v-model="selectedRule">
                  <option value="">Rule</option>
                  <option value="data">Data</option>
                  <option value="probability">Probability</option>
                </select>
              </div>
            </div>
            <div id="row_branches_if" class="row" style="visibility:hidden">
              <div class="sixteen wide column">
                <h4>If</h4>
              </div>
            </div>
            <div id="row_branches_tbl" class="row" style="visibility:hidden">
              <div class="sixteen wide column">
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
                      <td>Case.loan > 5000 and case.bank = 'A'</td>
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
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Simulation Configuration -->
    <div class="ui large sim_config modal">
      <i class="close icon"></i>
      <div id="x_title" class="header">
        <h3 class="ui green header">
          Simulation Configuration
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="six wide column">
                Run simultanious configuration
              </div>
              <div class="six wide column">
                <input type="text" id="rsc_txt" />
              </div>
              <div class="four wide column">
                <button id="sim_config_generate" class="ui grey button">Generate</button>
              </div>
            </div>
            <div class="row">
              <div id="sim_config_tbl" class="sixteen wide column"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

    <!-- Simulation Control -->
    <div class="ui tiny sim_control modal">
      <i class="close icon"></i>
      <div id="x_title" class="header">
        <h3 class="ui green header">
          Simulation Control
        </h3>
      </div>
      <div class="content">
        <div class="ui form">
          <div class="ui grid">
            <div class="row">
              <div class="four wide column">
                Replication no.
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Stopping Criteria
              </div>
              <div class="twelve wide column">
                <select class="ui dropdown">
                  <option value="">Criteria</option>
                  <option value="time">Data</option>
                  <option value="steps">Steps</option>
                  <option value="custom">Custom</option>
                </select>
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Value
              </div>
              <div class="twelve wide column">
                <input type="text" id="x_txt_label" />
              </div>
            </div>
            <div class="row">
              <div class="four wide column">
                Start Simulation time
              </div>
              <div class="ten wide column">
                <input type="text" id="x_txt_label" />
              </div>
              <div class="two wide column">
                <button class="ui button">...</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui save button">Save</div>
        <div class="ui cancel button">Cancel</div>
      </div>
    </div>

  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// Components
import StartNodeModal from '../../../common/kpi/components/modals/StartNodeModal.vue';

  declare const $: any;

  @Component
  export default class SimulationEditorUIView extends Vue {
    private selectedGate = '';
    private selectedSJ = '';
    private selectedRule = '';
    private selectedActivityType = '';

    private updated(): void {
      /*
        Branches
        
        if Gate == XOR and Type = split
        Then show specific form at the bottom part
      */
      if (this.selectedGate == 'xor' && this.selectedSJ == 'split') {
        $('#row_branches_rule').attr('style', 'visibility:visible');
        $('#row_branches_if').attr('style', 'visibility:visible');
        $('#row_branches_tbl').attr('style', 'visibility:visible');
      } else {
        $('#row_branches_rule').attr('style', 'visibility:hidden');
        $('#row_branches_if').attr('style', 'visibility:hidden');
        $('#row_branches_tbl').attr('style', 'visibility:hidden');
      }

      /*
        Activity

        if Activity Type == standard Then
        if Activity Type == concurrent Then
        else (Activity Type == split module)
      */
      if (this.selectedActivityType == 'standard') {
        $('#basic-standard-sm-1').attr('style', 'visibility:visible');
        $('#basic-cbp-sm-1').attr('style', 'visibility:hidden');
        $('#basic-cbp-sm-2').attr('style', 'visibility:hidden');
        $('#basic-cbp-sm-3').attr('style', 'visibility:hidden');

        $('#basic-split-sm-1').attr('style', 'visibility:hidden');
        $('#basic-split-sm-2').attr('style', 'visibility:hidden');
        $('#basic-split-sm-3').attr('style', 'visibility:hidden');
        $('#basic-split-sm-3').attr('style', 'visibility:hidden');
      } else if (this.selectedActivityType == 'concurrent') {
        $('#basic-standard-sm-1').attr('style', 'visibility:hidden');

        $('#basic-cbp-sm-1').attr('style', 'visibility:visible');
        $('#basic-cbp-sm-2').attr('style', 'visibility:visible');
        $('#basic-cbp-sm-3').attr('style', 'visibility:visible');

        $('#basic-split-sm-1').attr('style', 'visibility:hidden');
        $('#basic-split-sm-2').attr('style', 'visibility:hidden');
        $('#basic-split-sm-3').attr('style', 'visibility:hidden');
        $('#basic-split-sm-3').attr('style', 'visibility:hidden');
      } else {
        $('#basic-standard-sm-1').attr('style', 'visibility:hidden');

        $('#basic-cbp-sm-1').attr('style', 'visibility:hidden');
        $('#basic-cbp-sm-2').attr('style', 'visibility:hidden');
        $('#basic-cbp-sm-3').attr('style', 'visibility:hidden');

        $('#basic-split-sm-1').attr('style', 'visibility:visible');
        $('#basic-split-sm-2').attr('style', 'visibility:visible');
        $('#basic-split-sm-3').attr('style', 'visibility:visible');
        $('#basic-split-sm-4').attr('style', 'visibility:visible');
      }
    }
    private mounted(): void {
      $('.ui.dropdown').dropdown();
      $('.tabular.menu .item').tab();

      /* JQUery Event Handler */
      /* Add Rule for Branches Form */
      $('#btn_add_rule').click(function () {
        var new_row = "<tr>";
        new_row += "<td>Case.loan > 5000 and case.bank = 'A'</td>";
        new_row += "<td>";
        new_row += "<button id=\"btn_del_rule\" class=\"ui negative basic button\">";
        new_row += "<i class=\"close icon\"></i>";
        new_row += "</button>";
        new_row += "</td>";
        new_row += "</tr>";
        $('#tb_add_row').append(new_row);
      });

      $('#btn_del_rule').click(function () {
        //$('#btn_del_rule').closest('tr').remove();
      });

      /*
        Branches
        - Delete Button
      */
      $('#row_branches_tbl').on('click', '#btn_del_rule', function () {
        $('#btn_del_rule').closest('tr').remove();
        //alert("tes");
      });

      /* Simulation Configuration */
      $('#sim_config_generate').click(function () {
        var rsc_val = $('#rsc_txt').val();
        var gen_tbl = "";

        $('#sim_config_tbl').html(gen_tbl);

        var head_cols = "";
        var body_cols = "";

        var cols = rsc_val;
        for (var i = 0; i <= cols; i++) {
          head_cols += "<th>Config " + (i + 1) + "</th>";
          body_cols += "<td><button class=\"ui button\">...</button></td>";
        }

        gen_tbl += "<table class=\"ui celled compact table\">";
        gen_tbl += "<thead>";
        gen_tbl += "<tr>";
        gen_tbl += "<th>Module</th>";
        gen_tbl += head_cols;
        gen_tbl += "</tr>";
        gen_tbl += "</thead>";
        gen_tbl += "<tbody>";
        gen_tbl += "<tr>";
        gen_tbl += "<td>Start</td>";
        gen_tbl += body_cols;
        gen_tbl += "</tr>";
        gen_tbl += "<tr>";
        gen_tbl += "<td>End</td>";
        gen_tbl += body_cols;
        gen_tbl += "</tr>";
        gen_tbl += "</tbody>";
        gen_tbl += "</table>";

        $('#sim_config_tbl').html(gen_tbl);
      });

      const graph = new joint.dia.Graph;

      const paper = new joint.dia.Paper({
        el: document.getElementById('mypaper'),
        model: graph,
        width: window.innerWidth,
        height: window.innerHeight,
        gridSize: 1
      });

      /* Graph's Elements */
      const standar = joint.shapes.standard;

      const c_start = new standar.Circle();
      c_start.resize(20, 20);
      c_start.position(100, 50);
      c_start.attr('root/title', 'start');
      c_start.attr('body/fill', 'white')
      c_start.addTo(graph);

      const r_br1 = new standar.Rectangle();
      r_br1.resize(50, 50);
      r_br1.position(250, 25);
      r_br1.attr({
        rect: {
          transform: 'rotate(45)'
        }
      })
      r_br1.attr('root/title', 'branch');
      r_br1.attr('body/fill', 'white');
      r_br1.addTo(graph);

      const r_act_1 = new standar.Rectangle();
      r_act_1.resize(100, 50);
      r_act_1.position(400, 35);
      r_act_1.attr('root/title', 'activity');
      r_act_1.attr('body/fill', 'white');
      r_act_1.addTo(graph);

      const r_act_2 = r_act_1.clone() as joint.shapes.standard.Rectangle;
      r_act_2.position(400, 150);
      r_act_2.addTo(graph);

      const r_br2 = r_br1.clone() as joint.shapes.standard.Rectangle;
      r_br2.position(700, 25);
      r_br2.addTo(graph);

      const c_stop = c_start.clone() as joint.shapes.standard.Circle;
      c_stop.position(800, 50);
      c_stop.attr('root/title', 'stop');
      c_stop.attr('body/fill', 'black')
      c_stop.addTo(graph);

      /*
          Link between shapes
      */

      // Start to br1
      const l_stb = new joint.dia.Link();
      l_stb.attr('root/title', 'link');
      l_stb.source(c_start);
      l_stb.target(r_br1);
      l_stb.addTo(graph);

      const l_btr1 = new joint.dia.Link();
      l_btr1.source(r_br1);
      l_btr1.target(r_act_1);
      l_btr1.addTo(graph);

      const l_btr2 = new joint.dia.Link();
      l_btr2.source(r_br1);
      l_btr2.target(r_act_2);
      l_btr2.addTo(graph);

      const l_r1tbr2 = new joint.dia.Link()
      l_r1tbr2.source(r_act_1);
      l_r1tbr2.target(r_br2);
      l_r1tbr2.addTo(graph);

      const l_r2tbr2 = new joint.dia.Link()
      l_r2tbr2.source(r_act_2);
      l_r2tbr2.target(r_br2);
      l_r2tbr2.addTo(graph);

      const l_br2te = new joint.dia.Link()
      l_br2te.source(r_br2);
      l_br2te.target(c_stop);
      l_br2te.addTo(graph);

      /* Rect. for view UI */
      /* Object Type */
      const r_object_type = new standar.Rectangle()
      r_object_type.resize(100, 50);
      r_object_type.position(400, 250);
      r_object_type.attr('root/title', 'object_type');
      r_object_type.attr('body/fill', 'white');
      r_object_type.attr('label/text', 'Object Type');
      r_object_type.addTo(graph);

      /* Generator */
      const r_generator = r_object_type.clone() as joint.shapes.standard.Rectangle;
      r_generator.position(100, 100);
      r_generator.resize(40, 40);
      r_generator.attr('body/fill', 'lightblue');
      r_generator.attr('root/title', 'generator');
      r_generator.attr('label/text', 'G');
      r_generator.addTo(graph);

      /* Function */
      /*const r_function = r_object_type.clone() as joint.shapes.standard.Rectangle;
      r_function.position(400,440);
      r_function.attr('root/title','function');
      r_function.attr('label/text','Function');
      r_function.addTo(graph);*/

      var rClickCounter = 0;

      /* General Shape for popup */
      const r_general = r_object_type.clone() as joint.shapes.standard.Rectangle;
      r_general.resize(200, 50);
      r_general.position(400, 320);
      r_general.attr('root/title', 'general');
      r_general.attr('label/text', 'Other Forms');
      r_general.addTo(graph);

      /* Event Handler */
      paper.on({
        'blank:pointerclick': function (elementView) {
          resetAll(this);
        },
        'element:contextmenu': function (elementView) {
          resetAll(this);

          const currentElement = elementView.model;
          currentElement.attr('body/stroke', 'red');
          const currentElementTitle = currentElement.attr('root/title');

          if (currentElementTitle == "start") {
            $('#start_modal_title').html('<b>' + currentElementTitle + '</b>');
            $('.ui.tiny.start.modal').modal('show');
          } else if (currentElementTitle == "stop") {
            $('#stop_modal_title').html('<b>' + currentElementTitle + '</b>');
            $('.ui.tiny.stop.modal').modal('show');
          } else if (currentElementTitle == "activity") {
            $('.ui.tiny.activity.modal').modal('show');
          } else if (currentElementTitle == "object_type") {
            $('.ui.tiny.object_type.modal').modal('show');
          } else if (currentElementTitle == "generator") {
            $('.ui.tiny.generator.modal').modal('show');
          } else if (currentElementTitle == "link") {
            //$('.ui.tiny.function.modal').modal('show');
          } else if (currentElementTitle == "branch") {
            $('.ui.tiny.branches.modal').modal('show');
          } else if (currentElementTitle == "general") {
            if (rClickCounter == 0) {
              $('.ui.tiny.queue.modal').modal('show');
            } else if (rClickCounter == 1) {
              $('.ui.tiny.resource.modal').modal('show');
            } else if (rClickCounter == 2) {
              //$('.ui.tiny.branches.modal').modal('show');
            } else if (rClickCounter == 3) {
              $('.ui.large.sim_config.modal').modal('show');
            } else if (rClickCounter == 4) {
              $('.ui.tiny.sim_control.modal').modal('show');
            } else {
              rClickCounter = -1;
            }

            //alert(rClickCounter)
            rClickCounter++;
          }

        },
        'link:contextmenu': function (linkView) {
          const currentLink = linkView.model;

          $('.ui.tiny.function.modal').modal('show');
          //alert(rClickCounter);
          //alert(currentLink.attr('root/title'));

          rClickCounter++;
        }
      });

      function resetAll(paper: any) {

        /* Reset all elements in the paper */
        var elements = paper.model.getElements();
        for (var i = 0, ii = elements.length; i < ii; i++) {
          var currentElement = elements[i];
          currentElement.attr('body/stroke', 'black');
        }

        var links = paper.model.getLinks();
        for (var j = 0, jj = links.length; j < jj; j++) {
          var currentLink = links[j];
          currentLink.attr('line/stroke', 'black');
          currentLink.label(0, {
            attrs: {
              body: {
                stroke: 'black'
              }
            }
          })
        }
      }

      },
      'link:contextmenu': function (linkView) {
        const currentLink = linkView.model;

        $('.ui.tiny.function.modal').modal('show');
        //alert(rClickCounter);
        //alert(currentLink.attr('root/title'));

        rClickCounter++;
      }
    });

    function resetAll(paper: any) {

      /* Reset all elements in the paper */
      var elements = paper.model.getElements();
      for (var i = 0, ii = elements.length; i < ii; i++) {
        var currentElement = elements[i];
        currentElement.attr('body/stroke', 'black');
      }

      var links = paper.model.getLinks();
      for (var j = 0, jj = links.length; j < jj; j++) {
        var currentLink = links[j];
        currentLink.attr('line/stroke', 'black');
        currentLink.label(0, {
          attrs: {
            body: {
              stroke: 'black'
            }
          }
        })
      }
    }

  }
}
</script>