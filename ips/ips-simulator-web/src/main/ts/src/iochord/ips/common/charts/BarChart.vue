<template>
  <section>
    <g2-column :height="300" :data="model" :is-bar="false" />
  </section>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';
import 'vue-g2';

@Component
export default class BarChart extends BaseComponent {

  @Prop()
  public model?: any;

  public created(): void {
    const self = this;
  }
}
</script>