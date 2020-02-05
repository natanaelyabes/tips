<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="ui overlay fullscreen data table modal">
    <i class="close icon"></i>

    <div class="header">
      <h3 class="ui green header">Data Table</h3>
    </div>

    <div class="scrolling content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" v-model="label" id="datatable_txt_label">
            </div>
          </div>
        </div>
      </div>

      <div class="ui basic segment" id="datatable">
        <table class="ui definition celled table">
          <thead>
            <tr>
              <th>Header</th>
              <th v-for="(header, i) in matrix.header" :key="i" contenteditable>
                {{ header }}
                <button @click = "matrix.deleteField(i)" class="ui red circle icon button" style="float:right;">
                  <i class="minus icon"></i>
                </button>
              </th>
              <th rowspan="2">
                <center>
                  <button @click = "matrix.insertNewFields()" class="ui blue icon button">
                    <i class="plus icon"></i>
                  </button>
                </center>
              </th>
            </tr>
            <tr>
              <th>Type</th>
              <th v-for="(type, j) in matrix.type" :key="j">
                <select :id="'type-' + j" v-model="matrix.type[j]" class="ui search fluid dropdown">
                  <option value="" disabled>Type</option>
                  <option value="String">String</option>
                  <option value="Int">Int</option>
                  <option value="Double">Double</option>
                  <option value="Boolean">Boolean</option>
                </select>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(d, k) in matrix.data" :key="k">
              <td>
                <button @click = "matrix.deleteRow(k)" class="ui red circle icon button" style="float:right">
                  <i class="minus icon"></i>
                </button>
              </td>
              <td v-for="(v, l) in matrix.data[k]" :key="l" contenteditable>{{ v }}</td>
            </tr>
            <tr>
              <td></td>
              <td :colspan="matrix.type.length">
                <button @click = "matrix.insertNewRow()" class="ui fluid blue icon button">
                  <i class="plus icon"></i>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="actions">
      <div @click = "saveProperties(page, properties)" class="ui positive button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<style scoped>
#datatable{
  padding-left: 0;
  padding-right: 0;
}
</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import SemanticComponent from '../../../../common/ui/semantic-components/SemanticComponent';
import { Modal } from '../../interfaces/Modal';
import { JointGraphPageImpl } from '../../../../common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { GraphDataTableImpl } from '../../../../common/graph/ism/class/components/GraphDataTableImpl';
import { TSMap } from 'typescript-map';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import { getModule } from 'vuex-module-decorators';
import { GraphDataImpl } from '@/iochord/ips/common/graph/ism/class/GraphDataImpl';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';

declare const $: any;

const graphModule = getModule(GraphModule);

enum Type {
  String = 'String',
  Int = 'Int',
  Double = 'Double',
  Boolean = 'Boolean',
}

class Matrix {
  public header: string[] = [];
  public type: Type[] = [];
  public data: any[][] = [];

  constructor(fields?: TSMap<string, string>, type?: TSMap<string, Type>, data?: TSMap<string, TSMap<string, any>>) {
    if (fields !== undefined || data !== undefined) {
      this.header = fields!.values();
      this.type = type!.values();
      data!.values().forEach((value) => this.data.push(value.values()));
    }
  }

  public initDummyData(): void {
    this.header = ['New Field 1', 'New Field 2'];
    this.type = [Type.String, Type.String];
    this.data = [
      ['New Data 1', 'New Data 2'],
      ['New Data 1', 'New Data 2'],
    ];
  }

  public insertNewRow(): void {
    const newRow = new Array<any>(this.data[0].length);
    this.type.forEach((t, i) => {
      switch (t) {
        case Type.String:
          newRow[i] = 'New Data ' + (i + 1);
          break;
        case Type.Int: case Type.Double:
          newRow[i] = 0;
          break;
        case Type.Boolean:
          newRow[i] = false;
          break;
      }
    });
    this.data.push(newRow);
  }

  public deleteRow(i: number) {
    this.data.splice(i, 1);
  }

  public insertNewFields(): void {
    this.header[this.header.length] = 'New Field ' + (this.header.length + 1);
    this.type[this.type.length] = Type.String;
    this.data.forEach((d, i) => d[d.length] = 'New Data ' + (d.length + 1));
    this.data.push();
  }

  public deleteField(i: number) {
    this.header.splice(i, 1);
    this.type.splice(i, 1);
    this.data.forEach((d) => d.splice(i, 1));
  }

  public toMap(page: JointGraphPageImpl): { fields: TSMap<string, string>, data: TSMap<string, TSMap<string, any>> } {
    const fields = new TSMap<string, string>();
    const data = new TSMap<string, TSMap<string, any>>();

    this.header.forEach((h, i) => {
      fields.set(`${page.getId()}-field-${i}`, h);
    });

    this.data.forEach((datum, j) => {
      const value = new TSMap<string, any>();
      datum.forEach((d, k) => {
        data.set(`${page.getId()}-data-${j}`, value.set(`${page.getId()}-field-${k}`, d));
      });
    });

    return { fields, data };
  }
}

@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class DataTableModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataTableImpl> {

  private properties!: GraphDataTableImpl;

  private page!: JointGraphPageImpl;

  private label: string = '';
  private fields: TSMap<string, string> = new TSMap<string, string>();
  private type: TSMap<string, Type> = new TSMap<string, Type>();
  private data: TSMap<string, TSMap<string, string>> = new TSMap<string, TSMap<string, string>>();

  private matrix: Matrix = new Matrix();

  public populateProperties(page: JointGraphPageImpl, object: GraphDataTableImpl): void {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.fields = object.getFields() !== undefined ? object.getFields() as TSMap<string, string> : this.fields;
    this.fields.forEach((field) => this.type.set(field, Type.String));
    this.data = object.getData() !== undefined ? object.getData() as TSMap<string, TSMap<string, string>> : this.data;

    // Re-initialize Matrix
    this.matrix = new Matrix(this.fields, this.type, this.data);

    // If fields and data is empty, generate dummy data
    if (this.fields.length === 0 || this.data.length === 0) {
      this.matrix.initDummyData();
    }
  }

  public saveProperties(page: JointGraphPageImpl, object: GraphDataTableImpl): void {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataTableImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataTableImpl;

    const toMap = this.matrix.toMap(page);

    this.fields = toMap.fields;
    this.data = toMap.data;

    // Save properties
    data.setLabel(this.label);
    data.setFields(this.fields);
    data.setData(this.data);

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
    GraphDataImpl.instance.set(data.getId() as string, GraphDataTableImpl.deserialize(data) as GraphData);

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

  public getFields(): string[][] {
    return this.fields.entries();
  }

  public getDataRows(): Array<Array<string | TSMap<string, string>>> {
    return this.data.entries();
  }

  public getDataCols(): string[][] {
    return this.getDataRows().map((row) => (row[1] as TSMap<string, string>).entries())[0];
  }

  public declareSemanticModules(): void {
    $('.ui.dropdown').dropdown();
  }
}
</script>
