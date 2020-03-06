<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny activity modal">
    <i class="close icon"></i>
    <div id="activity_modal_title" class="header">
      <h3 class="ui green header">Activity</h3>
    </div>
    <div class="scrolling content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" v-model="label" id="activity_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">

              <!-- Tab menu -->
              <div class="ui top attached tabular menu">
                <a class="active item" data-tab="basic">Basic</a>
                <a class="item" data-tab="processing">Processing</a>
                <a class="item" data-tab="advanced">Advanced</a>
                <a class="item" data-tab="misc">Misc</a>
              </div>

              <!-- Basic tab -->
              <div class="ui bottom attached active tab segment" data-tab="basic">
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Activity Type</div>
                    <div class="twelve wide column">
                      <select id="act_txttype" class="ui fluid search dropdown" v-model="activityType">
                        <option value="STANDARD">Standard</option>
                        <option value="CONCURRENT_BATCH">Concurrent Batch Process</option>
                        <option value="SPLIT_MODULE">Split Module Process</option>
                      </select>
                    </div>
                  </div>

                  <template v-if="activityType === 'STANDARD'">
                    <div id="basic-standard-sm-1" class="row">
                      <div class="four wide column">Resources</div>
                      <div class="twelve wide column">
                        <select v-model="resource" id="act_txtresource" class="ui fluid search dropdown">
                          <option v-for="resource in resources" :key="resource.getId()" :value="resource.getId()">{{resource.getLabel()}} ({{resource.getId()}})</option>
                        </select>
                      </div>
                    </div>
                  </template>

                  <template v-if="activityType === 'CONCURRENT_BATCH'">
                    <div id="basic-cbp-sm-1" class="row">
                      <div class="four wide column">Process Criteria</div>
                      <div class="twelve wide column">
                        <input type="text" id="pc_txt_label">
                      </div>
                    </div>
                    <div id="basic-cbp-sm-2" class="row">
                      <div class="four wide column">Resources</div>
                      <div class="twelve wide column">
                        <select v-model="resource" id="act_txtresource" class="ui fluid search dropdown">
                          <option v-for="resource in resources" :key="resource.getId()" :value="resource.getId()">{{resource.getLabel()}} ({{resource.getId()}})</option>
                        </select>
                      </div>
                    </div>
                    <div id="basic-cbp-sm-3" class="row">
                      <div class="four wide column">Limit</div>
                      <div class="twelve wide column">
                        <input type="text" id="limit_txt_label">
                      </div>
                    </div>
                  </template>

                  <template v-if="activityType === 'SPLIT_MODULE'">
                    <div id="basic-split-sm-1" class="row">
                      <div class="four wide column">Split Criteria</div>
                      <div class="twelve wide column">
                        <input type="text" id="split_criteria_txt_label">
                      </div>
                    </div>
                    <div id="basic-split-sm-2" class="sixteen wide column">
                      <div class="ui checkbox">
                        <input type="checkbox" class="hidden">
                        <label>Report scrap</label>
                      </div>
                    </div>
                    <div id="basic-split-sm-3" class="row">
                      <div class="four wide column">Resources</div>
                      <div class="twelve wide column">
                        <select v-model="resource" id="act_txtresource" class="ui fluid search dropdown">
                          <option v-for="resource in resources" :key="resource.getId()" :value="resource.getId()">{{resource.getLabel()}} ({{resource.getId()}})</option>
                        </select>
                      </div>
                    </div>
                    <div id="basic-split-sm-4" class="row">
                      <div class="four wide column">Split Criteria</div>
                      <div class="twelve wide column">
                        <input type="text" id="split_criteria2_txt_label">
                      </div>
                    </div>
                  </template>

                  <!-- TODO: Phase 2 -->
                  <div class="sixteen wide column">
                    <div class="ui checkbox">
                      <input id="report-statistics" type="checkbox" v-model="report">
                      <label for="report-statistics">Report statistics</label>
                    </div>
                  </div>

                  <div class="row">
                    <div class="four wide column">Custom Monitor</div>
                    <div class="twelve wide column">
                      <input disabled type="text" v-model="customMonitor">
                    </div>
                  </div>

                  <div class="row">
                    <div class="four wide column">Queue</div>
                    <div class="twelve wide column">
                      <select v-model="queue" id="act_txtqueuelabel" class="ui fluid search dropdown">
                        <option v-for="queue in queues" :key="queue.getId()" :value="queue.getId()">{{queue.getLabel()}} ({{queue.getId()}})</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Processing tab -->
              <div class="ui bottom attached tab segment" data-tab="processing">

                <h4>Processing</h4>
                <div class="ui divider"></div>
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Processing Time</div>
                    <div class="twelve wide column">
                      <select id="act_txtprocessing" class="ui fluid search dropdown" v-model="processingTime">
                        <option value="RANDOM">Random</option>
                        <option value="CONSTANT">Constant</option>
                      </select>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Processing Time Parameter</div>
                    <div class="twelve wide column">
                      <input type="text" v-model="processingTimeParameter">
                    </div>
                  </div>
                </div>

                <h4>Setup</h4>
                <div class="ui divider"></div>
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Setup Time</div>
                    <div class="twelve wide column">
                      <select id="act_txtsetup" class="ui fluid search dropdown" v-model="setupTime">
                        <option value="RANDOM">Random</option>
                        <option value="CONSTANT">Constant</option>
                      </select>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Setup Time Parameter</div>
                    <div class="twelve wide column">
                      <input type="text" v-model="setupTimeParameter">
                    </div>
                  </div>
                </div>

                <div class="ui divider"></div>
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Time Unit</div>
                    <div class="twelve wide column">
                      <select id="act_txtunit" class="ui fluid search dropdown" v-model="timeUnit">
                        <option value="HOURS">Hours</option>
                        <option value="MINUTES">Minutes</option>
                        <option value="SECONDS">Seconds</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Advanced tab -->
              <div class="ui bottom attached tab segment" data-tab="advanced">
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Input Type</div>
                    <div class="twelve wide column">
                      <input type="text" v-model="inputType">
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Output Type</div>
                    <div class="twelve wide column">
                      <input type="text" v-model="outputType">
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Function</div>
                    <div class="twelve wide column">
                      <select v-model="this.function" id="act_txtfunction" class="ui fluid search dropdown">
                        <option v-for="fx in functions" :key="fx.getId()" :value="fx.getId()">{{fx.getLabel()}} ({{fx.getId()}})</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>

              <div class="ui bottom attached tab segment" data-tab="misc">
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">
                      Image Icon URL
                    </div>
                    <div class="twelve wide column">
                      <input type="text" v-model="imageIcon">
                      <img :src="imageIcon" alt="">
                    </div>
                  </div>
                </div>
              </div>
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

