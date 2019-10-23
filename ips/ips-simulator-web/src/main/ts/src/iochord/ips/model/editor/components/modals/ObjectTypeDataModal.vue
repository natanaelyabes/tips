<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
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
            <div class="three wide column">Name</div>
            <div class="thirteen wide column">
              <input type="text" v-model="label" id="object_type_txt_label">
            </div>
          </div>
          <div class="row">
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
import { Modal } from '../../interfaces/Modal';
import { GraphDataObjectTypeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphDataObjectTypeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { TSMap } from 'typescript-map';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';

declare const $: any;

const graphModule = getModule(GraphModule);

/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
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

  public populateProperties(page: JointGraphPageImpl, object: GraphDataObjectTypeImpl) {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
  }

  public saveProperties(page: JointGraphPageImpl, object: GraphDataObjectTypeImpl) {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataObjectTypeImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataObjectTypeImpl;

    // Save properties
    data.setLabel(this.label);

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
  }
}
</script>
