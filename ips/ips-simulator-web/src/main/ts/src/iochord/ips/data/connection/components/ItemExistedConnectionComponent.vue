<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="existed connection component">
    <div class="ui basic segment" id="header">
      <h4>Event logs</h4>
    </div>
    <div class="ui divider"></div>
    <div class="ui divided items">
      <!-- <div class="item">
        <div class="ui transparent icon input">
          <input type="text" placeholder="Browse...">
          <i class="search icon"></i>
        </div>
      </div>
      <div class="item">
        <div class="ui checkbox">
          <input id="select[all]" type="checkbox" name="select[all]">
          <label for="select[all]">Select all</label>
        </div>
      </div> -->
      <div class="item" v-for="(ds, i) in datasets" :key="i">
        <!-- <div class="content">
          <div class="ui checkbox">
            <input id="select[all]" type="checkbox" name="select[all]">
            <label for=""></label>
          </div>
        </div> -->
        <div class="content">
          <router-link tag="a" :to="'/iochord/ips/data/mapping/' + i">
            <h4 class="ui header">{{ds.name}}</h4> 
            <a class="ui label">({{i}})</a>
          </router-link>
        </div>

        <div class="bottom aligned content">
          <div class="ui basic segment">
            <button class="ui disabled red basic icon button">
              <i class="trash icon"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.ui.basic.segment {
  padding: 0 1em;
}

.ui.input {
  width: 100%;
}

.ui.items {
  margin: 1rem 0;
}
.ui.items .item {
  padding: 1rem 1rem;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import DataConnectionService from '@/iochord/ips/data/connection/services/DataConnectionService';

@Component

/**
 * Item existed connection component.
 *
 * @export
 * @class ItemExistedConnectionComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ItemExistedConnectionComponent extends BaseComponent {

  /**
   * Dataset object.
   *
   * @memberof ItemExistedConnectionComponent
   */
  public datasets = {};

  /**
   * Vue mounted lifecycle.
   *
   * @override
   * @returns {Promise<void>}
   * @memberof ItemExistedConnectionComponent
   */
  public async mounted(): Promise<void> {
    const self = this;
    DataConnectionService.getInstance().getFilteredDataConnections('dataset', (res: any) => {
      self.datasets = res.data;
    }, null);
  }
}
</script>