<style scoped>
.four.wide.column {
  font-weight: bold;
  padding-top: 8px;
  padding-bottom: 8px;
}
</style>

<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphStopEventNodeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphStopEventNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { Modal } from '../../interfaces/Modal';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';

declare const $: any;

import { TSMap } from 'typescript-map';
import { GraphActivityNodeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphActivityNodeImpl';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import { ACTIVITY_TYPE } from '../../../../common/graph/ism/enums/ACTIVITY';
import { DISTRIBUTION_TYPE } from '../../../../common/graph/ism/enums/DISTRIBUTION';
import { TIME_UNIT } from '../../../../common/graph/ism/enums/TIME_UNIT';
import { GraphElement } from '../../../../common/graph/ism/interfaces/GraphElement';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import { NODE_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/NODE';

const graphModule = getModule(GraphModule);

@Component

/**
 * The activity node modal component.
 *
 * @export
 * @class ActivityNodeModal
 * @extends {SemanticComponent}
 * @implements {Modal<JointGraphPageImpl, GraphActivityNodeImpl>}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class ActivityNodeModal extends SemanticComponent implements Modal<JointGraphPageImpl, GraphActivityNodeImpl> {

  /**
   * The graph activity node object.
   *
   * @private
   * @type {GraphActivityNodeImpl}
   * @memberof ActivityNodeModal
   */
  private properties!: GraphActivityNodeImpl;

  /**
   * The graph page object.
   *
   * @private
   * @type {JointGraphPageImpl}
   * @memberof ActivityNodeModal
   */
  private page!: JointGraphPageImpl;

  /**
   * The label of an activity.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private label: string = '';

  /**
   * The type of an activity.
   *
   * @private
   * @type {ACTIVITY_TYPE}
   * @memberof ActivityNodeModal
   */
  private activityType: ACTIVITY_TYPE = ACTIVITY_TYPE.STANDARD;

  /**
   * The resource object as string reference.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private resource: string = '';

  /**
   * A flag to enable reporting towards activity node.
   *
   * @private
   * @type {boolean}
   * @memberof ActivityNodeModal
   */
  private report: boolean = false;

  /**
   * The monitor object as string reference.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private customMonitor: string = '';

  /**
   * The distribution model of processing time.
   *
   * @private
   * @type {DISTRIBUTION_TYPE}
   * @memberof ActivityNodeModal
   */
  private processingTime: DISTRIBUTION_TYPE = DISTRIBUTION_TYPE.RANDOM;

  /**
   * The parameter setup for the specified distribution model of processing time.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private processingTimeParameter: string = '';

  /**
   * The distribution model of setup time.
   *
   * @private
   * @type {DISTRIBUTION_TYPE}
   * @memberof ActivityNodeModal
   */
  private setupTime: DISTRIBUTION_TYPE = DISTRIBUTION_TYPE.RANDOM;

  /**
   * The parameter setup for the specified distribution model of processing time.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private setupTimeParameter: string = '';

  /**
   * The time unit specified for an activity.
   *
   * @private
   * @type {TIME_UNIT}
   * @memberof ActivityNodeModal
   */
  private timeUnit: TIME_UNIT = TIME_UNIT.MINUTES;

  /**
   * The queue object as string reference.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private queue: string = '';

  /**
   * The type of input object as string reference.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private inputType: string = '';

  /**
   * The type of output object as string reference.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private outputType: string = '';

  /**
   * The function object as string reference.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private function: string = '';

  /**
   * The URI of image for activity icon.
   *
   * @private
   * @type {string}
   * @memberof ActivityNodeModal
   */
  private imageIcon: string = '';

  /**
   * Assign activity properties to the field of activity modal.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphActivityNodeImpl} object
   * @memberof ActivityNodeModal
   */
  public populateProperties(page: JointGraphPageImpl, object: GraphActivityNodeImpl): void {

    // Object properties
    this.properties = object;

    // Page renderer
    this.page = page;

    // Component properties
    this.label = object.getLabel() as string;
    this.activityType = object.getActivityType() as ACTIVITY_TYPE;
    this.resource = object.getResourceRef() as string;
    this.report = object.isReportStatistics() as boolean;
    this.processingTime = object.getProcessingTime() as DISTRIBUTION_TYPE;
    this.processingTimeParameter = object.getProcessingTimeParameter() as string;
    this.setupTime = object.getSetupTime() as DISTRIBUTION_TYPE;
    this.setupTimeParameter = object.getSetupTimeParameter() as string;
    this.timeUnit = object.getUnit() as TIME_UNIT;
    this.imageIcon = object.getImageIcon() as string;

    this.queue = object.getQueueRef() as string;
    this.function = object.getFunctionRef() as string;

    // Initialize dropdown with default value
    $('#act_txttype')
      .dropdown('set selected', this.activityType)
      .dropdown({
        onChange: (val: ACTIVITY_TYPE) => {
          this.activityType = val;
        },
      })
    ;

    $('#act_txtresource').dropdown({ clearable: true });
    $('#act_txtresource')
      .dropdown('set selected', this.resource)
      .dropdown({
        onChange: (val: string) => {
          this.resource = val;
        },
      })
    ;

    $('#act_txtprocessing')
      .dropdown('set selected', this.processingTime)
      .dropdown({
        onChange: (val: DISTRIBUTION_TYPE) => {
          this.processingTime = val;
        },
      })
    ;

    $('#act_txtsetup')
      .dropdown('set selected', this.setupTime)
      .dropdown({
        onChange: (val: DISTRIBUTION_TYPE) => {
          this.setupTime = val;
        },
      })
    ;

    $('#act_txtunit')
      .dropdown('set selected', this.timeUnit)
      .dropdown({
        onChange: (val: TIME_UNIT) => {
          this.timeUnit = val;
        },
      })
    ;

    $('#act_txtqueuelabel').dropdown({ clearable: true });
    $('#act_txtqueuelabel')
      .dropdown('set selected', this.queue)
      .dropdown({
        onChange: (val: string) => {
          this.queue = val;
        },
        clearable: true,
      })
    ;

    $('#act_txtfunction').dropdown({ clearable: true });
    $('#act_txtfunction')
      .dropdown('set selected', this.function)
      .dropdown({
        onChange: (val: string) => {
          this.function = val;
        },
        clearable: true,
      })
    ;
  }

  /**
   * Store the properties into activty node object, commit to the graph vuex module.
   *
   * @param {JointGraphPageImpl} page
   * @param {GraphActivityNodeImpl} object
   * @memberof ActivityNodeModal
   */
  public saveProperties(page: JointGraphPageImpl, object: GraphActivityNodeImpl): void {
    const nodePageId = (object.getId() as string).split('-')[0];
    const nodePage = (graphModule.graph.getPages() as TSMap<string, GraphPage>).get(nodePageId);
    const node: GraphActivityNodeImpl = (page.getNodes() as TSMap<string, GraphNode>).get(object.getId() as string) as GraphActivityNodeImpl;

    // Save properties
    node.setLabel(this.label);
    node.setActivityType(this.activityType as ACTIVITY_TYPE);
    node.setResourceRef(this.resource);
    node.setReportStatistics(this.report);
    node.setProcessingTime(this.processingTime);
    node.setProcessingTimeParameter(this.processingTimeParameter);
    node.setSetupTime(this.setupTime);
    node.setSetupTimeParameter(this.setupTimeParameter);
    node.setUnit(this.timeUnit);
    node.setQueueRef(this.queue);
    node.setFunctionRef(this.function);

    this.imageIcon = this.imageIcon !== '' ? this.imageIcon : NODE_TYPE.activity.image;
    node.setImageIcon(this.imageIcon);

    // Change label of the renderer node
    page.getGraph().getCells().map((cell: joint.dia.Cell) => {
      if (cell.attributes.nodeId === object.getId()) {
        cell.attr({
          label: {
            text: this.label,
          },
          image: {
            xlinkHref: this.imageIcon,
          },
        });
      }
    });

    // Update local instance
    GraphNodeImpl.instance.set(node.getId() as string, GraphActivityNodeImpl.deserialize(node) as GraphNode);

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
   * Declare semantic module.
   *
   * @memberof ActivityNodeModal
   */
  public declareSemanticModules(): void {
    $('.menu .item').tab();
  }

  /**
   * Returns the resources of an activity.
   *
   * @readonly
   * @type {GraphData[]}
   * @memberof ActivityNodeModal
   */
  public get resources(): GraphData[] {
    let resources;
    try {
      const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
      const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
      resources = nodeData.values().filter((value: GraphData) => value.getType() === 'resource');
    } catch (e) {
      resources = e;
    }
    return resources;
  }

  /**
   * Returns the queues of an activity.
   *
   * @readonly
   * @type {GraphData[]}
   * @memberof ActivityNodeModal
   */
  public get queues(): GraphData[] {
    let queues;
    try {
      const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
      const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
      queues = nodeData.values().filter((value: GraphData) => value.getType() === 'queue');
    } catch (e) {
      queues = e;
    }
    return queues;
  }

  /**
   * Returns the functions of an activity.
   *
   * @readonly
   * @type {GraphData[]}
   * @memberof ActivityNodeModal
   */
  public get functions(): GraphData[] {
    let functions;
    try {
      const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
      const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
      functions = nodeData.values().filter((value: GraphData) => value.getType() === 'function');
    } catch (e) {
      functions = e;
    }
    return functions;
  }
}
</script>
