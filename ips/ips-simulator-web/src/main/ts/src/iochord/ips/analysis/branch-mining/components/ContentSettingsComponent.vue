<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="content settings component">
    <div class="ui basic segment">
      <div class="ui button primary" @click="doBranchMining">Calculate</div>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import BranchMiningService from '../services/BranchMiningService';
import BranchMiningConfiguration from '../models/BranchMiningConfiguration';
import BranchMiningModule from '../stores/BranchMiningModule';
import { getModule } from 'vuex-module-decorators';

declare const $: any;

const branchMiningModule = getModule(BranchMiningModule);

@Component

/**
 * Settings component to set branch mining parameters or properties.
 *
 * @export
 * @class ContentSettingsComponent
 * @extends BaseComponent
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class ContentSettingsComponent extends SemanticComponent {

  /**
   * Dataset Id field for selecting event log dataset.
   *
   * @type {string}
   * @memberof AnalysisResourceMining
   */
  @Prop({default: ''})
  public datasetId!: string;

  /**
   * Branch mining configuration.
   *
   * @type {BranchMiningConfiguration}
   * @memberof ContentSettingsComponent
   */
  public config: BranchMiningConfiguration = new BranchMiningConfiguration();

  public doBranchMining() {
    if (this.datasetId !== 'Select a dataset') {
      this.config.datasetId = this.datasetId;
      branchMiningModule.clearBranch();
      BranchMiningService.getInstance().mineDecision(this.config, (res: any) => {
        branchMiningModule.addBranch(res);
        const route = { path: `/iochord/ips/analytics/branch/mining/${this.datasetId}` };
        this.$router.push(route); // Redirect to overall page
      }, null);
    }
  }

  public declareSemanticModules(): void {
    $('.dropdown').dropdown();
  }
}
</script>
