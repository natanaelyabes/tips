<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny activity modal">
    <i class="close icon"></i>
    <div id="activity_modal_title" class="header">
      <h3 class="ui green header">Activity</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">Label</div>
            <div class="thirteen wide column">
              <input type="text" id="activity_txt_label">
            </div>
          </div>
          <div class="row">
            <div class="sixteen wide column">
              <div class="ui top attached tabular menu">
                <a class="active item" data-tab="basic">Basic</a>
                <a class="item" data-tab="processing">Processing</a>
                <a class="item" data-tab="advanced">Advanced</a>
              </div>
              <!-- basic tab -->
              <div class="ui bottom attached active tab segment" data-tab="basic">
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Activity Type</div>
                    <div class="twelve wide column">
                      <select class="ui dropdown" @change="handleSelectedActivityType()" v-model="tempSelectedActivityType">
                        <option value="standard">Standard</option>
                        <option value="concurrent">Concurrent Batch Process</option>
                        <option value="split">Split Module Process</option>
                      </select>
                    </div>
                  </div>
                  <div id="basic-standard-sm-1" class="row" style="padding:0px"></div>
                  <div id="basic-cbp-sm-1" class="row" style="padding:0px"></div>
                  <div id="basic-cbp-sm-2" class="row" style="padding:0px"></div>
                  <div id="basic-cbp-sm-3" class="row" style="padding:0px"></div>
                  <div id="basic-split-sm-1" class="row" style="padding:0px"></div>
                  <div id="basic-split-sm-2" class="sixteen wide column" style="padding:0px"></div>
                  <div id="basic-split-sm-3" class="row" style="padding:0px"></div>
                  <div id="basic-split-sm-4" class="row" style="padding:0px"></div>
                  <div class="sixteen wide column">
                    <div class="inline field">
                      <input type="checkbox" @change="handleChangedReport()" v-model="tempReport" class="hidden">
                      <label>Report statistics</label>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Custom Monitor</div>
                    <div class="nine wide column">
                      <input type="text" @change="handleChangedCustomMonitor()" v-model="tempCustomMonitor">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                </div>
              </div>
              <!-- processing tab -->
              <div class="ui bottom attached tab segment" data-tab="processing">
                <div class="ui grid">
                  <div class="row">
                    <div class="three wide column">Processing Time</div>
                    <div class="thirteen wide column">
                      <input type="text" @change="handleChangedProcessingTime()" v-model="tempProcessingTime">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Parameter</div>
                    <div class="thirteen wide column">
                      <input type="text" @change="handleChangedParameter1()" v-model="tempParameter1">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Setup Time</div>
                    <div class="thirteen wide column">
                      <input type="text" @change="handleChangedSetupTime()" v-model="tempSetupTime">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Parameter</div>
                    <div class="thirteen wide column">
                      <input type="text" @change="handleChangedParameter2()" v-model="tempParameter2">
                    </div>
                  </div>
                  <div class="row">
                    <div class="three wide column">Unit</div>
                    <div class="thirteen wide column">
                      <input type="text" @change="handleChangedUnit()" v-model="tempUnit">
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Queue label</div>
                    <div class="nine wide column">
                      <input type="text" @change="handleChangedQueueLabel()" v-model="tempQueueLabel">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                </div>
              </div>
              <!-- advanced tab -->
              <div class="ui bottom attached tab segment" data-tab="advanced">
                <div class="ui grid">
                  <div class="row">
                    <div class="four wide column">Input Type</div>
                    <div class="nine wide column">
                      <input type="text" @change="handleChangedInputType()" v-model="tempInputType">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
                    </div>
                  </div>
                  <div class="row">
                    <div class="four wide column">Output Type</div>
                    <div class="nine wide column">
                      <input type="text" @change="handleChangedOutputType()" v-model="tempOutputType">
                    </div>
                    <div class="three wide column">
                      <button class="ui button">...</button>
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
      <div class="ui save button">Save</div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>
<style>
</style>

<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';

declare const $: any;

@Component
export default class ActivityNodeModal extends SemanticComponent {
  @Prop() private actNodeSelectedActivityType !: string;
  @Prop() private actNodeReport !: boolean;
  @Prop() private actNodeCustomMonitor !: string;
  @Prop() private actNodeProcessingTime !: string;
  @Prop() private actNodeParameter1 !: string;
  @Prop() private actNodeSetupTime !: string;
  @Prop() private actNodeParameter2 !: string;
  @Prop() private actNodeUnit !: string;
  @Prop() private actNodeQueueLabel !: string;
  @Prop() private actNodeInputType !: string;
  @Prop() private actNodeOutputType !: string;
  @Prop() private actNodeCodeSegment !: string;

  private tempSelectedActivityType: string = '';
  private tempReport: boolean = false;
  private tempCustomMonitor: string = '';
  private tempProcessingTime: string = '';
  private tempParameter1: string = '';
  private tempSetupTime: string = '';
  private tempParameter2: string = '';
  private tempUnit: string = '';
  private tempQueueLabel: string = '';
  private tempInputType: string = '';
  private tempOutputType: string = '';
  private tempCodeSegment: string = '';

