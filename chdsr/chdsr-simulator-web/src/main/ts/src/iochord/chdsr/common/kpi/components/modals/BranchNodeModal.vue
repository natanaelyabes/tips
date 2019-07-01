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
          <div id="row_branches_rule" class="row" style="visibility:hidden">
            <div class="four wide column">Rule</div>
            <div class="twelve wide column">
              <select class="ui dropdown" @change="handleSelectedRule()" v-model="_selectedRule">
                <option value="rule">Rule</option>
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
import { Component, Prop, Vue } from 'vue-property-decorator';
import store from '../../../../../../store';

declare const $: any;
@Component
export default class BranchNodeModal extends Vue {
  @Prop() private label!: string;
  @Prop() private selectedGate!: string;
  @Prop() private selectedType!: string;
  @Prop() private selectedRule!: string;

  private _label!: string;
  private _selectedGate!: string;
  private _selectedType!: string;
  private _selectedRule!: string;

  public handleChangedLabel(): void{
    this.$emit('changeLabel', this._label);
  }

  public handleSelectedGate(): void {
    this.$emit('changeSelectedGate', this._selectedGate);

    if(this._selectedGate !== '' && this._selectedType !== ''){
     this.showCondition();
    }
  }

  public handleSelectedType(): void{
    this.$emit('changeSelectedType', this._selectedType);

    if(this._selectedGate !== '' && this._selectedType !== ''){
     this.showCondition();
    }
  }

  public handleSelectedRule(): void{
    this.$emit('changeSelectedRule', this._selectedRule);
  }

  private update(): void {
    
  }

  private beforeMount(): void {
    this._label = this.label;
    this._selectedGate = this.selectedGate;
    this._selectedType = this.selectedType;
    this._selectedRule = this.selectedRule;
  }

  private mounted(): void {
    //this.selectedGate = 'xor';

    $('.ui.dropdown').dropdown();
    $('.tabular.menu .item').tab();

    $('#btn_add_rule').click(() => {
      let new_row = '<tr>';
      new_row += '<td>Case.loan > 5000 and case.bank = "A"</td>';
      new_row += '<td>';
      new_row += '<button id="btn_del_rule" class="ui negative basic button">';
      new_row += '<i class="close icon"></i>';
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

  public showCondition(): void{
    if (this._selectedGate === 'xor' && this._selectedType === 'split') {      
      $('#row_branches_rule').attr('style', 'visibility:visible');
      $('#row_branches_if').attr('style', 'visibility:visible');
      $('#row_branches_tbl').attr('style', 'visibility:visible');
    } else {      
      $('#row_branches_rule').attr('style', 'visibility:hidden');
      $('#row_branches_if').attr('style', 'visibility:hidden');
      $('#row_branches_tbl').attr('style', 'visibility:hidden');
    }
  }
}
</script>