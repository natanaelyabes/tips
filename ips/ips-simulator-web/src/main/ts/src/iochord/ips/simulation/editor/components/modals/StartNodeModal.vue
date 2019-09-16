<!--
  @package ips
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="ui tiny start modal">
    <i class="close icon"></i>
    <div id="start_modal_title" class="header">
      <h3 class="ui green header">Start</h3>
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui grid">
          <div class="row">
            <div class="three wide column">
              Label
            </div>
            <div class="thirteen wide column">
              <input type="text" @change="handleChangedLabel()" v-model="tempStartLabel" id="start_txt_label" />
            </div>
          </div>
          <div class="row">
            <div class="three wide column">
              Generator
            </div>
            <template v-if="reloaded">
              <div class="thirteen wide column">
                <select @change="handleChangedGenerator($event)" v-model="tempGenerator" id="start_txtgen" class="ui search dropdown">
                  <option v-for="nodeDatum in nodeData" :selected="nodeDatum[0] === tempGenerator" :key="nodeDatum[0]" :value="nodeDatum[0]">{{nodeDatum[0]}}</option>
                </select>
              </div>
            </template>
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
/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { GraphPageImpl } from '@/iochord/ips/common/graph/ism/class/GraphPageImpl';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';

// JQuery
declare const $: any;

// Vuex
const graphModule = getModule(GraphModule);

/**
 *
 * @package ips
 * @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
 * @since 2019
 *
 */
@Component
export default class StartNodeModal extends SemanticComponent {
  @Prop() private startLabel!: string;
  @Prop() private startGenerator!: string;

  private tempStartLabel: string = '';
  private tempGenerator: string = '';

  private reloaded: boolean = false;

  @Watch('startLabel')
  public onChangeStartLabel(newVal: string): void {
    this.tempStartLabel = newVal;
  }

  @Watch('startGenerator')
  public onChangeGenerator(newVal: string): void {
    this.tempGenerator = newVal;
  }

  public handleChangedLabel(): void {
    this.$emit('changeStartLabel', this.tempStartLabel);
  }

  public handleChangedGenerator(): void {
    this.$emit('changeStartGenerator', this.tempGenerator);
  }

  public mounted(): void {
    this.tempStartLabel = this.startLabel;
    this.tempGenerator = this.startGenerator;
  }

  public updated(): void {
    if (!this.reloaded) {
      this.reloaded = true;
    }

    // Only for dropdown values
    this.tempGenerator = this.startGenerator;
  }

  public get nodeData(): /* Map<string, GraphData> | null */ any {
    const pages = graphModule.graph.getPages() as Map<string, GraphPage>;
    const nodeData = (pages.get('0') as GraphPage).getData() as Map<string, GraphData>;
    return nodeData;
  }
}
</script>
