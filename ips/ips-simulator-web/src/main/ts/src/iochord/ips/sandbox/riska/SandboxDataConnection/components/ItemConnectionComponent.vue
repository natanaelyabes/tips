<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="item connection component">
    <div class="item">
      <div class="ui transparent icon input">
        <input type="text" placeholder="Search connection...">
        <i class="search icon"></i>
      </div>
    </div>
    <a v-on:click= "chooseConnection('oracle')" class="item">Oracle</a>
    <a v-on:click= "chooseConnection('mssql')" class="item">MSSQL Server</a>
    <a v-on:click= "chooseConnection('mysql')" class="item">MySQL</a>
    <a v-on:click= "chooseConnection('hadoop')" class="item">Hadoop FS</a>
  </div>
</template>

<style>

</style>


<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class ItemConnectionComponent extends BaseComponent {
  public currentContentComponent: string = '';

  public chooseConnection(prm: string) {
    if (prm === 'oracle') {
      this.currentContentComponent = 'ContentOraclePropertiesComponent';
    } else if (prm === 'mssql' ) {
      this.currentContentComponent = 'ContentMssqlPropertiesComponent';
    } else if (prm === 'mysql') {
      this.currentContentComponent = 'ContentMysqlPropertiesComponent';
    } else {
      this.currentContentComponent = 'ContentHadoopPropertiesComponent';
    }
    this.$root.$emit('ebContentComponent', this.currentContentComponent);
  }
}
</script>
>
