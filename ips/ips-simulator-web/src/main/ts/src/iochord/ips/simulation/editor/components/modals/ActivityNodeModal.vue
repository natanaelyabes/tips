<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
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
              <input type="text" @change="handleChangedLabel()" v-model="tempActLabel" id="activity_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">

              <!-- Tab menu -->
              <div class="ui top attached tabular menu">
                <a class="active item" data-tab="basic">Basic</a>
                <a class="item" data-tab="processing">Processing</a>
                <a class="item" data-tab="advanced">Advanced</a>
              </div>

              <!-- Basic tab -->
              <div class="ui bottom attached active tab segment" data-tab="basic">
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Activity Type</div>
                    <template v-if="reloaded">
                      <div class="twelve wide column">
                        <select class="ui dropdown" @change="handleSelectedActivityType($event)" v-model="tempSelectedActivityType">
                          <option value="STANDARD">Standard</option>
                          <option value="CONCURRENT_BATCH">Concurrent Batch Process</option>
                          <option value="SPLIT_MODULE">Split Module Process</option>
                        </select>
                      </div>
                    </template>
                  </div>

                  <template v-if="tempSelectedActivityType === 'STANDARD'">
                    <div id="basic-standard-sm-1" class="row">
                      <div class="four wide column">Resources</div>
                      <div class="twelve wide column">
                        <select @change="handleChangedResource($event)" v-model="tempResource" id="act_txtresource" class="ui search dropdown">
                          <option v-for="nodeDatum in nodeData" :selected="nodeDatum[0] === tempResource" :key="nodeDatum[0]" :value="nodeDatum[0]">{{ nodeDatum[0] }}</option>
                        </select>
                      </div>
                    </div>
                  </template>

                  <template v-if="tempSelectedActivityType === 'CONCURRENT_BATCH'">
                    <div id="basic-cbp-sm-1" class="row">
                      <div class="four wide column">Process Criteria</div>
                      <div class="twelve wide column">
                        <input type="text" id="pc_txt_label">
                      </div>
                    </div>
                    <div id="basic-cbp-sm-2" class="row">
                      <div class="four wide column">Resources</div>
                      <div class="twelve wide column">
                        <select @change="handleChangedResource($event)" v-model="tempResource" id="act_txtresource" class="ui search dropdown">
                          <option v-for="nodeDatum in nodeData" :selected="nodeDatum[0] === tempResource" :key="nodeDatum[0]" :value="nodeDatum[0]">{{ nodeDatum[0] }}</option>
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

                  <template v-if="tempSelectedActivityType === 'SPLIT_MODULE'">
                    <div id="basic-split-sm-1" class="row">
                      <div class="four wide column">Split Criteria</div>
                      <div class="twelve wide column">
                        <input type="text" id="split_criteria_txt_label">
                      </div>
                    </div>
                    <div id="basic-split-sm-2" class="sixteen wide column">
                      <div class="inline field">
                        <input type="checkbox" class="hidden">
                        <label>Report scrap</label>
                      </div>
                    </div>
                    <div id="basic-split-sm-3" class="row">
                      <div class="four wide column">Resources</div>
                      <div class="twelve wide column">
                        <select @change="handleChangedResource($event)" v-model="tempResource" id="act_txtresource" class="ui search dropdown">
                          <option v-for="nodeDatum in nodeData" :selected="nodeDatum[0] === tempResource" :key="nodeDatum[0]" :value="nodeDatum[0]">{{ nodeDatum[0] }}</option>
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

                  <div class="sixteen wide column">
                    <div class="inline field">
                      <input id="report-statistics" type="checkbox" @change="handleChangedReport()" v-model="tempReport" class="hidden">
                      <label for="report-statistics">Report statistics</label>
                    </div>
                  </div>

                  <div class="row">
                    <div class="four wide column">Custom Monitor</div>
                    <div class="twelve wide column">
                      <input disabled type="text" @change="handleChangedCustomMonitor()" v-model="tempCustomMonitor">
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
                      <template v-if="reloaded">
                        <select class="ui dropdown" @change="handleChangedProcessingTime($event)" v-model="tempProcessingTime">
                          <option value="RANDOM">Random</option>
                          <option value="CONSTANT">Constant</option>
                        </select>
                      </template>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Parameter</div>
                    <div class="twelve wide column">
                      <input type="text" @change="handleChangedProcessingTimeParameter()" v-model="tempProcessingTimeParameter">
                    </div>
                  </div>
                </div>

                <h4>Setup</h4>
                <div class="ui divider"></div>
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Setup Time</div>
                    <div class="twelve wide column">
                      <template v-if="reloaded">
                        <select class="ui dropdown" @change="handleChangedSetupTime($event)" v-model="tempSetupTime">
                          <option value="RANDOM">Random</option>
                          <option value="CONSTANT">Constant</option>
                        </select>
                      </template>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Parameter</div>
                    <div class="twelve wide column">
                      <input type="text" @change="handleChangedSetupTimeParameter()" v-model="tempSetupTimeParameter">
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Unit</div>
                    <div class="twelve wide column">
                      <template v-if="reloaded">
                        <select class="ui dropdown" @change="handleChangedUnit($event)" v-model="tempUnit">
                          <option value="HOURS">Hours</option>
                          <option value="MINUTES">Minutes</option>
                          <option value="SECONDS">Seconds</option>
                        </select>
                      </template>
                    </div>
                  </div>
                </div>

                <h4>Queue</h4>
                <div class="ui divider"></div>
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Queue label</div>
                    <div class="twelve wide column">
                      <template v-if="reloaded">
                        <select @change="handleChangedQueueLabel($event)" v-model="tempQueueLabel" id="act_txtqueuelabel" class="ui search dropdown">
                          <option v-for="nodeDatum in nodeData" :selected="nodeDatum[0] === tempQueueLabel" :key="nodeDatum[0]" :value="nodeDatum[0]">{{ nodeDatum[0] }}</option>
                        </select>
                      </template>
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
                      <input type="text" @change="handleChangedInputType()" v-model="tempInputType">
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Output Type</div>
                    <div class="twelve wide column">
                      <input type="text" @change="handleChangedOutputType()" v-model="tempOutputType">
                    </div>
                  </div>
                  <div class="row">
                    <div class="sixteen wide column">
                      <div class="field">
                        <label>Code Segment</label>
                        <textarea id="code_segment" @change="handleChangedCodeSegment()" v-model="tempCodeSegment"></textarea>
                      </div>
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
      <p><em>Node properties are automatically saved</em></p>
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

