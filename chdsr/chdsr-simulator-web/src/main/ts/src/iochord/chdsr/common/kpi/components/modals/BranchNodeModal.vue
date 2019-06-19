<template>
  <div class="ui tiny branches modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Branches</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Label</div>
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Gate</div>
            <div class="twelve wide column">
              <select class="ui dropdown" v-model="selectedGate">
                <option value>Gate</option>
                <option value="and">AND</option>
                <option value="xor">XOR</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Type</div>
            <div class="twelve wide column">
              <select class="ui dropdown" v-model="selectedSJ">
                <option value>Gate</option>
                <option value="split">Split</option>
                <option value="join">Join</option>
              </select>
            </div>
          </div>
          <div id="row_branches_rule" class="row" style="visibility:hidden">
            <div class="four wide column">Rule</div>
            <div class="twelve wide column">
              <select class="ui dropdown" v-model="selectedRule">
                <option value>Rule</option>
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
</template>
<style>
</style>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
@Component
export default class BranchNodeModal extends Vue {
  private selectedGate = "";
  private selectedSJ = "";
  private selectedRule = "";

  private update(): void {
    if (this.selectedGate == "xor" && this.selectedSJ == "split") {
      $("#row_branches_rule").attr("style", "visibility:visible");
      $("#row_branches_if").attr("style", "visibility:visible");
      $("#row_branches_tbl").attr("style", "visibility:visible");
    } else {
      $("#row_branches_rule").attr("style", "visibility:hidden");
      $("#row_branches_if").attr("style", "visibility:hidden");
      $("#row_branches_tbl").attr("style", "visibility:hidden");
    }
  }

  private mounted(): void{
    $(".ui.dropdown").dropdown();
    $(".tabular.menu .item").tab();

    $("#btn_add_rule").click(function() {
      var new_row = "<tr>";
      new_row += "<td>Case.loan > 5000 and case.bank = 'A'</td>";
      new_row += "<td>";
      new_row += '<button id="btn_del_rule" class="ui negative basic button">';
      new_row += '<i class="close icon"></i>';
      new_row += "</button>";
      new_row += "</td>";
      new_row += "</tr>";
      $("#tb_add_row").append(new_row);
    });

    $("#row_branches_tbl").on("click", "#btn_del_rule", function() {
      $("#btn_del_rule")
        .closest("tr")
        .remove();
    });
  }
}
</script>