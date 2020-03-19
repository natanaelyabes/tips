<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <section>
    <g2-line :height="300" :data="model" />
  </section>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';
import 'vue-g2';

@Component

/**
 * Implementation of line chart component.
 *
 * @prop {any} model?
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default class LineChart extends BaseComponent {

  @Prop()
  public model?: any;

  public created(): void {
    const self = this;
  }
}
</script>
