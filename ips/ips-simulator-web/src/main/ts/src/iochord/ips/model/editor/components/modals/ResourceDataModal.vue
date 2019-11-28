<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny resource modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Resource</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Label</div>
            <div class="twelve wide column">
              <input type="text" v-model="label" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Number of resources</div>
            <div class="twelve wide column">
              <input type="number" v-model="numberOfResource" id="x_txt_resources">
            </div>
            <!-- <div class="four wide column">
              <label for="x_txt_import">Import from table</label>
            </div>
            <div class="twelve wide column">
              <select id="x_txt_table" class="ui search dropdown" v-model="table">
                <option v-for="t in tables" :key="t.getId()" :value="t.getId()">{{t.getLabel()}} ({{t.getId()}})</option>
              </select>
            </div> -->
          </div>
        </div>
      </div>
    </div>
    <div class="actions">
      <div @click="saveProperties(page, properties)" class="ui positive button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<style>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { Modal } from '../../interfaces/Modal';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { TSMap } from 'typescript-map';
import { getModule } from 'vuex-module-decorators';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphDataResourceImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphDataResourceImpl';
import { GraphDataImpl } from '@/iochord/ips/common/graph/ism/class/GraphDataImpl';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

const graphModule = getModule(GraphModule);

declare const $: any;


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class ResourceDataModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataResourceImpl> {

  // Whole object properties
  private properties!: GraphDataResourceImpl;

  // Page renderer
  private page!: JointGraphPageImpl;

  // Component properties
  private id: string = '';
  private label: string = '';
  private numberOfResource: number = 0;
  private importTable: boolean = false;
  private table: string = '';

  public populateProperties(page: JointGraphPageImpl, object: GraphDataResourceImpl) {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.id = object.getGroupId() as string;
    this.label = object.getLabel() as string;
    this.numberOfResource = object.getNumberOfResource() as number;
    this.importTable = object.isImported() as boolean;
    this.table = object.getDataRef() as string;

    // Initialize dropdown with default value
    $('#x_txt_table')
      .dropdown('set selected', this.table)
      .dropdown({
        onChange: (val: string) => {
          this.table = val;
        },
      })
    ;
  }

  public saveProperties(page: JointGraphPageImpl, object: GraphDataResourceImpl) {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataResourceImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataResourceImpl;

    // Save properties
    data.setGroupId(this.id);
    data.setLabel(this.label);
    data.setImported(this.importTable);
    data.setDataRef(this.table);
    data.setNumberOfResource(this.numberOfResource);

    // Change label of the renderer data
    page.getGraph().getCells().map((cell: joint.dia.Cell) => {
      if (cell.attributes.dataId === object.getId()) {
        cell.attr({
          label: {
            text: this.id,
          },
        });
      }
    });

    // Update local instance
    GraphDataImpl.instance.set(data.getId() as string, GraphDataResourceImpl.deserialize(data) as GraphData);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Pop up toast
    ($('body') as any).toast({
      position: 'bottom right',
      class: 'info',
      className: { toast: 'ui message' },
      message: `${object.getId()} properties have been saved`,
      newestOnTop: true,
    });
  }

  public get tables(): GraphData[] {
    let table;
    try {
      const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
      const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
      table = nodeData.values().filter((value: GraphData) => value.getType() === 'datatable');
      return table;
    } catch (e) {
      table = e;
    }
    return table;
  }
}
</script>
