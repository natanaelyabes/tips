<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="content settings component">
    <div class="ui basic segment">
      <div v-if="datasetId !== 'Select a dataset'" class="ui stackable two column grid">
        <div class="column">
          <div class="ui form">
            <div class="ui top attached blue inverted segment">
              <h4>Process Model Discovery</h4>
            </div>
            <div class="ui attached segment" id="discoverGraph">
              <div class="field">
                <label for="discoverGraph[pdPositiveObservationThreshold]">Positive Observation Threshold</label>
                <input type="number" name="decisionTree[pdPositiveObservationThreshold]" id="decisionTree[pdPositiveObservationThreshold]" 
                      v-model=config.pdPositiveObservationThreshold placeholder="0" />
              </div>
              <div class="field">
                <label for="discoverGraph[pdDependencyThreshold]">Dependency Threshold</label>
                <input type="number" name="decisionTree[pdDependencyThreshold]" id="decisionTree[pdDependencyThreshold]" 
                      v-model=config.pdDependencyThreshold placeholder="0" />
              </div>
            </div>
            <div class="field">
              <label for="decisionTree[strategy]">Strategy</label>
              <select class="ui search dropdown" name="decisionTree[strategy]" id="decisionTree[strategy]"
                    v-model="config.strategy">
                <option value="" disabled>Select Strategy</option>
                <option value="ENTROPY">Entropy</option>
                <option value="GINI" selected>Gini</option>
              </select>
            </div>
            <div class="field">
              <label for="decisionTree[splitter]">Splitter</label>
              <select class="ui search dropdown" name="decisionTree[splitter]" id="decisionTree[splitter]" 
                    v-model="config.splitter">
                <option value="" disabled>Select Splitter</option>
                <option value="BEST" selected>Best</option>
                <option value="RANDOM">Random</option>
              </select>
            </div>
            <div class="field">
              <label for="decisionTree[maxDepth]">Max Depth</label>
              <input type="number" name="decisionTree[maxDepth]" id="decisionTree[maxDepth]"
                      v-model=config.maxDepth />
            </div>
            <div class="field">
              <label for="decisionTree[randomState]">Random State</label>
              <input type="number" name="decisionTree[randomState]" id="decisionTree[randomState]"
                      v-model=config.randomState />
            </div>
            <div class="field">
              <label for="decisionTree[trainTestRandomState]">Train/Test Random State</label>
              <input type="number" name="decisionTree[trainTestRandomState]" id="decisionTree[trainTestRandomState]"
                      v-model=config.trainTestRandomState />
            </div>
            <div class="field">
              <label for="decisionTree[testSize]">Test Size (decimal)</label>
              <input type="number" name="decisionTree[testSize]" id="decisionTree[testSize]"
                      v-model=config.testSize />
            </div>
            <div class="ui button primary" @click="performBranchMining">Calculate</div>
          </div>
        </div>
      </div>
      <div v-else>
        <p>Select a dataset id.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.ui.form {
  margin-bottom: 1em;
}

#discoverGraph {
  margin-bottom: 1em;
}
</style>

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

  public performBranchMining() {
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
