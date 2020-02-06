<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <section>
    <g2-pie :height="300" :data="model" :label-option="options" :axis-name="axisLabels"/>
  </section>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';
import 'vue-g2';

@Component({
  components: {
  },
})

/**
 * Implementation of pie chart component.
 *
 * @extends BaseComponent
 * @prop {any} model?
 * @prop {any} options?
 * @prop {any} axisLabels?
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class PieChart extends BaseComponent {
  @Prop()
  public model?: any;

  @Prop()
  public options?: any;

  @Prop()
  public axisLabels?: any;
}
</script>
