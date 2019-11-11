<template>
  <section>
    <g2-line :height="300" :data="model" />
  </section>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';
import 'vue-g2';

@Component
export default class LineChart extends BaseComponent {

  @Prop()
  public model?: any;

  public created(): void {
    const self = this;
  }

}
</script>