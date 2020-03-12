<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div class="content overall component">
    <div class="ui basic segment">
      <div class="ui top attached tabular menu">
	    <a class="item active" data-tab="first">JSON</a>
	    <a class="item" data-tab="second">Diagram</a>
	    <a class="item" data-tab="third">Table</a>
	  </div>
	  <div class="ui bottom attached tab segment active" data-tab="first">
	    <div v-if="isEmpty(this.resMiningResult)">
  			Please calculate the resource mining in the settings menu to see the result
		</div>
		<div v-else>
  			<pre>{{this.resMiningResult}}</pre>
		</div>
	  </div>
	  <div class="ui bottom attached tab segment" data-tab="second">
	    <div v-if="isEmpty(this.resMiningResult)">
  			Please calculate the resource mining in the settings menu to see the result
		</div>
	    <div v-else>
  			<ContentGraphComponent :resMiningResult='this.resMiningResult'></ContentGraphComponent>
		</div>
	  </div>
	  <div class="ui bottom attached tab segment" data-tab="third">
	    <div v-if="isEmpty(this.resMiningResult)">
  			Please calculate the resource mining in the settings menu to see the result
		</div>
	    <div v-else>
  			<ContentTableComponent :resMiningResult='this.resMiningResult'></ContentTableComponent>
		</div>
	  </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import ContentGraphComponent from './ContentGraphComponent.vue';
import ContentTableComponent from './ContentTableComponent.vue';
import ResourceMiningResultModule from '../store/modules/ResourceMiningResultModule';
import ResourceMiningResult from '../models/ResourceMiningResult';
import { getModule } from 'vuex-module-decorators';

const resourceMiningResultModule = getModule(ResourceMiningResultModule);

@Component({
  components: {
    ContentGraphComponent,
    ContentTableComponent,
  },
})

/**
 * Overall page to provide a side by side
 * view of result from resource mining.
 *
 * @extends BaseComponent
 * @package ips
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
export default class ContentOverallComponent extends BaseComponent {

  /**
   * Dataset Id field for selecting event log dataset.
   *
   * @type {string}
   * @memberof AnalysisResourceMining
   */
  @Prop({default: ''})
  public datasetId!: string;

  /**
   * ResourceMiningResult
   *
   * @type {ResourceMiningResult}
   * @memberof AnalysisResourceMining
   */
  public resMiningResult: ResourceMiningResult = {} as ResourceMiningResult;

  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisResourceMining
   */
  public mounted(): void {
    this.resMiningResult = resourceMiningResultModule.mapResmResult[this.datasetId];
    let elem: any;
    elem = $('.menu .item');
    elem.tab();
  }

  public isEmpty(obj: any) {
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        return false;
      }
    }
    return true;
  }
}
</script>
