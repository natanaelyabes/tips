<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny generator modal">
    <i class="close icon"></i>
    <div id="x_title" class="ui red header">
      <h3 class="ui green header">Generator</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" v-model="label" id="generator_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Object</h4>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Data</div>
            <div class="nine wide column">
              <input type="text" v-model="data" id="x_txt_label">
            </div>
            <div class="three wide column">
              <button class="ui button">...</button>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Arrival Rate</h4>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Distribution Type</div>
            <div class="twelve wide column">
              <input type="text" v-model="distributionType" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Expression</div>
            <div class="twelve wide column">
              <input type="text" v-model="expression" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Unit</div>
            <div class="twelve wide column">
              <input type="text" v-model="unit" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Entities per Arrival</div>
            <div class="twelve wide column">
              <input type="text" v-model="entities" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Max Arrivals</div>
            <div class="twelve wide column">
              <input type="text" v-model="maxArrival" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">First Creation</div>
            <div class="twelve wide column">
              <input type="text" v-model="firstCreation" id="x_txt_label">
            </div>
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
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { TSMap } from 'typescript-map';
import { getModule } from 'vuex-module-decorators';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { Modal } from '../../interfaces/Modal';
import { GraphDataGeneratorImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphDataGeneratorImpl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { DISTRIBUTION_TYPE } from '@/iochord/ips/common/graph/ism/enums/DISTRIBUTION';
import { TIME_UNIT } from '@/iochord/ips/common/graph/ism/enums/TIME_UNIT';
import { GraphDataImpl } from '@/iochord/ips/common/graph/ism/class/GraphDataImpl';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

const graphModule = getModule(GraphModule);

declare const $: any;


/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class GeneratorDataModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataGeneratorImpl> {

  // Whole object properties
  private properties!: GraphDataGeneratorImpl;

  // Page renderer
  private page!: JointGraphPageImpl;

  // Component properties
  private label: string = '';
  private data: string = '';
  private distributionType: DISTRIBUTION_TYPE = DISTRIBUTION_TYPE.RANDOM;
  private expression: string = '';
  private unit: TIME_UNIT = TIME_UNIT.MINUTES;
  private entities: number = 0;
  private maxArrival: number = 0;
  private firstCreation: number = 0;

  public populateProperties(page: JointGraphPageImpl, object: GraphDataGeneratorImpl) {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.distributionType = object.getDistributionType() as DISTRIBUTION_TYPE;
    this.expression = object.getExpression() as string;
    this.unit = object.getUnit() as TIME_UNIT;
    this.entities = object.getEntitiesPerArrival() as number;
    this.maxArrival = object.getMaxArrival() as number;
    this.firstCreation = object.getFirstCreation() as number;
  }

  public saveProperties(page: JointGraphPageImpl, object: GraphDataGeneratorImpl) {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataGeneratorImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataGeneratorImpl;

    // Save properties
    data.setLabel(this.label);
    data.setDistributionType(this.distributionType);
    data.setExpression(this.expression);
    data.setUnit(this.unit);
    data.setEntitiesPerArrival(this.entities);
    data.setMaxArrival(this.maxArrival);
    data.setFirstCreation(this.firstCreation);

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
    GraphDataImpl.instance.set(data.getId() as string, GraphDataGeneratorImpl.deserialize(data) as GraphData);

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
}
</script>
