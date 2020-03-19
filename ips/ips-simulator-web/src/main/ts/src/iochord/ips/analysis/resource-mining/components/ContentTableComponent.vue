<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div class="content table component">
    <div class="ui three column grid">
      <div class="column">
        <h2>Activity</h2>
        <ul class="resmMenu">
          <li v-for="(item, key, index) in mactivities" :class="{ 'menu-item': !mactivities[key], lactive: mactivities[key] }">
            <input type="checkbox" v-model="mactivities[key]" @change="setActive(true, key)"/>
            <label>{{key}}</label>
          </li>
        </ul>
      </div>
      <div class="column">
        <h2>Group Unit</h2>
        <ul class="resmMenu">
          <li v-for="(item, key, index) in mgroups" :class="{ 'menu-item': !mgroups[key], lactive: mgroups[key] }">
            <input type="checkbox" v-model="mgroups[key]" @change="setActive(false, key)"/>
            <label>{{key}}</label> 
          </li>
        </ul>
      </div>
      <div class="column">
        <h2>Resource</h2>
        <ul class="resmMenu">
          <li v-for="(item, key, index) in  mresources" :class="{ 'menu-item': !mresources[key], lactive: mresources[key] }">
            <input v-model="mresources[key]" type="checkbox"/>
            <label>{{key}}</label>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style>
ul.resmMenu {
  list-style-type: none;
  display: contents;
}

ul.resmMenu > li {
  padding: 5px;
}

ul.resmMenu > li > input {
  margin-right: 5px;
}

.menu-item:nth-child(odd) {
  background-color: #E5E7E9;
} 

.menu-item:nth-child(even) {
  background-color: #F8F9F9;
}

.lactive {
  background-color: #85C1E9;
}
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
export default class ContentTableComponent extends BaseComponent {

  public mactivities: any = {};
  public mgroups: any = {};
  public mresources: any = {};

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
    const initActivities: any = {};
    const initGroups: any = {};
    const initResources: any = {};

    for (const activity of this.resMiningResult.activities)
       initActivities[activity] = false;
    for (const group of this.resMiningResult.groups)
      initGroups[group] = false;
    for (const resource of this.resMiningResult.resources)
      initResources[resource] = false;

    this.mactivities = initActivities;
    this.mgroups = initGroups;
    this.mresources = initResources;
  }

  protected setActive(isActivity: boolean, value: string) {
    if (isActivity) {
      for (const group in this.mgroups) {
        if (this.resMiningResult.mactgroup[value].some((x: string) => x === group)) {
          let needToggle = true;
          if (!this.mactivities[value])
          for (const act of this.resMiningResult.mgroupact[group]) {
            if (this.mactivities[act]) {
              needToggle = false;
              break;
            }
          }
          if (needToggle) {
            this.mgroups[group] = this.mactivities[value];
            this.setActive(false, group);
          }
        }
      }
    } else {
      for (const resource in this.mresources) {
      if (this.resMiningResult.mgroupres[value].some((x: string) => x === resource)) {
        let needToggle = true;
        if (!this.mgroups[value])
        for (const grp of this.resMiningResult.mresgroup[resource]) {
          if (this.mgroups[grp])
            needToggle = false;
        }
        if (needToggle)
          this.mresources[resource] = this.mgroups[value];
      }
    }
    }
  }
}
</script>
