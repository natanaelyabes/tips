<template>
  <div class="ui large sim_config modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Simulation Configuration</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="six wide column">Run simultanious configuration</div>
            <div class="six wide column">
              <input type="text" id="rsc_txt">
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
</template>
<style>
</style>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
@Component
export default class ConfigurationModal extends Vue {
  private mounted(): void{
    $(".ui.dropdown").dropdown();
    $(".tabular.menu .item").tab();

    $("#sim_config_generate").click(function() {
      var rsc_val = $("#rsc_txt").val();
      var gen_tbl = "";

      $("#sim_config_tbl").html(gen_tbl);

      var head_cols = "";
      var body_cols = "";

      var cols = rsc_val;
      for (var i = 0; i <= cols; i++) {
        head_cols += "<th>Config " + (i + 1) + "</th>";
        body_cols += '<td><button class="ui button">...</button></td>';
      }

      gen_tbl += '<table class="ui celled compact table">';
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

      $("#sim_config_tbl").html(gen_tbl);
    });


  }
}
</script>