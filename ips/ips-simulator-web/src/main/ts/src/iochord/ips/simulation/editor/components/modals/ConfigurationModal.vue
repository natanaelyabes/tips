<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
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
            <div class="six wide column">Run simultaneous configuration</div>
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
      <p><em>Node properties are automatically saved</em></p>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
declare const $: any;

@Component

/**
 * The configuration modal.
 *
 * @export
 * @class ConfigurationModal
 * @extends {SemanticComponent}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class ConfigurationModal extends SemanticComponent {

  /**
   * Declare semantic module for the configuration modal.
   *
   * @override
   * @memberof ConfigurationModal
   */
  public declareSemanticModules(): void {
    $('.ui.dropdown').dropdown();
    $('.tabular.menu .item').tab();
  }

  /**
   * Vue mounted lifecycle.
   *
   * @memberof ConfigurationModal
   */
  public mounted(): void {
    $('#sim_config_generate').click(() => {
      const rsc_val = $('#rsc_txt').val();
      let gen_tbl = '';

      $('#sim_config_tbl').html(gen_tbl);

      let head_cols = '';
      let body_cols = '';

      const cols = rsc_val;
      for (let i = 0; i <= cols; i++) {
        head_cols += '<th>Config ' + (i + 1) + '</th>';
        body_cols += '<td><button class="ui button">...</button></td>';
      }

      gen_tbl += '<table class="ui celled compact table">';
      gen_tbl += '<thead>';
      gen_tbl += '<tr>';
      gen_tbl += '<th>Module</th>';
      gen_tbl += head_cols;
      gen_tbl += '</tr>';
      gen_tbl += '</thead>';
      gen_tbl += '<tbody>';
      gen_tbl += '<tr>';
      gen_tbl += '<td>Start</td>';
      gen_tbl += body_cols;
      gen_tbl += '</tr>';
      gen_tbl += '<tr>';
      gen_tbl += '<td>End</td>';
      gen_tbl += body_cols;
      gen_tbl += '</tr>';
      gen_tbl += '</tbody>';
      gen_tbl += '</table>';

      $('#sim_config_tbl').html(gen_tbl);
    });
  }
}
</script>
