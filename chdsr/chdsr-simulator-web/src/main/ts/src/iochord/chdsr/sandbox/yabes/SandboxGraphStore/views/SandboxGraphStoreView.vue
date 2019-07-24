<template>
  <div class="sandbox graph store view">
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
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';

const graphModule = getModule(GraphModule);

@Component
export default class SandboxGraphStoreView extends PageLayout {

  /** @Override */
  public async mounted(): Promise<void> {
    graphModule.fetchGraph();
    console.log(graphModule.graph);
  }
}
</script>
