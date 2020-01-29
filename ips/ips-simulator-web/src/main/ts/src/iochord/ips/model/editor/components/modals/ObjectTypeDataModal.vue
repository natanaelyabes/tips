<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny object_type modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Object Type</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" v-model="label" id="object_type_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">
              <label for="x_txt_import">Import from table</label>
            </div>
            <div class="twelve wide column">
              <select id="x_txt_table" class="ui fluid search dropdown" v-model="table">
                <option v-for="t in tables" :key="t.getId()" :value="t.getId()">{{t.getLabel()}} ({{t.getId()}})</option>
              </select>
            </div>
          </div>
          <!-- <div class="row">
            <div class="sixteen wide column">
              <h4>Type</h4>
            </div>
          </div>
          <div class="row">
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
            <div class="two wide column">
              <button class="ui button">...</button>
            </div>
            <div class="two wide column">
              <button class="ui positive basic button">
                <i class="plus icon"></i>
              </button>
            </div>
          </div>
          <div class="row">
            <div class="twelve wide column">
              <input type="text" id="x_txt_label">
            </div>
            <div class="two wide column">
              <button class="ui button">...</button>
            </div>
            <div class="two wide column">
              <button class="ui negative basic button">
                <i class="close icon"></i>
              </button>
            </div>
          </div> -->
        </div>
      </div>
    </div>
    <div class="actions">
      <div @click = "saveProperties(page, properties)" class="ui positive button">Save</div>
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
import { Component, Prop, Watch, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { Modal } from '../../interfaces/Modal';
import { GraphDataObjectTypeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphDataObjectTypeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { TSMap } from 'typescript-map';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { GraphDataImpl } from '@/iochord/ips/common/graph/ism/class/GraphDataImpl';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

declare const $: any;

const graphModule = getModule(GraphModule);

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class ObjectTypeDataModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataObjectTypeImpl> {

  // Whole object properties
  private properties!: GraphDataObjectTypeImpl;

  // Page renderer
  private page!: JointGraphPageImpl;

  // Component properties
  private label: string = '';
  private table: string = '';

  public populateProperties(page: JointGraphPageImpl, object: GraphDataObjectTypeImpl) {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.table = object.getTypeRefs() as string;

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

  public saveProperties(page: JointGraphPageImpl, object: GraphDataObjectTypeImpl) {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataObjectTypeImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataObjectTypeImpl;

    // Save properties
    data.setLabel(this.label);
    data.setTypeRefs(this.table);

    // Change label of the renderer data
    page.getGraph().getCells().map((cell: joint.dia.Cell) => {
      if (cell.attributes.dataId === object.getId()) {
        cell.attr({
          label: {
            text: this.label,
          },
        });
      }
    });

    // Update local instance
    GraphDataImpl.instance.set(data.getId() as string, GraphDataObjectTypeImpl.deserialize(data) as GraphData);

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
