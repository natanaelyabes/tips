<!--
  @package ips
  @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
  @since   2019
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
    </table>
  </section>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import BaseComponent from '../ui/layout/class/BaseComponent';

@Component

/**
 * The IPS table component.
 *
 * @export
 * @class IpsTable
 * @extends {BaseComponent}
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export default class IpsTable extends BaseComponent {
  /**
   * Header of the table
   *
   * @type {*}
   * @memberof IpsTable
   */
  @Prop()
  public dataHeader?: any;

  /**
   * The table data model.
   *
   * @type {*}
   * @memberof IpsTable
   */
  @Prop()
  public model?: any;

  /**
   * Pagination object of the table
   *
   * @type {*}
   * @memberof IpsTable
   */
  @Prop()
  public pagination?: any;

  /**
   * Perform specific action upon created Vue lifecyle.
   *
   * @memberof IpsTable
   */
  public created(): void {
    const self = this;
  }
}
</script>
