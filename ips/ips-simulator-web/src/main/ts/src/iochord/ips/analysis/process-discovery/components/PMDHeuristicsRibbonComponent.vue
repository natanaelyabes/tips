<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="pmd heuristics ribbon component">
    <div class="item"><div class="header"><strong>Heuristics Miner</strong></div></div>
    <form class="ui form">
    <div class="ui secondary menu">
        <div class="item">
          <div class="field">
            <label>Dependency threshold</label>
            <div class="ui labeled ticked range slider" id="slider-range"></div>
          </div>
        </div>
        <div class="item">
          <div class="field">
            <label>Minimum frequency</label>
            <input type="text" name="pmd[minfreq]" placeholder="10">
          </div>
        </div>
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
        <div class="item">
          <div class="field">
            <button class="ui primary button">
              Mine
            </button>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Ribbon component to define heuristic net parameter settings
 *
 * @extends BaseComponent
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class PMDHeuristicsRibbonComponent extends BaseComponent {
// $('.ui.range.slider')
//   .slider({
//     min: 5,
//     max: 100,
//     start: 10,
//     end: 90,
//     step: 5
//   })
// ;
}
</script>
