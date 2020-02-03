<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="sandbox rx js view">
    <div class="ui basic segment">
      <template v-for="(str, index) in list">
        <h1 :key="index">{{str}}</h1>
      </template>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';
import { DataService } from '@/iochord/ips/sandbox/yabes/SandboxRxJs/class/DataService';

@Component<SandboxRxJsView>({
  subscriptions: () => {
    return (
      {
        data: DataService.fetchData(),
      }
    );
  },
})
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class SandboxRxJsView extends PageLayout {
  private list: string[] = new Array<string>();

  /** @Override */
  public mounted(): void {
    this.$observables.data.subscribe((message) => {
      this.list.push(message);
    });
    DataService.sendData('Test');
    DataService.sendData('A');
    DataService.sendData('B');
    DataService.sendData('C');
    DataService.sendData('D');
  }
}
</script>
