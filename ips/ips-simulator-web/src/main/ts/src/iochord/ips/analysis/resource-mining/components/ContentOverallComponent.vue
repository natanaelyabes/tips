<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div class="content settings component">
    <div class="ui basic segment">
      <form class="ui form">
        <div class="field">
          <pre>{{ resMiningResult }}</pre>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import ResourceMiningResultModule from '../store/modules/ResourceMiningResultModule';
import ResourceMiningResult from '../models/ResourceMiningResult';
import { getModule } from 'vuex-module-decorators';

const resourceMiningResultModule = getModule(ResourceMiningResultModule);

@Component

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
  
  public resMiningResult:ResourceMiningResult = {} as ResourceMiningResult;
  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisResourceMining
   */
  public mounted(): void {
    this.resMiningResult = resourceMiningResultModule.resminresult;
  }
}
</script>