  private basicStandardSM1 !: string;
  private basicCbpSM1 !: string;
  private basicCbpSM2 !: string;
  private basicCbpSM3 !: string;
  private basicSplitSM1 !: string;
  private basicSplitSM2 !: string;
  private basicSplitSM3 !: string;
  private basicSplitSM4 !: string;

  @Watch('actNodeSelectedActivityType')
  public onChangeSelectedActivityType(newVal: string): void {
    this.tempSelectedActivityType = newVal;
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

  @Watch('actNodeParameter1')
  public onChangeParameter1(newVal: string): void {
    this.tempParameter1 = newVal;
  }

  @Watch('actNodeSetupTime')
  public onChangeSetupTime(newVal: string): void {
    this.tempSetupTime = newVal;
  }

  @Watch('actNodeParameter2')
  public onChangeParameter2(newVal: string): void {
    this.tempParameter2 = newVal;
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

  public handleSelectedActivityType(): void {
    this.$emit('changeActNodeSelectedActivityType', this.tempSelectedActivityType);
    this.showDetailBasic();
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

  public handleChangedParameter1(): void {
    this.$emit('changeActNodeParameter1', this.tempParameter1);
  }

  public handleChangedSetupTime(): void {
    this.$emit('changeActNodeSetupTime', this.tempSetupTime);
  }

  public handleChangedParameter2(): void {
    this.$emit('changeActNodeParameter2', this.tempParameter2);
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

  public beforeMount(): void {
    this.tempSelectedActivityType = this.actNodeSelectedActivityType;
    this.basicStandardSM1 = '';
    this.basicCbpSM1 = '';
    this.basicCbpSM2 = '';
    this.basicCbpSM3 = '';
    this.basicSplitSM1 = '';
    this.basicSplitSM2 = '';
    this.basicSplitSM3 = '';
    this.basicSplitSM4 = '';
  }

  public declareSemanticModules() {
    $('.ui.dropdown').dropdown();
    $('.tabular.menu .item').tab();
  }

  public mounted(): void {
    this.$nextTick(() => {
      this.tempSelectedActivityType = this.actNodeSelectedActivityType;
      this.tempReport = this.actNodeReport;
      this.tempCustomMonitor = this.actNodeCustomMonitor;
      this.tempProcessingTime = this.actNodeProcessingTime;
      this.tempParameter1 = this.actNodeParameter1;
      this.tempSetupTime = this.actNodeSetupTime;
      this.tempParameter2 = this.actNodeParameter2;
      this.tempUnit = this.actNodeUnit;
      this.tempQueueLabel = this.actNodeQueueLabel;
      this.tempInputType = this.actNodeInputType;
      this.tempOutputType = this.actNodeOutputType;
      this.tempCodeSegment = this.actNodeCodeSegment;
    });

    this.basicStandardSM1 += '<div class=\'four wide column\'>Resources</div>';
    this.basicStandardSM1 += '<div class=\'nine wide column\'>';
    this.basicStandardSM1 += '<input type=\'text\' id=\'resources_txt_label\'>';
    this.basicStandardSM1 += '</div>';
    this.basicStandardSM1 += '<div class=\'three wide column\'>';
    this.basicStandardSM1 += '<button class=\'ui button\'>...</button>';
    this.basicStandardSM1 += '</div>';

    this.basicCbpSM1 += '<div class=\'four wide column\'>Process Criteria</div>';
    this.basicCbpSM1 += '<div class=\'nine wide column\'>';
    this.basicCbpSM1 += '<input type=\'text\' id=\'pc_txt_label\'>';
    this.basicCbpSM1 += '</div>';
    this.basicCbpSM1 += '<div class=\'three wide column\'>';
    this.basicCbpSM1 += '<button class=\'ui button\'>...</button>';
    this.basicCbpSM1 += '</div>';

    this.basicCbpSM2 += '<div class=\'four wide column\'>Resources</div>';
    this.basicCbpSM2 += '<div class=\'nine wide column\'>';
    this.basicCbpSM2 += '<input type=\'text\' id=\'resources_txt_label\'>';
    this.basicCbpSM2 += '</div>';
    this.basicCbpSM2 += '<div class=\'three wide column\'>';
    this.basicCbpSM2 += '<button class=\'ui button\'>...</button>';
    this.basicCbpSM2 += '</div>';

    this.basicCbpSM3 += '<div class=\'four wide column\'>Limit</div>';
    this.basicCbpSM3 += '<div class=\'nine wide column\'>';
    this.basicCbpSM3 += '<input type=\'text\' id=\'limit_txt_label\'>';
    this.basicCbpSM3 += '</div>';

    this.basicSplitSM1 += '<div class=\'four wide column\'>Split Criteria</div>';
    this.basicSplitSM1 += '<div class=\'nine wide column\'>';
    this.basicSplitSM1 += '<input type=\'text\' id=\'split_criteria_txt_label\'>';
    this.basicSplitSM1 += '</div>';
    this.basicSplitSM1 += '<div class=\'three wide column\'>';
    this.basicSplitSM1 += '<button class=\'ui button\'>...</button>';
    this.basicSplitSM1 += '</div>';

    this.basicSplitSM2 += '<div class=\'inline field\'>';
    this.basicSplitSM2 += '<input type=\'checkbox\' class=\'hidden\'>';
    this.basicSplitSM2 += '<label>Report scrap</label>';
    this.basicSplitSM2 += '</div>';

    this.basicSplitSM3 += '<div class=\'four wide column\'>Resources</div>';
    this.basicSplitSM3 += '<div class=\'nine wide column\'>';
    this.basicSplitSM3 += '<input type=\'text\' id=\'resources_txt_label\'>';
    this.basicSplitSM3 += '</div>';
    this.basicSplitSM3 += '<div class=\'three wide column\'>';
    this.basicSplitSM3 += '<button class=\'ui button\'>...</button>';
    this.basicSplitSM3 += '</div>';

    this.basicSplitSM4 += '<div class=\'four wide column\'>Split Criteria</div>';
    this.basicSplitSM4 += '<div class=\'nine wide column\'>';
    this.basicSplitSM4 += '<input type=\'text\' id=\'split_criteria2_txt_label\'>';
    this.basicSplitSM4 += '</div>';
    this.basicSplitSM4 += '<div class=\'three wide column\'>';
    this.basicSplitSM4 += '<button class=\'ui button\'>...</button>';
    this.basicSplitSM4 += '</div>';
  }

  public showDetailBasic(): void {
    const paddingStyle14 = 'padding-top:14px;padding-bottom:14px;';
    const paddingStyle0 = 'padding:0px';

    if (this.tempSelectedActivityType === 'standard') {
      $('#basic-standard-sm-1').html(this.basicStandardSM1);
      $('#basic-standard-sm-1').attr('style', paddingStyle14);
      $('#basic-cbp-sm-1').html('');
      $('#basic-cbp-sm-1').attr('style', paddingStyle0);
      $('#basic-cbp-sm-2').html('');
      $('#basic-cbp-sm-2').attr('style', paddingStyle0);
      $('#basic-cbp-sm-3').html('');
      $('#basic-cbp-sm-3').attr('style', paddingStyle0);
      $('#basic-split-sm-1').html('');
      $('#basic-split-sm-1').attr('style', paddingStyle0);
      $('#basic-split-sm-2').html('');
      $('#basic-split-sm-2').attr('style', paddingStyle0);
      $('#basic-split-sm-3').html('');
      $('#basic-split-sm-3').attr('style', paddingStyle0);
      $('#basic-split-sm-4').html('');
      $('#basic-split-sm-4').attr('style', paddingStyle0);
    } else if (this.tempSelectedActivityType === 'concurrent') {
      $('#basic-standard-sm-1').html('');
      $('#basic-standard-sm-1').attr('style', paddingStyle0);
      $('#basic-cbp-sm-1').html(this.basicCbpSM1);
      $('#basic-cbp-sm-1').attr('style', paddingStyle14);
      $('#basic-cbp-sm-2').html(this.basicCbpSM2);
      $('#basic-cbp-sm-2').attr('style', paddingStyle14);
      $('#basic-cbp-sm-3').html(this.basicCbpSM3);
      $('#basic-cbp-sm-3').attr('style', paddingStyle14);
      $('#basic-split-sm-1').html('');
      $('#basic-split-sm-1').attr('style', paddingStyle0);
      $('#basic-split-sm-2').html('');
      $('#basic-split-sm-2').attr('style', paddingStyle0);
      $('#basic-split-sm-3').html('');
      $('#basic-split-sm-3').attr('style', paddingStyle0);
      $('#basic-split-sm-4').html('');
      $('#basic-split-sm-4').attr('style', paddingStyle0);
    } else {
      $('#basic-standard-sm-1').html('');
      $('#basic-standard-sm-1').attr('style', paddingStyle0);
      $('#basic-cbp-sm-1').html('');
      $('#basic-cbp-sm-1').attr('style', paddingStyle0);
      $('#basic-cbp-sm-2').html('');
      $('#basic-cbp-sm-2').attr('style', paddingStyle0);
      $('#basic-cbp-sm-3').html('');
      $('#basic-cbp-sm-3').attr('style', paddingStyle0);
      $('#basic-split-sm-1').html(this.basicSplitSM1);
      $('#basic-split-sm-1').attr('style', paddingStyle14);
      $('#basic-split-sm-2').html(this.basicSplitSM2);
      $('#basic-split-sm-2').attr('style', paddingStyle14);
      $('#basic-split-sm-3').html(this.basicSplitSM3);
      $('#basic-split-sm-3').attr('style', paddingStyle14);
      $('#basic-split-sm-4').html(this.basicSplitSM4);
      $('#basic-split-sm-4').attr('style', paddingStyle14);
    }
  }
}
</script>