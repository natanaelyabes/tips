<template>
  <section>
    <g2-histogram :bin-width="binWidth" :bins="0" :data="model" :axisName="{x: 'Interval',y: 'Count'}" :axisColor="{ titleColor: '#e50000', labelColor: '#e50000' }" :color="{backgroundColor: '#e50000'}"></g2-histogram>
  </section>
</template>

<style scoped>

</style>

<script lang="ts">
import { Vue, Prop, Component } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';
import 'vue-g2';

@Component({
  components: {

  },
})
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
    // this.$children[0];
    // .interval().color('#e50000')
    // (this.$children[0] as any).chart.interval().color('#e50000');
    // (this.$children[0] as any).drawChart(this.model);
  }
}
</script>