declare const $: any;

import { TSMap } from 'typescript-map';


const graphModule = getModule(GraphModule);

@Component
export default class ActivityNodeModal extends SemanticComponent {
  @Prop() private actLabel!: string;
  @Prop() private actNodeSelectedActivityType!: string;
  @Prop() private actNodeResource!: string;
  @Prop() private actNodeReport!: boolean;
  @Prop() private actNodeCustomMonitor!: string;
  @Prop() private actNodeProcessingTime!: string;
  @Prop() private actNodeProcessingTimeParameter!: string;
  @Prop() private actNodeSetupTime!: string;
  @Prop() private actNodeSetupTimeParameter!: string;
  @Prop() private actNodeUnit!: string;
  @Prop() private actNodeQueueLabel!: string;
  @Prop() private actNodeInputType!: string;
  @Prop() private actNodeOutputType!: string;
  @Prop() private actNodeCodeSegment!: string;

  private tempActLabel: string = '';
  private tempSelectedActivityType: string = '';
  private tempResource: string = '';
  private tempReport: boolean = false;
  private tempCustomMonitor: string = '';
  private tempProcessingTime: string = '';
  private tempProcessingTimeParameter: string = '';
  private tempSetupTime: string = '';
  private tempSetupTimeParameter: string = '';
  private tempUnit: string = '';
  private tempQueueLabel: string = '';
  private tempInputType: string = '';
  private tempOutputType: string = '';
  private tempCodeSegment: string = '';

  private reloaded: boolean = false;

  @Watch('actLabel')
  public onChangeActLabel(newVal: string): void {
    this.tempActLabel = newVal;
  }

  @Watch('actNodeSelectedActivityType')
  public onChangeSelectedActivityType(newVal: string): void {
    this.tempSelectedActivityType = newVal;
  }

  @Watch('actNodeResource')
  public onChangeActResource(newVal: string): void {
    this.tempResource = newVal;
  }

  @Watch('actNodeReport')
  public onChangeReport(newVal: boolean): void {
    this.tempReport = newVal;
  }

  @Watch('actNodeCustomMonitor')
  public onChangeCustomMonitor(newVal: string): void {
    this.tempCustomMonitor = newVal;
  }

  @Watch('actNodeProcessingTime')
  public onChangeProcessingTime(newVal: string): void {
    this.tempProcessingTime = newVal;
  }

