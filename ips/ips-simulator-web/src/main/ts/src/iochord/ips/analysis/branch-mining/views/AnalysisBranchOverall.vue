<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="sandbox analysis branch">
    <DepthTwoLeftWrapperComponent>

      <!-- Header -->
      <template slot="header-breadcrumb">
        <router-link to="/iochord/ips/home" tag="a" class="section">Home</router-link>
        <i class="right angle icon divider"></i>
        <div class="section">Analysis</div>
        <i class="right angle icon divider"></i>
        <div class="active section">{{this.title}}</div>
        <i class="right angle icon divider"></i>
        <select class="ui floating scrolling dropdown button" ref="datasetSelector" v-model="selectedDatasetId">
          <option value="Select a dataset" selected>Select a dataset</option>
          <option v-for="(ds, i) in datasets" :key="i" class="item" :value="i">{{ds.name}} ({{i}})</option>
        </select>
      </template>

      <!-- Left Sidebar Menu Item -->
      <template slot="depth-one-menu-item">
        <router-link :to="`/iochord/ips/analytics/branch/settings/${datasetId}`"
          tag="a" class="item"><i class="cog icon"></i>Settings</router-link>
        <router-link :to="`/iochord/ips/analytics/branch/mining/${datasetId}`"
          tag="a" class="item active"><i class="chart bar icon"></i>Overall</router-link>
      </template>

      <!-- Content -->
      <template slot="depth-two-menu-item">
        <div class="ui basic segment" id="header">
          <h4>Branching rules</h4>
        </div>
        <div class="ui divider"></div>
        <div class="ui divided items">
          <template v-if="branchMining">
            <a v-for="(branch, i) in branchMining.rule" :key="i" class="item" 
              @click="showDecisionRule(branch.eventName)">{{branch.eventName}}</a>
          </template>
          <template v-else>
            <div class="item">
              <div class="ui basic segment">
                Branch is not exists.
              </div>
            </div>
          </template>
        </div>
      </template>
      <template slot="content">
        <template v-if="branchMining">
          <template v-if="selectedBranchRule === ''">
            <p>Select branch to display the decision model.</p>
          </template>
          <template v-else>
            <ItemProcessModel :branchRule="selectedBranchRule"/>
          </template>
        </template>
        <template v-else>
          <p>Select dataset id and perform branch mining in the settings panel.</p>
        </template>
      </template>
    </DepthTwoLeftWrapperComponent>
  </div>
</template>

<style>
.sandbox.analysis.branch {
  height: 100%;
}

a.section {
  color: white!important;
  text-decoration: underline;
}

.ui.segment {
  margin: 0;
}

#depth-two #header {
  min-width: 260px;
}

#depth-two .ui.divider {
  margin: 0;
}

#depth-two .ui.divided.items {
  margin: 0;
}

#depth-two .item {
  padding: 0;
}

#depth-two a.item {
  color: black;
  padding: 1rem!important;
}

#depth-two a.item:hover {
  background: rgba(0,0,0,.03);
}
</style>

<script lang="ts">
import { Vue, Prop, Component } from 'vue-property-decorator';
import ExplorerLayoutView from '@/iochord/ips/common/ui/layout/class/ExplorerLayoutView';
import DepthTwoLeftWrapperComponent from '@/iochord/ips/common/ui/layout/components/DepthTwoLeftWrapperComponent.vue';
import ContentSettingsComponent from '../components/ContentSettingsComponent.vue';
import { getModule } from 'vuex-module-decorators';
import DataConnectionService from '../../../data/connection/services/DataConnectionService';
import ItemProcessModel from '../components/ItemProcessModel.vue';
import ItemBranchList from '../components/ItemBranchList.vue';
import BranchMiningResult from '../models/BranchMiningResult';
import BranchMiningModule from '../stores/BranchMiningModule';

const branchMiningModule = getModule(BranchMiningModule);

declare const $: any;

@Component({
  components: {
    DepthTwoLeftWrapperComponent,
    ItemProcessModel,
    ItemBranchList,
  },
})

/**
 * Overall page to view side by side comparison
 * between process model and its branching rule.
 *
 * @export
 * @class AnalysisBranchOverall
 * @extends DiffLayoutView
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class AnalysisBranchOverall extends ExplorerLayoutView {

  /**
   * Title field of AnalysisBranchOverall.
   *
   * @type {string}
   * @memberof AnalysisBranchOverall
   */
  public title: string = '';

  /**
   * List of available datasets.
   *
   * @type {any}
   * @memberof AnalysisBranchOverall
   */
  public datasets = {};

  /**
   * The dataset id as retreived from router prop.
   *
   * @type {string}
   * @memberof AnalysisBranchOverall
   */
  @Prop({default: ''})
  public datasetId!: string;

  /**
   * Selected branch rule.
   *
   * @type {string}
   * @memberof AnalysisBranchOverall
   */
  public selectedBranchRule: any = '';

  /**
   * Selected dataset id.
   *
   * @type {string}
   * @memberof AnalysisBranchOverall
   */
  public selectedDatasetId: string = 'Select a dataset';

  /**
   * Branch mining result.
   *
   * @type {any}
   * @memberof AnalysisBranchOverall
   */
  public branchMining: BranchMiningResult = {} as BranchMiningResult;

  /**
   * Override browser properties for AnalysisBranchOverall
   *
   * @override
   * @memberof AnalysisBranchOverall
   */
  public overrideBrowserProperties() {
    this.setDocumentTitle('Data Analysis: Branch');
  }

  /**
   * Override title for AnalysisBranchOverall
   *
   * @override
   * @memberof AnalysisBranchOverall
   */
  public setTitle(): void {
    this.title = `Branch mining`;
  }

  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisBranchOverall
   */
  public mounted(): void {
    this.forceReRender();
    this.branchMining = new BranchMiningResult();
    DataConnectionService.getInstance().getDataConnections((res: any) => {
      this.datasets = res.data;
      if (this.datasetId !== '') {
        this.selectedDatasetId = this.datasetId;
        this.branchMining = branchMiningModule.branches.data as BranchMiningResult;
      }
    }, null);
  }

  public showDecisionRule(branchId: string): void {
    this.selectedBranchRule = this.branchMining.rule
      .filter((branch) => branch.eventName === branchId);
  }

  public declareSemanticModules(): void {
    $('.dropdown').dropdown();
  }
}
</script>
