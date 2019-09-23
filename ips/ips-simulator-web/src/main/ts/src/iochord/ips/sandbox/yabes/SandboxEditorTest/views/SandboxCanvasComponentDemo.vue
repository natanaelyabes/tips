<template>
  <div class="sandbox canvas component demo view">
    <CanvasComponent :key="reRenderKey" v-bind:response="graphData" />
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import BaseLayout from '@/iochord/ips/common/ui/layout/class/BaseLayout';

// Async component must be lazily load
const CanvasComponent = () => import('@/iochord/ips/simulation/editor/components/canvas/CanvasComponent.vue');

// Vuex module
const graphModule = getModule(GraphModule);

@Component<SandboxCanvasComponentDemo>({
  components: {
    CanvasComponent,
  },
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class SandboxCanvasComponentDemo extends BaseLayout {

  /** @Override */
  public async mounted(): Promise<void> {
    try {

      // Fetch graph to Vuex state
      await graphModule.loadGraph();

      // Update rxjs subject
      GraphSubject.update(graphModule.graph);

      this.$observables.graph.subscribe((graph: Graph) => {
        console.log(graph);
        graphModule.setGraph(graph);
      });
    } catch (e) {
      console.error(e);
    }

    // Force re-render page component
    this.forceReRender();

    // Always reload component when resizing content
    window.addEventListener('resize', () => {
      this.forceReRender();
    });
  }

  public get graphData(): Graph | undefined {
    return graphModule.graph;
  }
}
</script>
