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
    console.log('Page 0: ', page);
    console.log('Default page: ', graphModule.defaultPage as GraphPage);
    console.log('Page 0 Arcs: ', graphModule.pageArcs(page));
    console.log('Page 0 Arc 0', graphModule.pageArc(page, '0'));
    console.log('Page 0 Data', graphModule.pageData(page));
    console.log('Page 0 Data 0', graphModule.pageDatum(page, '0'));
    console.log('Page 0 Type', graphModule.pageElementType(page));
    console.log('Page 0 Id', graphModule.pageId(page));
    console.log('Page 0 Label', graphModule.pageLabel(page));
    console.log('Page 0 Nodes', graphModule.pageNodes(page));
    console.log('Page 0 Node 0', graphModule.pageNode(page, '0'));
  }
}
</script>
