<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny queue modal">
    <i class="close icon"></i>
    <div id="x_title" class="header">
      <h3 class="ui green header">Queue</h3>
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
            <div class="four wide column">Queue Type</div>
            <div class="twelve wide column">
              <select id="x_txt_type" class="ui search dropdown" v-model="type">
                <option value="FIFO">FIFO</option>
                <option value="LIFO">LIFO</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <div class="ui checkbox">
                <input id="x_txt_shared" type="checkbox" v-model="shared">
                <label for="x_txt_shared">Shared buffer</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <h4>Type</h4>
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <div class="ui checkbox">
                <input id="x_txt_single" type="checkbox" v-model="single">
                <label for="x_txt_single">Single</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="four wide column">Queue Size</div>
            <div class="twelve wide column">
              <input type="text" v-model="size" id="x_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <!-- <table class="ui celled compact table">
                <thead>
                  <tr>
                    <th>Sub Queue ID</th>
                    <th>Queue Size</th>
                    <th>Resource ID</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                      <button class="ui negative basic button">
                        <i class="close icon"></i>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table> -->
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
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { QUEUE_TYPE } from '@/iochord/ips/common/graph/ism/enums/QUEUE';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { TSMap } from 'typescript-map';
import { getModule } from 'vuex-module-decorators';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { Modal } from '../../interfaces/Modal';
import { GraphDataQueueImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphDataQueueImpl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
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
export default class QueueDataModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphDataQueueImpl> {

  // Whole object properties
  private properties!: GraphDataQueueImpl;

  // Page renderer
  private page!: JointGraphPageImpl;

  // Component properties
  private label: string = '';
  private type: QUEUE_TYPE = QUEUE_TYPE.FIFO;
  private shared: boolean = false;
  private single: boolean = true;
  private size: number = 0;

  public populateProperties(page: JointGraphPageImpl, object: GraphDataQueueImpl) {

    // Whole object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.type = object.getQueueType() as QUEUE_TYPE;
    this.shared = object.isShared() as boolean;
    this.single = object.isSingle() as boolean;
    this.size = object.getSize() as number;

    $('#x_txt_type')
      .dropdown('set selected', this.type)
      .dropdown({
        onChange: (val: QUEUE_TYPE) => {
          this.type = val;
        },
      })
    ;
  }

  public saveProperties(page: JointGraphPageImpl, object: GraphDataQueueImpl) {
    const dataPageId = (object.getId() as string).split('-')[0];
    const dataPage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(dataPageId);
    const data: GraphDataQueueImpl = (page.getData() as TSMap<string, GraphData>).get(object.getId() as string) as GraphDataQueueImpl;

    // Save properties
    data.setLabel(this.label);
    data.setQueueType(this.type);
    data.setShared(this.shared);
    data.setSingle(this.single);
    data.setSize(this.size);

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
    GraphDataImpl.instance.set(data.getId() as string, GraphDataQueueImpl.deserialize(data) as GraphData);

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
