<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny function modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Function</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="four wide column">Name</div>
            <div class="twelve wide column">
              <input v-model="label" type="text" id="x_fn_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Input Parameters</div>
            <div class="twelve wide column">
              <select id="x_input" v-model="inputParameters" class="ui fluid search dropdown" multiple>
                <option v-for="objectType in objectTypes" :key="objectType.getId()" :value="objectType.getId()">{{objectType.getLabel()}} ({{objectType.getId()}})</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Code Segment</h4>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <textarea v-model="code"></textarea>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Output Variables</div>
            <div class="twelve wide column">
              <select id="x_output" v-model="outputVariables" class="ui fluid search dropdown" multiple>
                <option v-for="objectType in objectTypes" :key="objectType.getId()" :value="objectType.getId()">{{objectType.getLabel()}} ({{objectType.getId()}})</option>
              </select>
            </div>
          </div>
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
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { TSMap } from 'typescript-map';
import { getModule } from 'vuex-module-decorators';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphDataFunctionImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphDataFunctionImpl';
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

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class FunctionDataModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataFunctionImpl> {

  // Whole object properties
  private properties!: GraphDataFunctionImpl;

  // Page renderer
  private page!: JointGraphPageImpl;

  // Component properties
  private label: string = '';
  private inputParameters: string[] = [];
  private code: string = '';
  private outputVariables: string[] = [];

  public populateProperties(page: JointGraphPageImpl, object: GraphDataFunctionImpl) {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.code = object.getCode() as string;

    this.inputParameters = object.getInputParametersRefs()!.values();
    this.outputVariables = object.getOutputVariablesRefs()!.values();

    $('.ui.dropdown#x_input')
      .dropdown('set selected', this.inputParameters)
      .dropdown({
        onChange: (val: string[]) => {
          this.inputParameters = val;
        },
      },
    );

    $('.ui.dropdown#x_output')
      .dropdown('set selected', this.outputVariables)
      .dropdown({
        onChange: (val: string[]) => {
          this.outputVariables = val;
        },
      },
    );
  }

  public saveProperties(page: JointGraphPageImpl, object: GraphDataFunctionImpl) {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataFunctionImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataFunctionImpl;

    // Save properties
    data.setLabel(this.label);
    data.setCode(this.code);

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
    GraphDataImpl.instance.set(data.getId() as string, GraphDataFunctionImpl.deserialize(data) as GraphData);

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

  public get objectTypes(): GraphData[] {
    let objectTypes;
    try {
      const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
      const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
      objectTypes = nodeData.values().filter((value: GraphData) => value.getType() === 'objecttype');
    } catch (e) {
      objectTypes = e;
    }
    return objectTypes;
  }
}
</script>
