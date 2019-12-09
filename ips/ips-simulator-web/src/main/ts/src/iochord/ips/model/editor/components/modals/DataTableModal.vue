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

      <div id="container">
      </div>
    </div>

    <div class="actions">
      <div @click="saveProperties(page, properties)" class="ui positive button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<style scoped>

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

import '#root/node_modules/jexcel/dist/jexcel.js';
import '#root/node_modules/jsuites/dist/jsuites.js';

import '#root/node_modules/jexcel/dist/jexcel.css';
import '#root/node_modules/jsuites/dist/jsuites.css';

declare const $: any;

const graphModule = getModule(GraphModule);

@Component
export default class DataTableModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataTableImpl> {

  private properties!: GraphDataTableImpl;

  private page!: JointGraphPageImpl;

  private label: string = '';
  private fields: TSMap<string, string> = new TSMap<string, string>();
  private data: TSMap<string, TSMap<string, string>> = new TSMap<string, TSMap<string, string>>();

  private d = [
    ['Jazz', 'Honda', '2019-02-12', '', true, '$ 2.000,00', '#777700'],
    ['Civic', 'Honda', '2018-07-11', '', true, '$ 4.000,01', '#007777'],
  ];

  public populateProperties(page: JointGraphPageImpl, object: GraphDataTableImpl): void {

    const s = document.getElementById('spreadsheet');

    if (s) {
      document.getElementById('container')!.removeChild(s as HTMLElement);
    }

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.fields = object.getFields() !== undefined ? object.getFields() as TSMap<string, string> : this.fields;
    this.data = object.getData() !== undefined ? object.getData() as TSMap<string, TSMap<string, string>> : this.data;

    const spreadsheet = document.createElement('div');
    spreadsheet.id = 'spreadsheet';
    document.getElementById('container')!.appendChild(spreadsheet);

    const jexcel = require('#root/node_modules/jexcel/dist/jexcel.js');
    jexcel(document.getElementById('spreadsheet'), {
      data: this.d,
      columns: [
        { type: 'text', title: 'Car', width: 120 },
        { type: 'dropdown', title: 'Make', width: 200, source: [ 'Alfa Romeo', 'Audi', 'Bmw' ] },
        { type: 'calendar', title: 'Available', width: 200 },
        { type: 'image', title: 'Photo', width: 120 },
        { type: 'checkbox', title: 'Stock', width: 80 },
        { type: 'numeric', title: 'Price', width: 100, mask: '$ #.##,00', decimal: ',' },
        { type: 'color', width: 100, render: 'square' },
      ],
    });
  }

  public saveProperties(page: JointGraphPageImpl, object: GraphDataTableImpl): void {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataTableImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataTableImpl;

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

  public setField(e: Event) {
    const target = e.target;
    const fieldId = (target as Element).id;
    const field = (target as Element).innerHTML;

    this.fields.set(fieldId, field);
  }

  public setData(row: string | TSMap<string, string>, e: Event) {
    const target = e.target;
    const fieldId = (target as Element).id;
    const rowId = row as string;

    this.data.set(rowId, this.data.get(rowId).set(fieldId, (target as Element).innerHTML));
  }

  public createNewEmptyDataTable(): void {

    // Reset fields and data
    this.fields = new TSMap<string, string>();
    this.data = new TSMap<string, TSMap<string, string>>();

    // Create new fields
    this.fields.set('0-field-0', 'New Field 1');
    this.fields.set('0-field-1', 'New Field 2');

    // Set data
    this.data.set('0-data-0', new TSMap<string, string>()
      .set('0-field-0', 'New Data 1')
      .set('0-field-1', 'New Data 2'));
  }

  public addNewField(): void {

    // [["0-data-0"], TSMap] => "0-data-0" => take the last 0
    const key = this.fields.entries()[this.fields.length - 1][0].toString();
    const id = parseInt(key[key.length - 1], 10) + 1;

    this.fields.set(`${this.page.getId()}-field-${id}`, `New Field ${id + 1}`);

    this.data.forEach((datum: TSMap<string, string>) => {
      datum.set(`${this.page.getId()}-data-${id - 1}`, `New Data ${id}`);
    });
  }

  public removeRow(rowId: string): void {
    this.data.delete(rowId);
  }

  public removeField(columnId: string): void {
    this.fields.delete(columnId);
    const id = columnId[columnId.length - 1];

    this.data.forEach((datum: TSMap<string, string>) => {
      datum.delete(`${this.page.getId()}-data-${id}`);
    });
  }

  public addNewRow(): void {

    const fields = new TSMap<string, string>();

    this.fields.forEach((value, k, index) => {
      fields.set(k as string, `New Data ${index as number + 1}`);
    });

    // [["0-data-0"], TSMap] => "0-data-0" => take the last 0
    const key = this.data.entries()[this.data.length - 1][0].toString();
    const id = parseInt(key[key.length - 1], 10) + 1;

    this.data.set(`${this.page.getId()}-data-${id}`, fields);
  }
}
</script>
