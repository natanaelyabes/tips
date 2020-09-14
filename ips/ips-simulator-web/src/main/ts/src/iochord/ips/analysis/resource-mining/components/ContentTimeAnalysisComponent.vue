<!--
  @package ips
  @author Nur Ichsan Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div id="parentContainer">
    <div id="containerSvg">
      <svg class="svg" :view-box.camel="viewbox" preserveAspectRatio="none">
        <text x="50%" y="15" dominant-baseline="middle" text-anchor="middle" class="title">Cluster : {{ curCluster }}</text>
        <line :x1="xZero" :y1="yMax" :x2="xZero" :y2="yZero" class="lineCoor"/>
        <line :x1="xZero" :y1="yZero" :x2="xMax" :y2="yZero" class="lineCoor"/>
        <text v-for="(n,i) in maxHourEx" :key="'maxHour-' + i" x="0" :y="5+i*heightPerHour" class="small">{{ insp(maxHourEx-1-i) }}</text>
        <template v-if="resMiningResult.timecluster !== null">
          <g v-for="(item, i) in resMiningResult.timecluster[curCluster]" :key="'cluster-' + i">
            <line :x1="startBinX+widthBin*i" :y1="toY(getHourStart(resMiningResult.timeanalysis[item]))" :x2="startBinX+widthBin*i" :y2="toY(getHourStart(resMiningResult.timeanalysis[item])+getDuration(resMiningResult.timeanalysis[item]))" :style="{ 'stroke': resMiningResult.timeanalysis[item][4], 'stroke-width': widthBin-1 }" v-on:click="disp(item,resMiningResult.timeanalysis[item][4])"/>
            <text :x="startBinX+widthBin*i" :y="yZero+1" class="small txtVertical">{{ i+1 }}</text>
          </g>
        </template>
      </svg>
    </div>
    <div id="overlaySelect">
      Choose Cluster
      <select v-model="curCluster">
        <option v-for="option in clusters" :key="'clusterOption-' + option" :value="option">Cluster {{option}}</option>
      </select>
    </div>
  </div>
</template>

<style>
.svg {
  height: 100%;
  width: 100%;
}

.title { 
  font: 6px sans-serif;
  font-weight: bold;
  font-size: small;
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
  public sizeX: number = 1000;
  public sizeY: number = 600;
  public curCluster: number = 0;
  public clusters: any = [];
  public yZero = this.sizeY - 15;
  public xZero = 15;
  public yMax = 0;
  public xMax = this.sizeX;
  public maxHourEx = 48;
  public heightPerHour = (this.yZero - this.yMax) / this.maxHourEx;
  public startBinX = this.xZero + 10;
  public widthBin = 5;
  public wind2: boolean = false;

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
    if (this.resMiningResult.timecluster !== null) {
      const objectkeys: any = Object.keys(this.resMiningResult.timecluster);
      if (objectkeys.length > 0)
        this.curCluster = objectkeys[0];
      this.clusters = objectkeys;
    }
  }

  public disp(resource: string, color: string): void {
    alert(resource + ' - ' + color);
  }

  public insp(i: any): string {
    const is = (i > 23 ? (i - 24) : i) + '';
    return is.length === 1 ? '0' + is : is;
  }

  public getHourStart(workinfo: string): number {
    const stHour = Number(workinfo[0].split(' ', 2)[1].split(':', 3)[0]);
    const edHour = Number(workinfo[1].split(' ', 2)[1].split(':', 3)[0]);
    if (this.wind2) {
      if (stHour < edHour)
        return stHour + 24;
      else
        return stHour;
    } else
      return stHour;
  }

  public getDuration(workinfo: string): number {
    return Number(workinfo[2]);
  }

  public toY(hour: number): number {
    return this.yZero - hour * this.heightPerHour;
  }

  get viewbox(): string {
    return '0 0 ' + this.sizeX + ' ' + this.sizeY;
  }

  @Watch('curCluster')
  public onPropertyChanged(value: string, oldValue: string) {
    this.wind2 = false;
    if (this.resMiningResult.timecluster !== null) {
      for (const te of this.resMiningResult.timecluster[value]) {
        const workinfo = this.resMiningResult.timeanalysis[te];
        const st = Number(workinfo[0].split(' ', 2)[1].split(':', 3)[0]);
        const ed = Number(workinfo[1].split(' ', 2)[1].split(':', 3)[0]);
        if (ed < st && st > 20) {
          this.wind2 = true;
          break;
        }
      }
    }
  }
}
</script>
