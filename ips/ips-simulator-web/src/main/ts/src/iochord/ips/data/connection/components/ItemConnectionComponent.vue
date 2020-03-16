<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="connection component">
    <div class="ui divided items">
      <div class="item">
        <div class="ui transparent icon input">
          <input type="text" placeholder="Search connection...">
          <i class="search icon"></i>
        </div>
      </div>
      <div class="item hovered" v-on:click = "chooseConnection('oracle')">Oracle</div>
      <div class="item hovered" v-on:click = "chooseConnection('mssql')">MSSQL Server</div>
      <div class="item hovered" v-on:click = "chooseConnection('mysql')">MySQL</div>
      <div class="item hovered" v-on:click = "chooseConnection('hadoop')">Hadoop FS</div>
    </div>
  </div>
</template>

<style scoped>
.ui.input {
  width: 100%;
}

.ui.items {
  margin: 1rem 0;
}
.ui.items .item {
  padding: 1rem 1rem;
  cursor: pointer;
}

.ui.items .item:first-child {
  margin: initial;
  /* padding: 1rem 1rem!important; */
}

.ui.items .item:last-child {
  margin: initial;
  padding: 1rem 1rem!important;
}

.ui.items .item.hovered:hover {
  background: rgba(0,0,0,.03);
}

a {
  color: black;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Item connection component.
 *
 * @export
 * @class ItemConnectionComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ItemConnectionComponent extends BaseComponent {

  /**
   * Current content component.
   *
   * @type {string}
   * @memberof ItemConnectionComponent
   */
  public currentContentComponent: string = '';

  /**
   * Choose connection.
   *
   * @param {string} prm
   * @memberof ItemConnectionComponent
   */
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
