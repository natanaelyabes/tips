<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div class="content table component">
    <div class="ui three column grid">
      <div class="column">
        Activity
        <ul>
          <li v-for="(activity, i) in resMiningResult.activities" :key="i + activity">{{activity}}</li>
        </ul>
      </div>
      <div class="column">
        Group Unit
        <ul>
          <li v-for="(group, i) in resMiningResult.groups" :key="i + group">{{group}}</li>
        </ul>
      </div>
      <div class="column">
        Resource
        <ul>
          <li v-for="(resource, i) in resMiningResult.resources" :key="i + resource">{{resource}}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
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
export default class ContentTableComponent extends BaseComponent {

  /**
   * Object result from resource mining
   *
   * @type {ResourceMiningResult}
   * @memberof AnalysisResourceMining
   */
  @Prop({default: {}})
  public resMiningResult!: ResourceMiningResult;
}
</script>
