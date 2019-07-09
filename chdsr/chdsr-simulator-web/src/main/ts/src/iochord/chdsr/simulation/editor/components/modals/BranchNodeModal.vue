<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
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
              <input type="text" @change="handleChangedLabel()" v-model="_label" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Gate</div>
            <div class="twelve wide column">
              <select class="ui dropdown" @change="handleSelectedGate()" v-model="_selectedGate">
                <option value>Gate</option>
                <option value="and">AND</option>
                <option value="xor">XOR</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Type</div>
            <div class="twelve wide column">
              <select class="ui dropdown" @change="handleSelectedType()" v-model="_selectedType">
                <option value>Type</option>
                <option value="split">Split</option>
                <option value="join">Join</option>
              </select>
            </div>
          </div>
          <div id="row_branches_rule" class="row" style="padding:0px"></div>
          <div id="row_branches_if" class="row" style="padding:0px"></div>
          <div id="row_branches_tbl" class="row" style="padding:0px"></div>
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
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';

declare const $: any;


/**
 *
 * @package chdsr
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class BranchNodeModal extends SemanticComponent {
  @Prop() private label!: string;
  @Prop() private selectedGate!: string;
  @Prop() private selectedType!: string;
  @Prop() private selectedRule!: string;

  private _label!: string;
  private _selectedGate!: string;
  private _selectedType!: string;
  private _selectedRule!: string;

  private rowBranchesRuleContent!: string;
  private rowBranchesIfContent!: string;
  private rowBranchesTblContent!: string;

  public handleChangedLabel(): void {
    this.$emit('changeLabel', this._label);
  }

  public handleSelectedGate(): void {
    this.$emit('changeSelectedGate', this._selectedGate);

    if (this._selectedGate !== '' && this._selectedType !== '') {
     this.showCondition();
    }
  }

  public handleSelectedType(): void {
    this.$emit('changeSelectedType', this._selectedType);

    if (this._selectedGate !== '' && this._selectedType !== '') {
     this.showCondition();
    }
  }

  public handleSelectedRule(): void {
    this.$emit('changeSelectedRule', this._selectedRule);
  }

  public beforeMount(): void {
    this._label = this.label;
    this._selectedGate = this.selectedGate;
    this._selectedType = this.selectedType;
    this._selectedRule = this.selectedRule;

    this.rowBranchesRuleContent = '';
    this.rowBranchesIfContent = '';
    this.rowBranchesTblContent = '';
  }

  public mounted(): void {
    $('.ui.dropdown').dropdown();
    $('.tabular.menu .item').tab();

    this.rowBranchesRuleContent += '<div class=\'four wide column\'>Rule</div>';
    this.rowBranchesRuleContent += '<div class=\'seven wide column\'>';
    this.rowBranchesRuleContent += '<select class=\'ui dropdown\' @change=\'handleSelectedRule()\' v-model=\'_selectedRule\'>';
    this.rowBranchesRuleContent += '<option value=\'rule\'>Rule</option>';
    this.rowBranchesRuleContent += '<option value=\'data\'>Data</option>';
    this.rowBranchesRuleContent += '<option value=\'probability\'>Probability</option>';
    this.rowBranchesRuleContent += '</select>';
    this.rowBranchesRuleContent += '</div>';

    this.rowBranchesIfContent += '<div class=\'sixteen wide column\'>';
    this.rowBranchesIfContent += '<h4>If</h4>';
    this.rowBranchesIfContent += '</div>';

    this.rowBranchesTblContent += '<div class=\'sixteen wide column\'>';
    this.rowBranchesTblContent += '<table class=\'ui celled compact table\'>';
    this.rowBranchesTblContent += '<thead>';
    this.rowBranchesTblContent += '<tr>';
    this.rowBranchesTblContent += '<th>Condition</th>';
    this.rowBranchesTblContent += '<th>';
    this.rowBranchesTblContent += '<button id=\'btn_add_rule\' class=\'ui positive basic button\'>';
    this.rowBranchesTblContent += '<i class=\'plus icon\'></i>';
    this.rowBranchesTblContent += '</button>';
    this.rowBranchesTblContent += '</th>';
    this.rowBranchesTblContent += '</tr>';
    this.rowBranchesTblContent += '</thead>';
    this.rowBranchesTblContent += '<tbody id=\'tb_add_row\'>';
    this.rowBranchesTblContent += '<tr>';
    this.rowBranchesTblContent += '<td>Case.loan > 5000 and case.bank = \'A\'</td>';
    this.rowBranchesTblContent += '<td>';
    this.rowBranchesTblContent += '<button id=\'btn_del_rule\' class=\'ui negative basic button\'>';
    this.rowBranchesTblContent += '<i class=\'close icon\'></i>';
    this.rowBranchesTblContent += '</button>';
    this.rowBranchesTblContent += '</td>';
    this.rowBranchesTblContent += '</tr>';
    this.rowBranchesTblContent += '</tbody>';
    this.rowBranchesTblContent += '</table>';
    this.rowBranchesTblContent += '</div>';

    $('#btn_add_rule').click(() => {
      let new_row = '<tr>';
      new_row += '<td>Case.loan > 5000 and case.bank = \'A\'</td>';
      new_row += '<td>';
      new_row += '<button id=\'btn_del_rule\' class=\'ui negative basic button\'>';
      new_row += '<i class=\'close icon\'></i>';
      new_row += '</button>';
      new_row += '</td>';
      new_row += '</tr>';
      $('#tb_add_row').append(new_row);
    });

    $('#row_branches_tbl').on('click', '#btn_del_rule', () => {
      $('#btn_del_rule')
        .closest('tr')
        .remove();
    });
  }

  public showCondition(): void {
    const paddingStyle14 = 'padding-top:14px;padding-bottom:14px;';
    const paddingStyle0 = 'padding:0px';

    if (this._selectedGate === 'xor' && this._selectedType === 'split') {
      $('#row_branches_rule').html(this.rowBranchesRuleContent);
      $('#row_branches_rule').attr('style', paddingStyle14);
      $('#row_branches_if').html(this.rowBranchesIfContent);
      $('#row_branches_if').attr('style', paddingStyle14);
      $('#row_branches_tbl').html(this.rowBranchesTblContent);
      $('#row_branches_tbl').attr('style', paddingStyle14);
    } else {
      $('#row_branches_rule').html('');
      $('#row_branches_rule').attr('style', paddingStyle0);
      $('#row_branches_if').html('');
      $('#row_branches_if').attr('style', paddingStyle0);
      $('#row_branches_tbl').html('');
      $('#row_branches_tbl').attr('style', paddingStyle0);
    }
  }
}
</script>