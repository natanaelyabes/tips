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

declare const $: any;

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

  public doBranchMining() {
    if (this.datasetId !== 'Select a dataset') {
      const route = { path: `/iochord/ips/analytics/branch/mining/${this.datasetId}` };
      this.$router.push(route);
    }
  }

  public declareSemanticModules(): void {
    $('.dropdown').dropdown();
  }
}
</script>
