<template>
  <div class="item process model component">
    <template v-if="branchRule">
      <center>
        <h1>{{ branchRule[0].eventName }} decision point</h1>
        <p>Model accuracy: {{ branchRule[0].modelAccuracy }}</p>
      </center>
      <div id="graph"></div>
    </template>
  </div>
</template>

<style scoped>
#graph {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

#graph svg {
  display: block!important;
  margin: auto!important;
}
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import ModelViewer from '@/iochord/ips/simulation/editor/components/ModelViewer.vue';
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';

// Vuex module
const graphModule = getModule(GraphModule);

@Component({
  components: {
    ModelViewer,
  },
})
export default class ItemProcessModel extends SemanticComponent {

  /**
   * Selected branch rule.
   *
   * @type {string}
   * @memberof AnalysisResourceMining
   */
  @Prop({ default: '' })
  public branchRule!: any;

  /**
   * Override Vue mounted lifecyle.
   *
   * @memberof ItemProcessModel
   */
  public mounted(): void {
    this.renderGraph();
  }

  public updated(): void {
    this.renderGraph();
  }

  public renderGraph(): void {
    const Viz = require('viz.js/viz');
    const { Module, render } = require('viz.js/full.render');
    let viz = new Viz({ Module, render });
    viz.renderSVGElement(this.branchRule[0].decisionRule)
      .then((element: any) => {
        let graph = document.getElementById('graph')!;
        graph.innerHTML = '';
        graph = graph.appendChild(document.createElement('center'));
        graph.appendChild(element);
      })
      .catch((e: any) => {
        // Create a new Viz instance (@see Caveats page for more info)
        viz = new Viz();

        // Possibly display the error
        console.error(e);
      });
  }
}
</script>