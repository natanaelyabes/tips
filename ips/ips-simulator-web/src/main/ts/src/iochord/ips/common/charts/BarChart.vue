<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <section>
    <g2-column :height="300" :data="model" :is-bar="false" />
  </section>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';
import 'vue-g2';

@Component

/**
 * Implementation for bar chart component.
 *
 * @extends BaseComponent
 * @prop {any} model?
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default class BarChart extends BaseComponent {

  @Prop()
  public model?: any;

  public created(): void {
    const self = this;
  }
}
</script>
