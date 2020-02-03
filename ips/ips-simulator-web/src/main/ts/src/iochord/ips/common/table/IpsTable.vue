<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <section>
    <table class="ui selectable celled striped table" :header="dataHeader" :data="model" :footer="pagination">
      <thead>
        <tr class="center aligned">
          <th v-for="(header, i) in dataHeader" :key="i">{{header}}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, j) in model" :key="j" class="right aligned">
          <th v-for="(column, k) in row" :key="k">{{column}}</th>
        </tr>
      </tbody>
      <!-- <tfoot>
      </tfoot> -->
    </table>
  </section>
</template>

<style scoped>

</style>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';

@Component({
  components: {

  },
})
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class IpsTable extends BaseComponent {
  @Prop()
  public dataHeader?: any;

  @Prop()
  public model?: any;

  @Prop()
  public pagination?: any;

  public created(): void {
    const self = this;
  }
}
</script>