<template>
  <div class="item existed connection component">
    <div class="item">
      <div class="ui transparent icon input">
        <input type="text" placeholder="Browse...">
        <i class="search icon"></i>
      </div>
    </div>
    <router-link class="item" v-for="(ds, i) in datasets" :key="i" tag="a" to="/iochord/ips/analysis-process-model">{{ds.name}} ({{i}})</router-link>
    <!-- <template slot="existed-connection">
    </template> -->
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import DataConnectionService from '@/iochord/ips/common/service/data/DataConnectionService';

@Component
export default class ItemExistedConnectionComponent extends BaseComponent {

  public datasets = {};

  /** @Override */
  public async mounted(): Promise<void> {
    const self = this;
    DataConnectionService.getInstance().getDataConnections((res: any) => {
      self.datasets = res.data;
    }, (tick: any) => {
      console.log('AAAAAAAA', tick);
    });
  }

}
</script>
