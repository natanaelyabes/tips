<!--
  @package ips
  @author N. I. Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div class="content table component">
    <div class="ui three column grid">
      <div class="column">
        <h2>Activity</h2>
        <ul class="resmMenu">
          <li v-for="(activity, i) in activities" :class="{ 'menu-item': !mactivities[activity], lactive: mactivities[activity] }" :key="activity + i">
            <input type="checkbox" v-model="mactivities[activity]" :change="toggleGroup(activity)"/>
            <label>{{activity}}</label>
          </li>
        </ul>
      </div>
      <div class="column">
        <h2>Group Unit</h2>
        <ul class="resmMenu">
          <li v-for="(group,i) in groups" :class="{ 'menu-item': !mgroups[group], lactive: mgroups[group] }" :key="group + i">
            <input v-model="mgroups[group]" type="checkbox" :change="toggleResource(group)"/>
            <label>{{group}}</label> 
          </li>
        </ul>
      </div>
      <div class="column">
        <h2>Resource</h2>
        <ul class="resmMenu">
          <li v-for="(resource,i) in resources" :class="{ 'menu-item': !mresources[resource], lactive: mresources[resource] }" :key="resource + i">
            <input v-model="mresources[resource]" type="checkbox"/>
            <label>{{resource}}</label>
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
 * @author N. I. Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
export default class ContentTableComponent extends BaseComponent {

  public activities: any = {};
  public groups: any = {};
  public resources: any = {};

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
    this.activities = this.resMiningResult.activities;
    this.groups = this.resMiningResult.groups;
    this.resources = this.resMiningResult.resources;
  }

  public toggleGroup(activity: string): void {
    for (const group of this.groups) {
      if (this.resMiningResult.mactgroup[activity].some((x: string) => x === group)) {
        let needToggle = true;
        if (!this.mactivities[activity])
        for (const act of this.resMiningResult.mgroupact[group]) {
          if (this.mactivities[act]) {
            needToggle = false;
            break;
          }
        }
        if (needToggle) {
          this.mgroups[group] = this.mactivities[activity];
          this.toggleResource(group);
        }
      }
    }
  }

  public toggleResource(group: string): void {
    for (const resource of this.resources) {
      if (this.resMiningResult.mgroupres[group].some((x: string) => x === resource)) {
        let needToggle = true;
        if (!this.mgroups[group])
        for (const grp of this.resMiningResult.mresgroup[resource]) {
          if (this.mgroups[grp])
            needToggle = false;
        }
        if (needToggle)
          this.mresources[resource] = this.mgroups[group];
      }
    }
  }
}
</script>
