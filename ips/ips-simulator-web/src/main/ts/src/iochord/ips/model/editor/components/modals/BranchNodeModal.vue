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
              <input type="text" @change="handleChangedLabel()" v-model="tempBranchLabel">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Gate</div>
            <div class="twelve wide column">
              <template v-if="reloaded">
                <select class="ui dropdown" @change="handleSelectedGate()" v-model="tempSelectedGate">
                  <option value="AND">AND</option>
                  <option value="XOR">XOR</option>
                </select>
              </template>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Type</div>
            <div class="twelve wide column">
              <template v-if="reloaded">
                <select class="ui dropdown" @change="handleSelectedType()" v-model="tempSelectedType">
                  <option value="SPLIT">Split</option>
                  <option value="JOIN">Join</option>
                </select>
              </template>
            </div>
          </div>

          <template v-if="tempSelectedGate !== '' && tempSelectedType !== ''">
            <!-- Conditions & Rules -->
            <div id="row_branches_rule" class="row">
              <div class="four wide column">Rule</div>
              <div class="twelve wide column">
                <template v-if="reloaded">
                  <select class="ui dropdown" @change="handleSelectedRule()" v-model="tempSelectedRule">
                    <option value="DATA">Data</option>
                    <option value="PROBABILITY">Probability</option>
                  </select>
                </template>
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
      <p><em>Node properties are automatically saved</em></p>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';

declare const $: any;


/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class BranchNodeModal extends SemanticComponent {
  @Prop() private branchLabel!: string;
  @Prop() private selectedGate!: string;
  @Prop() private selectedType!: string;
  @Prop() private selectedRule!: string;

  private tempBranchLabel: string = '';
  private tempSelectedGate: string = '';
  private tempSelectedType: string = '';
  private tempSelectedRule: string = '';

  private rowBranchesRuleContent: string = '';
  private rowBranchesIfContent: string = '';
  private rowBranchesTblContent: string = '';

  private reloaded: boolean = false;

  @Watch('branchLabel')
  public onChangeBranchLabel(newVal: string): void {
    this.tempBranchLabel = this.branchLabel;
  }

  @Watch('selectedGate')
  public onChangeSelectedGate(newVal: string): void {
    this.tempSelectedGate = this.selectedGate;
  }

  @Watch('selectedType')
  public onChangeSelectedType(newVal: string): void {
    this.tempSelectedType = this.selectedType;
  }

  @Watch('selectedRule')
  public onChangeSelectedRule(newVal: string): void {
    this.tempSelectedRule = this.selectedRule;
  }

  public handleChangedLabel(): void {
    this.$emit('changeBranchLabel', this.tempBranchLabel);
  }

  public handleSelectedGate(): void {
    this.$emit('changeBranchSelectedGate', this.tempSelectedGate);
  }

  public handleSelectedType(): void {
    this.$emit('changeBranchSelectedType', this.tempSelectedType);
  }

  public handleSelectedRule(): void {
    this.$emit('changeBranchSelectedRule', this.tempSelectedRule);
  }

  public beforeMount(): void {
    this.rowBranchesRuleContent = '';
    this.rowBranchesIfContent = '';
    this.rowBranchesTblContent = '';
  }

  public declareSemanticModules(): void {
    $('.ui.dropdown').dropdown();
    $('.tabular.menu .item').tab();
  }

  public mounted(): void {
    this.tempBranchLabel = this.branchLabel;
    this.tempSelectedGate = this.selectedGate;
    this.tempSelectedType = this.selectedType;
    this.tempSelectedRule = this.selectedRule;
  }

  public updated(): void {
    if (!this.reloaded) {
      this.reloaded = true;
    }

    // Only for dropdown values
    this.tempSelectedGate = this.selectedGate;
    this.tempSelectedType = this.selectedType;
    this.tempSelectedRule = this.selectedRule;
  }
}
</script>
