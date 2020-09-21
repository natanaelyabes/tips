<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="pmd heuristics ribbon component">
    <template v-if="!replayOnly">
      <div class="item">
        <div class="header">
          <strong>Heuristics Miner</strong>
        </div>
      </div>
    </template>
    <form class="ui form">
      <div class="ui secondary menu">
        <template v-if="!replayOnly">
          <div class="item">
            <div class="field">
              <label>Dependency threshold</label>
              <input type="number" name="pmd[depthresh]" v-model="depTh" step="0.01" min="-1" max="1">
            </div>
          </div>
          <div class="item">
            <div class="field">
              <label>Minimum frequency</label>
              <input type="number" name="pmd[minfreq]" v-model="freqTh" min="0">
            </div>
          </div>
          <!--
          <div class="item">
            <div class="field">
              <label>Node Color Category</label>
              <select class="ui search dropdown">
                <input type="hidden" name="pmd[nodecolor]">
                  <option value="totalduration">Total duration</option>
                  <option value="meanduration">Mean duration</option>
                  <option value="totalcost">Total cost</option>
                  <option value="busycost">Busy cost</option>
                  <option value="idlecost">Idle cost</option>
              </select>
            </div>
          </div>
          <div class="item">
            <div class="field">
              <label>Arc Color/Thickness Category</label>
              <select class="ui search dropdown">
                <input type="hidden" name="pmd[nodecolor]">
                  <option value="totalduration">Total duration</option>
                  <option value="meanduration">Mean duration</option>
                  <option value="totalcost">Total cost</option>
                  <option value="busycost">Busy cost</option>
                  <option value="idlecost">Idle cost</option>
              </select>
            </div>
          </div>
          -->
          <div class="item">
            <div class="field">
              <label>&nbsp;</label>
              <button type="button" @click="$emit('onRun')" class="ui primary button">
                Apply
              </button>
            </div>
          </div>
          <div class="item">
            <div class="field">
              <label>Footprint-based Fitness</label>
              <input type="number" :value="fpBasedFitness" readonly="readonly" />
            </div>
          </div>
          <div class="item">
            <div class="field">
              <label>Footprint-based Fitness</label>
              <input type="number" :value="trBasedFitness" readonly="readonly" />
            </div>
          </div>
        </template>
        <div class="item">
          <div class="field">
            <label>&nbsp;</label>
            <button v-if="replayState < 0" type="button" class="ui disabled button" disabled="disabled">
              Loading ...
            </button>
            <button v-if="replayState == 0" type="button" @click="$emit('startReplay')" class="ui primary button">
              Start Replay
            </button>
            <button v-if="replayState == 1" type="button" @click="$emit('pauseReplay')" class="ui primary button">
              Pause Replay
            </button>
            <button v-if="replayState == 2" type="button" @click="$emit('pauseReplay')" class="ui primary button">
              Unpause Replay
            </button>
            <button v-if="replayState > 0" type="button" @click="$emit('stopReplay')" class="ui primary button">
              Stop Replay
            </button>
          </div>
        </div>
        </div>
    </form>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';

@Component

/**
 * Ribbon component to define heuristic net parameter settings
 *
 * @extends SemanticComponent
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class PMDHeuristicsRibbonComponent extends SemanticComponent {

  public depTh = -1;

  public freqTh = 0;

  @Prop()
  public replayOnly?: number;

  @Prop({
    default: -1,
  })
  public fpBasedFitness!: number;

  @Prop({
    default: -1,
  })
  public trBasedFitness!: any;

  @Prop({
    default: -1,
  })
  public replayState!: any;

}
</script>
