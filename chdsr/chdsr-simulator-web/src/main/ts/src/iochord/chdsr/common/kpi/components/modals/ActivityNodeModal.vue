<template>
  <div class="ui tiny activity modal">
    <i class="close icon"></i>
    <div id="activity_modal_title" class="header">
      <h3 class="ui green header">Activity</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" id="activity_txt_label">
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
                    <div class="four wide column">Activity Type</div>
                    <div class="twelve wide column">
                      <select class="ui dropdown" v-model="selectedActivityType">
                        <option value="standard">Standard</option>
                        <option value="concurrent">Concurrent Batch Process</option>
                        <option value="split">Split Module Process</option>
                      </select>
                    </div>
                  </div>
                  <div id="basic-standard-sm-1" class="row" style="visibility:visible">
                    <div class="four wide column">Resources</div>
                    <div class="nine wide column">
                      <input type="text" id="resources_txt_label">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div id="basic-cbp-sm-1" class="row" style="visibility:hidden">
                    <div class="four wide column">Process Criteria</div>
                    <div class="nine wide column">
                      <input type="text" id="pc_txt_label">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div id="basic-cbp-sm-2" class="row" style="visibility:hidden">
                    <div class="four wide column">Resources</div>
                    <div class="nine wide column">
                      <input type="text" id="resources_txt_label">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div id="basic-cbp-sm-3" class="row" style="visibility:hidden">
                    <div class="four wide column">Limit</div>
                    <div class="nine wide column">
                      <input type="text" id="limit_txt_label">
                    </div>
                  </div>
                  <div id="basic-split-sm-1" class="row" style="visibility:hidden">
                    <div class="four wide column">Split Criteria</div>
                    <div class="nine wide column">
                      <input type="text" id="split_criteria_txt_label">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div id="basic-split-sm-2" class="sixteen wide column" style="visibility:hidden">
                    <div class="inline field">
                      <input type="checkbox" class="hidden">
                      <label>Report scrap</label>
                    </div>
                  </div>
                  <div id="basic-split-sm-3" class="row" style="visibility:hidden">
                    <div class="four wide column">Resources</div>
                    <div class="nine wide column">
                      <input type="text" id="resources_txt_label">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div id="basic-split-sm-4" class="row" style="visibility:hidden">
                    <div class="four wide column">Split Criteria</div>
                    <div class="nine wide column">
                      <input type="text" id="split_criteria2_txt_label">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div class="sixteen wide column">
                    <div class="inline field">
                      <input type="checkbox" class="hidden">
                      <label>Report statistics</label>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Custom Monitor</div>
                    <div class="nine wide column">
                      <input type="text" id="cm_type_txt_label">
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
                    <div class="three wide column">Processing Time</div>
                    <div class="thirteen wide column">
                      <input type="text" id="processing_time_txt_label">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Parameter</div>
                    <div class="thirteen wide column">
                      <input type="text" id="parameter_pt_txt_label">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Setup Time</div>
                    <div class="thirteen wide column">
                      <input type="text" id="setup_time_txt_label">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Parameter</div>
                    <div class="thirteen wide column">
                      <input type="text" id="parameter_st_txt_label">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Unit</div>
                    <div class="thirteen wide column">
                      <input type="text" id="unit_txt_label">
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Queue label</div>
                    <div class="nine wide column">
                      <input type="text" id="ql_txt_label">
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
                    <div class="four wide column">Input Type</div>
                    <div class="nine wide column">
                      <input type="text" id="input_type_txt_label">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Output Type</div>
                    <div class="nine wide column">
                      <input type="text" id="input_type_txt_label">
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
</template>
<style>
</style>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
@Component
export default class ActivityNodeModal extends Vue {
  private updated(): void{
    if (this.selectedActivityType == "standard") {
      $("#basic-standard-sm-1").attr("style", "visibility:visible");
      $("#basic-cbp-sm-1").attr("style", "visibility:hidden");
      $("#basic-cbp-sm-2").attr("style", "visibility:hidden");
      $("#basic-cbp-sm-3").attr("style", "visibility:hidden");

      $("#basic-split-sm-1").attr("style", "visibility:hidden");
      $("#basic-split-sm-2").attr("style", "visibility:hidden");
      $("#basic-split-sm-3").attr("style", "visibility:hidden");
      $("#basic-split-sm-3").attr("style", "visibility:hidden");
    } else if (this.selectedActivityType == "concurrent") {
      $("#basic-standard-sm-1").attr("style", "visibility:hidden");

      $("#basic-cbp-sm-1").attr("style", "visibility:visible");
      $("#basic-cbp-sm-2").attr("style", "visibility:visible");
      $("#basic-cbp-sm-3").attr("style", "visibility:visible");

      $("#basic-split-sm-1").attr("style", "visibility:hidden");
      $("#basic-split-sm-2").attr("style", "visibility:hidden");
      $("#basic-split-sm-3").attr("style", "visibility:hidden");
      $("#basic-split-sm-3").attr("style", "visibility:hidden");
    } else {
      $("#basic-standard-sm-1").attr("style", "visibility:hidden");

      $("#basic-cbp-sm-1").attr("style", "visibility:hidden");
      $("#basic-cbp-sm-2").attr("style", "visibility:hidden");
      $("#basic-cbp-sm-3").attr("style", "visibility:hidden");

      $("#basic-split-sm-1").attr("style", "visibility:visible");
      $("#basic-split-sm-2").attr("style", "visibility:visible");
      $("#basic-split-sm-3").attr("style", "visibility:visible");
      $("#basic-split-sm-4").attr("style", "visibility:visible");
    }
  }
  
  private mounted(): void{
    $(".ui.dropdown").dropdown();
    $(".tabular.menu .item").tab();
  }
}
</script>