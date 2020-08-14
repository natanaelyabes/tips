<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
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
            <div class="four wide column">Type</div>
            <div class="twelve wide column">
              <select v-model="type" id="generator_txt_type" class="ui fluid search dropdown">
                <option v-for="objectType in objectTypes" :key="objectType.getId()" :value="objectType.getId()">{{objectType.getLabel()}} ({{objectType.getId()}})</option>
              </select>
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
              <input type="text" v-model="distributionType" id="x_txt_dist_type">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Expression</div>
            <div class="twelve wide column">
              <input type="text" v-model="expression" id="x_txt_expr">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Unit</div>
            <div class="twelve wide column">
              <input type="text" v-model="unit" id="x_txt_unit">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Entities per Arrival</div>
            <div class="twelve wide column">
              <input type="text" v-model="entities" id="x_txt_ent">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Max Arrivals</div>
            <div class="twelve wide column">
              <input type="text" v-model="maxArrival" id="x_txt_max_arr">
            </div>
          </div>
          <div class="row">
            <div class="four wide column">First Creation</div>
            <div class="twelve wide column">
              <input type="text" v-model="firstCreation" id="x_txt_creation">
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

<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import { TSMap } from 'typescript-map';
import { getModule } from 'vuex-module-decorators';
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { Modal } from '../../interfaces/Modal';
import { GraphDataGeneratorImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphDataGeneratorImpl';
import { GraphData } from '@/iochord/ips/common/graphs/ism/interfaces/GraphData';
import { DISTRIBUTION_TYPE } from '@/iochord/ips/common/graphs/ism/enums/DISTRIBUTION';
import { TIME_UNIT } from '@/iochord/ips/common/graphs/ism/enums/TIME_UNIT';
import { GraphDataImpl } from '@/iochord/ips/common/graphs/ism/class/GraphDataImpl';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';

const graphModule = getModule(GraphModule);

declare const $: any;

@Component

/**
 * The generator data modal.
 *
 * @class GeneratorDataModal
 * @extends {SemanticComponent}
 * @implements {Modal<JointGraphPageImpl, GraphDataGeneratorImpl>}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class GeneratorDataModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataGeneratorImpl> {

  /**
   * The object of graph data generator.
   *
   * @private
   * @type {GraphDataGeneratorImpl}
   * @memberof GeneratorDataModal
   */
  private properties!: GraphDataGeneratorImpl;

  /**
   * The graph page object.
   *
   * @private
   * @type {JointGraphPageImpl}
   * @memberof GeneratorDataModal
   */
  private page!: JointGraphPageImpl;

  /**
   * The label of generator data modal.
   *
   * @private
   * @type {string}
   * @memberof GeneratorDataModal
   */
  private label: string = '';

  /**
   * The type of generator data modal.
   *
   * @private
   * @type {string}
   * @memberof GeneratorDataModal
   */
  private type: string = '';

  /**
   * The distribution model of the generator.
   *
   * @private
   * @type {DISTRIBUTION_TYPE}
   * @memberof GeneratorDataModal
   */
  private distributionType: DISTRIBUTION_TYPE = DISTRIBUTION_TYPE.RANDOM;

  /**
   * The expression for distribution type of the generator.
   *
   * @private
   * @type {string}
   * @memberof GeneratorDataModal
   */
  private expression: string = '';

  /**
   * The time unit settings for the generator.
   *
   * @private
   * @type {TIME_UNIT}
   * @memberof GeneratorDataModal
   */
  private unit: TIME_UNIT = TIME_UNIT.MINUTES;

  /**
   * The number of entities of the generator.
   *
   * @private
   * @type {number}
   * @memberof GeneratorDataModal
   */
  private entities: number = 0;

  /**
   * The number of maximum arrival of the generator.
   *
   * @private
   * @type {number}
   * @memberof GeneratorDataModal
   */
  private maxArrival: number = 0;

  /**
   * Number of first created entities for the generator.
   *
   * @private
   * @type {number}
   * @memberof GeneratorDataModal
   */
  private firstCreation: number = 0;

  /**
   * Assigns the properties of generator data node to the generator data modal properties.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphDataGeneratorImpl} object
   * @memberof GeneratorDataModal
   */
  public populateProperties(page: JointGraphPageImpl, object: GraphDataGeneratorImpl) {

    // Object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.type = object.getObjectTypeRef() as string;
    this.distributionType = object.getDistributionType() as DISTRIBUTION_TYPE;
    this.expression = object.getExpression() as string;
    this.unit = object.getUnit() as TIME_UNIT;
    this.entities = object.getEntitiesPerArrival() as number;
    this.maxArrival = object.getMaxArrival() as number;
    this.firstCreation = object.getFirstCreation() as number;

    $('#generator_txt_type')
      .dropdown('set selected', this.type)
      .dropdown({
        onChange: (val: string) => {
          this.type = val;
        },
      })
    ;
  }

  /**
   * Store the properties into generator data object, commit to vuex store.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphDataGeneratorImpl} object
   * @memberof GeneratorDataModal
   */
  public saveProperties(page: JointGraphPageImpl, object: GraphDataGeneratorImpl) {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataGeneratorImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataGeneratorImpl;

    // Save properties
    data.setLabel(this.label);
    data.setObjectTypeRef(this.type);
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

  /**
   * Returns the object type of generator data modal.
   *
   * @readonly
   * @type {GraphData[]}
   * @memberof GeneratorDataModal
   */
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
