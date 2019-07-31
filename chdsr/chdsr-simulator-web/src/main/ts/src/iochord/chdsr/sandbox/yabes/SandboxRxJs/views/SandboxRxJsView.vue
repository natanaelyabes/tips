<template>
  <div class="sandbox rx js view">
    <div class="ui basic segment">
      <h1>Hello</h1>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import PageLayout from '@/iochord/chdsr/common/ui/layout/classes/PageLayout';
import { DataService } from '@/iochord/chdsr/sandbox/yabes/SandboxRxJs/classes/DataService';

@Component<SandboxRxJsView>({
  subscriptions() {
    return (
      {
        data: DataService.fetchData(),
      }
    );
  },
})
export default class SandboxRxJsView extends PageLayout {
  /** @Override */
  public mounted(): void {
    this.$observables.data.subscribe((message) => {
      console.log(message);
    });
    DataService.sendData('Test');
    DataService.sendData('A');
    DataService.sendData('B');
    DataService.sendData('C');
    DataService.sendData('D');
  }
}
</script>
