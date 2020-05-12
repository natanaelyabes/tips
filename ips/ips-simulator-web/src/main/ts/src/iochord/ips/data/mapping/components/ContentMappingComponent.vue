<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content mapping component">
    <div class="ui basic segment">
      <h4 class="ui dividing header">Map Settings</h4>
      <div class="ui warning message">
        The data is a preview. Only the first 100<sup>th</sup> rows is displayed.
      </div>
      <div v-show="datasetId === '---'" class="ui error message">
        Table is not loaded. Please select a dataset.
      </div>
      <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
        <table v-show="datasetId !== '---'" class="ui celled striped table">
          <thead>
            <tr>
              <th>Technical Name</th>
              <th v-for="(name, i) in technicalNames" :key="name  + i">{{ name }}</th>
            </tr>
            <tr>
              <th>Column Name</th>
              <th v-for="(k, v) of colHeaders" :key="k + v">{{ k[1] }}</th>
            </tr>
            <tr>
              <th>Mapping</th>
              <th v-for="(k, v) of mapping.mapSettings" :key="k + v">
                <select v-model="mapping.mapSettings[v]" class="ui fluid search dropdown">
                  <option value="" disabled>Select Mapping</option>
                  <option value="ci">Case ID</option>
                  <option value="ea">Activity</option>
                  <option value="et">Lifecycle</option>
                  <option value="eo">Originator</option>
                  <option value="er">Resource</option>
                  <option value="es">Start Time</option>
                  <option value="ec">Complete Time</option>
                  <!--
                  <option value="case_id">case_id</option>
                  <option value="concept:name">concept:name</option>
                  <option value="time:timestamp">time:timestamp</option>
                  <option value="lifecycle:transition">lifecycle:transition</option>
                  <option value="org:resource">org:resource</option>
                  -->
                </select>
              </th>
            </tr>
          </thead>
          <tbody>
            <colgroup>
              <col span="1" class="selected">
            </colgroup>
            <tr v-for="(row, i) in firstNRows" :key="row + i">
              <td v-if="i === 0" :rowspan="rowsize"></td>
              <td v-for="(col, j) in shifted(Object.values(row))" :key="col + j">
                {{ col }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="ui basic segment" style="padding: 1em 0;">
        <button @click="save" class="ui large blue labeled icon right floated button"
          v-bind:class="{ disabled : datasetId === '---' }">
          <i class="save icon"></i> Save
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Prop, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import ContentDataViewComponent from '../../SandboxDataConnection/components/ContentDataViewComponent.vue';
import { getModule } from 'vuex-module-decorators';
import MappingModule from '../stores/MappingModule';
import MappingResource from '../models/MappingResource';
import MappingService from '../services/MappingService';
import IMappingResource from '../interfaces/IMappingResource';
import SemanticComponent from '../../../common/ui/semantic-components/SemanticComponent';

// Vuex module
const mappingModule = getModule(MappingModule);

declare const $: any;

@Component

/**
 * The content mapping component.
 *
 * @export
 * @class ContentMappingComponent
 * @extends {BaseComponent}
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentMappingComponent extends SemanticComponent {
  public mapping: MappingResource = new MappingResource();
  public rowsize: number = 0;

  @Prop(String)
  public datasetId?: string;

  public mounted(): void {
    this.retreiveMapping();
  }

  public async retreiveMapping(): Promise<void> {
    if (this.datasetId !== '---') {
      await mappingModule.retreiveMapResource(this.datasetId as string);
      this.rowsize = this.firstNRows.length;
      this.mapping.technicalNames = mappingModule.mapResource.technicalNames;
      this.mapping.colHeaders = mappingModule.mapResource.colHeaders;
      this.mapping.mapSettings = mappingModule.mapResource.mapSettings;
      this.mapping.firstNRows = mappingModule.mapResource.firstNRows;
    }
  }

  public get technicalNames(): string[] {
    const technicalNames = mappingModule.mapResource.technicalNames;
    if (technicalNames) technicalNames.shift();
    return technicalNames;
  }

  public get mapSettings(): Map<string, string> {
    const mapSettings = this.mapping.mapSettings;
    let map = new Map<string, string>();
    if (mapSettings) {
      map = new Map<string, string>(Object.entries(mapSettings));
    }
    return map;
  }

  public get colHeaders(): Map<string, string> {
    const colHeaders = this.mapping.colHeaders;
    let map = new Map<string, string>();
    if (colHeaders) {
      map = new Map<string, string>(Object.entries(colHeaders));
    }
    return map;
  }

  public get firstNRows() {
    return this.mapping.firstNRows;
  }

  public shifted(array: string[]) {
    array.shift();
    return array;
  }

  public save(): void {
    if (this.datasetId) {
      MappingService.getInstance().postMappingSettings(this.datasetId, JSON.stringify(this.mapping.toJson()), (res: any) => {
        console.log(res);
      }, (tick: any) => {
        console.log(tick);
      });
    }
  }

  public declareSemanticModules(): void {
    $('.dropdown').dropdown();
  }
}
</script>
