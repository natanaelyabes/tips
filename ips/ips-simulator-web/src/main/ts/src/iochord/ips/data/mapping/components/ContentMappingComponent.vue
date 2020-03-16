<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content mapping component">
    <h4 class="ui dividing header">Map Settings</h4>
    <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
      <table class="ui celled striped table">
        <thead>
          <tr>
            <th>Technical Name</th>
            <th v-for="(name, i) in technicalNames" :key="name  + i">{{ name }}</th>
          </tr>
          <tr>
            <th>Column Name</th>
            <th v-for="(settings, i) in mapSettings" :key="settings + i">{{ settings }}</th>
          </tr>
          <tr>
            <th>Mapping</th>
            <th v-for="(settings, i) in mapSettings" :key="settings + i">
              <select v-model="mapSettings[i]" class="ui fluid search dropdown">
                <option value="" disabled>Select Mapping</option>
                <option value="case_id">case_id</option>
                <option value="concept:name">concept:name</option>
                <option value="time:timestamp">time:timestamp</option>
                <option value="lifecycle:transition">lifecycle:transition</option>
                <option value="org:resource">org:resource</option>
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
      <button class="ui large blue labeled icon right floated button"><i class="save icon"></i> Save</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
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
  public mapping?: IMappingResource;
  public rowsize: number = 0;
  public async mounted(): Promise<void> {
    await mappingModule.retreiveMapResource('ips_dataset_1584089402853');
    this.rowsize = this.firstNRows.length;

    this.declareSemanticModules();
  }

  public get technicalNames(): string[] {
    const technicalNames = mappingModule.mapResource.technicalNames;
    if (technicalNames) technicalNames.shift();
    return technicalNames;
  }

  public get mapSettings(): string[] {
    const mapSettings = mappingModule.mapResource.mapSettings;
    const settings: string[] = [];
    if (mapSettings) {
      mapSettings.shift();
      mapSettings.forEach((value: Map<string, string>) => {
        const setting = Object.values(value)[0];
        settings.push(setting);
      });
    }
    return settings;
  }

  public get firstNRows() {
    return mappingModule.mapResource.firstNRows;
  }

  public shifted(array: string[]) {
    array.shift();
    return array;
  }

  public declareSemanticModules(): void {
    $('.dropdown').dropdown();
  }
}
</script>
