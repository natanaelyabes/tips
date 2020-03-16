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
          <label>Algorithms</label>
          <select v-model="config.resMinAlg">
            <option value="def">Default Mining</option>
            <option value="dst">Doing Similar Task</option>
          </select>
        </div>
        <div class="field" v-if="config.resMinAlg == 'dst'">
          <label>Distance Measure</label>
          <select v-model="config.distMesAlg">
            <option value="pcc">Pearson Correlation Coefficient</option>
            <option value="ham">Hamming Distance</option>
            <option value="ho1">Hamming Distance Binary</option>
          </select>
        </div>
        <div class="field" v-if="config.resMinAlg == 'dst'">
          <label>Threshold : {{threshold}}</label>
          <input v-model="sliderValue" type="range" class="slider" min="0" max="100"></input>
        </div>
        <div class="field">
          <button type="button" :disabled="isUploading" class="ui primary button" @click="doMining()">
            Calculate {{uploadStatus}}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style>
#input-slider {
  width: auto;
}

.slider {
  -webkit-appearance: none;
  height: 15px;
  width: 100%;
  background: #d3d3d3;
  border-radius: 5px;
  outline: none;
  opacity: 0.7;
  -webkit-transition: .2s;
  transition: opacity .2s;
}

.slider::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 25px;
  height: 25px;
  border-radius: 50%; 
  background: #4CAF50;
  cursor: pointer;
}

.slider::-moz-range-thumb {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background: #4CAF50;
  cursor: pointer;
}
</style>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import ResourceMiningService, { ResourceMiningConfiguration } from '../service/ResourceMiningService';
import ResourceMiningResultModule from '../store/modules/ResourceMiningResultModule';
import { getModule } from 'vuex-module-decorators';

const resourceMiningResultModule = getModule(ResourceMiningResultModule);

@Component

/**
 * Calculate page to provide a side by side
 * view settings params for resource mining.
 *
 * @extends BaseComponent
 * @package ips
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
export default class ContentSettingsComponent extends BaseComponent {

  /**
   * Dataset Id field for selecting event log dataset.
   *
   * @type {string}
   * @memberof AnalysisResourceMining
   */
  @Prop({default: ''})
  public datasetId!: string;

  /**
   * Slider value tracking
   *
   * @type {number}
   * @memberof ContentSettingsComponent
   */
  public sliderValue: number = 0;

  /**
   * Status to indicate whether current component is in the uploading state. False otherwise.
   *
   * @type {boolean}
   * @memberof ContentSettingsComponent
   */
  public isUploading: boolean = false;

  /**
   * Status to indicate the upload state.
   *
   * @type {string}
   * @memberof ContentSettingsComponent
   */
  public uploadStatus: string = '';

  /**
   * Resource Mining configuration.
   *
   * @type {ResourceMiningConfiguration}
   * @memberof ContentSettingsComponent
   */
  public config: ResourceMiningConfiguration = new ResourceMiningConfiguration();

  /**
   * Threshold variable, watch changing of input sliderValue and modify the value
   *
   * @type {ResourceMiningConfiguration}
   * @memberof ContentSettingsComponent
   */
  get threshold(): number {
    return this.sliderValue / 100;
  }

  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisResourceMining
   */
  public mounted(): void {
    this.config.resMinAlg = 'def';
    this.config.distMesAlg = 'pcc';
  }

  /**
   * Mining resources from web service.
   *
   * @memberof ContentSettingsComponent
   */
  public doMining(): void  {
    const self = this;
    if (self.datasetId === '') {
      alert('Choose datasetId');
    } else {
      self.isUploading = true;
      this.config.datasetId = self.datasetId;
      this.config.threshold = this.threshold;
      ResourceMiningService.getInstance().mineResource(this.config,
        (res: any) => {
          const resmResult: any = {};
          resmResult[self.datasetId] = res.data;
          resourceMiningResultModule.addResminresult( resmResult );
          self.isUploading = false;
          self.uploadStatus = 'Finish';
          self.$router.push({
            path: `/iochord/ips/analytics/resource/mining/${self.datasetId}`,
          });
        }, (tick: any) => {
          self.uploadStatus = tick.progress;
      });
    }
  }
}
</script>
