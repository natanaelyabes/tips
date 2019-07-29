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
import { DATA_TYPE_ENUM } from '@/iochord/chdsr/common/graph/sbpnet/enums/DATA';
import { GraphPage } from '@/iochord/chdsr/common/graph/sbpnet/interfaces/GraphPage';

const graphModule = getModule(GraphModule);

@Component
export default class SandboxGraphStoreView extends PageLayout {

  /** @Override */
  public async mounted(): Promise<void> {
    await graphModule.fetchGraph();
    const page = graphModule.page('0') as GraphPage;
    console.log(page);
    console.log(graphModule.pageArc(page, '0'));
    console.log(graphModule.pageData(page, 'resource'));
    console.log(graphModule.pageDatum(page, 'objecttype', '1'));
  }
}
</script>
