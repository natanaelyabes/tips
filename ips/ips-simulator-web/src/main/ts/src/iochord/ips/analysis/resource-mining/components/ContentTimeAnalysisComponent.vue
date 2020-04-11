<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div class="content time analysis component">
    <pre>
    {{ maptimes }}
    </pre>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import ResourceMiningResult from '../models/ResourceMiningResult';

@Component

/**
 * Table page to provide a side by side
 * view of result from resource mining.
 *
 * @extends BaseComponent
 * @package ips
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
export default class ContentTimeAnalysisComponent extends BaseComponent {

  public maptimes: any = {};

  /**
   * Object result from resource mining
   *
   * @type {ResourceMiningResult}
   * @memberof AnalysisResourceMining
   */
  @Prop({default: {}})
  public resMiningResult!: ResourceMiningResult;

  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisResourceMining
   */
  public mounted(): void {
    const temp: any = {};
    for (const cluster of Object.keys(this.resMiningResult.timecluster)) {
      const features: any = {};
      for (const origshift of this.resMiningResult.timecluster[cluster]) {
        features[origshift] = this.resMiningResult.timeanalysis[origshift];
      }
      temp['Cluster-' + cluster] = features;
    }
    this.maptimes = temp;
  }
}
</script>
