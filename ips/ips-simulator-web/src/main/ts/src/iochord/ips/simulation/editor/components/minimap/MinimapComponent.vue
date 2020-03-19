<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="minimap component">
    <div class="ui basic segment" style="width: 260px">
      <h2>Model Pane</h2>
      <div id="minimap" class="active"></div>
    </div>
  </div>
</template>

<style>
.minimap.component {
  height: 100%; 
}
#minimap {
  cursor: pointer;
  border: 1px solid black;
  width: 100%;
  height: 100%;
}

#minimap:hover {
  border: 2px solid #81b0c5;
}

.active#minimap:hover {
  border: 3px solid #03a4cc;
}

.active#minimap {
  border: 2px solid #03628c;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Prop, Mixins, Vue } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import { TSMap } from 'typescript-map';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

// SVG Pan and Zoom
import SvgPanZoom from 'svg-pan-zoom';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { Graph } from '@/iochord/ips/common/graphs/ism/interfaces/Graph';
import { GraphControlImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphControlImpl';
import { GraphImpl } from '@/iochord/ips/common/graphs/ism/class/GraphImpl';
import { GraphNode } from '@/iochord/ips/common/graphs/ism/interfaces/GraphNode';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphPageImpl } from '@/iochord/ips/common/graphs/ism/class/GraphPageImpl';
import { JointGraphConnectorImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphConnectorImpl';
import { JointGraphNodeImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphNodeImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import JointJsRenderer from '@/iochord/ips/common/graphs/ism/rendering-engine/joint/class/JointJsRenderer';

// Mixins
import CanvasMixin from '../../mixins/editors/CanvasMixin';

// Vuex
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';

// Vuex module instance
const graphModule = getModule(GraphModule);

// JQuery Handler
declare const $: any;

/**
 * The canvas component.
 *
 * @export
 * @class MinimapComponent
 * @extends {Mixins(BaseComponent, CanvasMixin)}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class MinimapComponent extends Mixins(BaseComponent, CanvasMixin) {

  /**
   * Response from the web service as the graph object.
   *
   * @type {Graph}
   * @memberof MinimapComponent
   */
  @Prop() public response?: Graph;

  /**
   * The SVG Pan and Zoom instance.
   *
   * @type {SvgPanZoom.Instance}
   * @memberof MinimapComponent
   */
  public panAndZoom?: SvgPanZoom.Instance;

  /**
   * Vue mounted lifecycle.
   *
   * @memberof MinimapComponent
   */
  public mounted(): void {
    this.loadGraph();
    this.$forceUpdate();
  }

  /**
   * Load graph into minimap.
   *
   * @memberof MinimapComponent
   */
  public loadGraph(): void {
    try {

      // Deserialize the model
      this.graph = this.response as Graph;

      const renderer = new JointJsRenderer(
        this.graph,
        this.activePage as GraphPage,
        this.currentSelectedElement as GraphNode,
      );

      // Get panAndZoom instance from renderer
      this.panAndZoom = renderer.panAndZoom;

      // Set active page to the first page of the graph
      if (this.graph) {
        this.activePage = this.graph.getPages()!.get('0');
      }

      // Assign Joint.js page as an active page
      if (this.activePage) {
        this.activePage = renderer.activeJointPage(this.activePage.getId() as string) as JointGraphPageImpl;
      }
    } catch (e) {
      console.error(e);
    }
  }

}
</script>
