<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div id="parentContainer">
    <div id="containerSvg">
      <svg :view-box.camel="viewbox">
        <text :x="-20+(xMax-xZero)/2" y="5" class="title">Cluster : {{ curCluster }}</text>
        <line :x1="xZero" :y1="yMax" :x2="xZero" :y2="yZero" class="lineCoor"/>
        <line :x1="xZero" :y1="yZero" :x2="xMax" :y2="yZero" class="lineCoor"/>
        <text v-for="(n,i) in maxHourEx" x="0" :y="5+i*heightPerHour" class="small">{{ insp(maxHourEx-1-i) }}</text>
        <g v-for="(item, i) in resMiningResult.timecluster[curCluster]">
          <line :x1="startBinX+widthBin*i" :y1="toY(getHourStart(resMiningResult.timeanalysis[item]))" :x2="startBinX+widthBin*i" :y2="toY(getHourStart(resMiningResult.timeanalysis[item])+getDuration(resMiningResult.timeanalysis[item]))" class="binClass" :style="{ 'stroke-width': widthBin-1 }"/>
          <text :x="startBinX+widthBin*i" :y="yZero+1" class="small txtVertical">{{ i+1 }}</text>
        </g>
      </svg>
    </div>
    <div id="overlaySelect">
      Choose Cluster
      <select v-model="curCluster">
        <option v-for="option in clusters" :value="option">Cluster {{option}}</option>
      </select>
    </div>
  </div>
</template>

<style>
.title { 
  font: 6px sans-serif;
  font-weight: bold; 
}

.small { 
  font: 4px sans-serif; 
}

.txtVertical {
  writing-mode: tb;
}

.lineCoor {
  stroke:rgb(174,214,241);
  stroke-width:1
}

.binClass {
  stroke:rgb(88,214,141);
}

.containerSvg {
  height: 100%;
  width: 100%;
  position: absolute;
}

#parentContainer {
  position: relative;
  height: 100%;
  width: 100%;
}

#overlaySelect {
  position: absolute;
  top: 10px;
  right: 10px;
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
export default class ContentTimeAnalysisComponent extends BaseComponent {

  public size: number = 460;
  public curCluster: number = 0;
  public clusters: any = [];
  public heightPerHour = 5;
  public yZero = 240;
  public xZero = 15;
  public yMax = 0;
  public xMax = 460;
  public maxHourEx = 48;
  public startBinX = 20;
  public widthBin = 5;

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
    console.log(this.resMiningResult);
    const objectkeys: any = Object.keys(this.resMiningResult.timecluster);
    if (objectkeys.length > 0)
      this.curCluster = objectkeys[0];
    this.clusters = objectkeys;
  }

  public insp(i: any): string {
    const is = (i > 23 ? (i - 24) : i) + '';
    return is.length === 1 ? '0' + is : is;
  }

  public getHourStart(workinfo: string): number {
    return Number(workinfo[0].split(' ', 2)[1].split(':', 3)[0]);
  }

  public getDuration(workinfo: string): number {
    return Number(workinfo[2]);
  }

  public toY(hour: number): number {
    return this.yZero - hour * this.heightPerHour;
  }

  get viewbox(): string {
    return '0 0 ' + this.size + ' ' + this.size;
  }

  @Watch('curCluster')
  public onPropertyChanged(value: string, oldValue: string) {
    console.log(value);
    console.log(this.resMiningResult.timecluster[value]);
    for (const te of this.resMiningResult.timecluster[value]) {
      console.log(this.resMiningResult.timeanalysis[te]);
    }
  }
}
</script>