  @Watch('actNodeProcessingTimeParameter')
  public onChangeProcessingTimeParameter(newVal: string): void {
    this.tempProcessingTimeParameter = newVal;
  }

  @Watch('actNodeSetupTime')
  public onChangeSetupTime(newVal: string): void {
    this.tempSetupTime = newVal;
  }

  @Watch('actNodeSetupTimeParameter')
  public onChangeSetupTimeParameter(newVal: string): void {
    this.tempSetupTimeParameter = newVal;
  }

  @Watch('actNodeUnit')
  public onChangeUnit(newVal: string): void {
    this.tempUnit = newVal;
  }

  @Watch('actNodeQueueLabel')
  public onChangeQueueLabel(newVal: string): void {
    this.tempQueueLabel = newVal;
  }

  @Watch('actNodeInputType')
  public onChangeInputType(newVal: string): void {
    this.tempInputType = newVal;
  }

  @Watch('actNodeOutputType')
  public onChangeOutputType(newVal: string): void {
    this.tempOutputType = newVal;
  }

  @Watch('actNodeCodeSegment')
  public onChangeCodeSegment(newVal: string): void {
    this.tempCodeSegment = newVal;
  }

  public handleChangedLabel(): void {
    this.$emit('changeActLabel', this.tempActLabel);
  }

  public handleSelectedActivityType(): void {
    this.$emit('changeActNodeSelectedActivityType', this.tempSelectedActivityType);
  }

  public handleChangedResource(): void {
    this.$emit('changeActNodeResource', this.tempResource);
  }

  public handleChangedReport(): void {
    this.$emit('changeActNodeReport', this.tempReport);
  }

  public handleChangedCustomMonitor(): void {
    this.$emit('changeActNodeCustomMonitor', this.tempCustomMonitor);
  }

  public handleChangedProcessingTime(): void {
    this.$emit('changeActNodeProcessingTime', this.tempProcessingTime);
  }

  public handleChangedProcessingTimeParameter(): void {
    this.$emit('changeActNodeProcessingTimeParameter', this.tempProcessingTimeParameter);
  }

  public handleChangedSetupTime(): void {
    this.$emit('changeActNodeSetupTime', this.tempSetupTime);
  }

  public handleChangedSetupTimeParameter(): void {
    this.$emit('changeActNodeSetupTimeParameter', this.tempSetupTimeParameter);
  }

  public handleChangedUnit(): void {
    this.$emit('changeActNodeUnit', this.tempUnit);
  }

  public handleChangedQueueLabel(): void {
    this.$emit('changeActNodeQueueLabel', this.tempQueueLabel);
  }

  public handleChangedInputType(): void {
    this.$emit('changeActNodeInputType', this.tempInputType);
  }

  public handleChangedOutputType(): void {
    this.$emit('changeActNodeOutputType', this.tempOutputType);
  }

  public handleChangedCodeSegment(): void {
    this.$emit('changeActNodeCodeSegment', this.tempCodeSegment);
  }

  public declareSemanticModules() {
    $('.ui.dropdown').dropdown();
    $('.tabular.menu .item').tab();
  }

  public mounted(): void {
    this.tempSelectedActivityType = this.actNodeSelectedActivityType;
    this.tempResource = this.actNodeResource;
    this.tempReport = this.actNodeReport;
    this.tempCustomMonitor = this.actNodeCustomMonitor;
    this.tempProcessingTime = this.actNodeProcessingTime;
    this.tempProcessingTimeParameter = this.actNodeProcessingTimeParameter;
    this.tempSetupTime = this.actNodeSetupTime;
    this.tempSetupTimeParameter = this.actNodeSetupTimeParameter;
    this.tempUnit = this.actNodeUnit;
    this.tempQueueLabel = this.actNodeQueueLabel;
    this.tempInputType = this.actNodeInputType;
    this.tempOutputType = this.actNodeOutputType;
    this.tempCodeSegment = this.actNodeCodeSegment;
  }

  public updated(): void {
    if (!this.reloaded) {
      this.reloaded = true;
    }

    // Only for dropdown values
    this.tempSelectedActivityType = this.actNodeSelectedActivityType;
    this.tempResource = this.actNodeResource;
    this.tempProcessingTime = this.actNodeProcessingTime;
  }

  public get nodeData(): /* TSMap<string, GraphData> | null */ any {
    const pages = graphModule.graph.getPages() as TSMap<string, GraphPage>;
    const nodeData = (pages.get('0') as GraphPage).getData() as TSMap<string, GraphData>;
    return nodeData.entries();
  }
}
</script>
