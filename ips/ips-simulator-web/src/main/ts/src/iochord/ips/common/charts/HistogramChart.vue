<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <section>
    <g2-histogram :bin-width="binWidth" :bins="0" :data="model" :axisName="{x: 'Interval',y: 'Count'}" :axisColor="{ titleColor: '#e50000', labelColor: '#e50000' }" :color="{backgroundColor: '#e50000'}"></g2-histogram>
  </section>
</template>

<script lang="ts">
import { Vue, Prop, Component } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';
import 'vue-g2';

@Component

/**
 * Implementation of histogram chart component.
 *
 * @extends BaseComponent
 * @prop {any} binWidth?
 * @prop {any} model?
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default class HistogramChart extends BaseComponent {

  @Prop()
  public binWidth?: any;

  @Prop()
  public model?: any;

  public created(): void {
    const self = this;
  }

  public mounted(): void {
    console.log(this.$children[0]);
  }
}
</script>
