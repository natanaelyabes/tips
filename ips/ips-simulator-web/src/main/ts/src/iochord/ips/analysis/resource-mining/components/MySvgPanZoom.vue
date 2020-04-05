<!--
  @package ips
  @author N. I. Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div id="parentContainer">
    <div id="containerSvg">
    </div>
    <div id="overlaySelect">
      <select  v-model="layoutDirection">
        <option value="TB" selected>Vertical</option>
        <option value="LR">Horizontal</option>
      </select>
      <ul class="legend">
        <li><div id="legendActivity"></div> - activity</li>
        <li><div id="legendGroup"></div> - group</li>
        <li><div id="legendResource"></div> - resource</li>
      </ul>
    </div>
    <span id="tooltip"></span>
  </div>
</template>

<style>
ul.legend {
  list-style-type: none;
  display: contents;
}

#legendActivity {
  background-color: #85C1E9;
  width: 1em;
  height: 1em;
  display: inline-block;
}

#legendGroup {
  background-color: #CCE2FF;
  width: 1em;
  height: 1em;
  display: inline-block;
}

#legendResource {
  background-color: #ABB2B9;
  width: 1em;
  height: 1em;
  display: inline-block;
}

#parentContainer {
  position: relative;
  height: 100%;
  width: 100%;
}

#containerSvg {
  height: 100%;
  width: 100%;
  position: absolute;
}

#overlaySelect {
  position: absolute;
  top: 10px;
  right: 10px;
}

#tooltip {
  display: none;
  background-color: black;
  color: #fff;
  text-align: center;
  border-radius: 5px;
  padding: 5px;
  position: absolute;
  top: 10px;
  left: 10px;
}
</style>

<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import ResourceMiningResult from '../models/ResourceMiningResult';
import SvgPanZoom from 'svg-pan-zoom';
import * as joint from 'jointjs';

@Component

/**
 * Graph page to provide a side by side
 * view of result from resource mining.
 *
 * @extends BaseComponent
 * @package ips
 * @author N. I. Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
export default class MySvgPanZoom extends BaseComponent {

  /**
   * Object result from resource mining
   *
   * @type {ResourceMiningResult}
   * @memberof AnalysisResourceMining
   */
  @Prop()
  public resMiningResult!: ResourceMiningResult;

  public layoutDirection: any = 'TB';

  public zoomEnabled: boolean = true;
  public controlIconsEnabled: boolean = true;
  public fit: boolean = true;
  public center: boolean = true;
  public minZoom: number = 0.1;

  public graph: joint.dia.Graph = new joint.dia.Graph();
  public svgZoom: any = null;
  public cells: any = [];

  /**
   * Override Vue mounted lifecyle
   *
   * @memberof AnalysisResourceMining
   */
  public mounted(): void {
    const paper: joint.dia.Paper = new joint.dia.Paper({
      el: $('#containerSvg'),
      model: this.graph,
      width: '100%',
      height: '100%',
      gridSize: 1,
      async: true,
    });

    this.buildNodes(this.resMiningResult.activities, 'ACT', this.graph);
    this.buildNodes(this.resMiningResult.groups, 'GRP', this.graph);
    this.buildNodes(this.resMiningResult.resources, 'RES', this.graph);
    this.buildLinks(this.resMiningResult.mactgroup, this.resMiningResult.mgroupres, this.graph);

    this.graph.resetCells(this.cells);

    paper.on('render:done', () => {
      joint.layout.DirectedGraph.layout(this.graph, { ranker: 'network-simplex', rankDir: this.layoutDirection });

    this.svgZoom.zoom(0.8);

    paper.on('cell:mouseover', () => {
      document.body.style.cursor = 'all-scroll';
      this.svgZoom = SvgPanZoom('#containerSvg svg',
        {
          zoomEnabled: this.zoomEnabled,
          controlIconsEnabled: this.controlIconsEnabled,
          fit: this.fit,
          center: this.center,
          minZoom: this.minZoom,
        });
    });

    paper.on('cell:mouseover', (cellview: any) => {
      const id = cellview.model.id;
      if (!id.startsWith('LNK_')) {
        const name = cellview.model.attributes.attrs.name.text;
        const ev: any = event;
        const tooltip = $('#tooltip');
        tooltip.text(name);
        tooltip.css( 'display', 'block' );
      }
    });

    paper.on('cell:mouseout', (cellview: any) => {
      const id = cellview.model.id;
      if (!id.startsWith('LNK_')) {
        const tooltip = $('#tooltip');
        tooltip.css( 'display', 'none' );
      }
    });

    paper.on('cell:pointerdown', (cellview: any) => {
      const id = cellview.model.id;
      if (!id.startsWith('LNK_')) {
        const tooltip = $('#tooltip');
        tooltip.css( 'display', 'none' );
      }
      document.body.style.cursor = 'grabbing';
      this.svgZoom.disablePan();
    });

    paper.on('cell:pointerup', (cellview: any) => {
      const id = cellview.model.id;
      if (!id.startsWith('LNK_')) {
        const tooltip = $('#tooltip');
        tooltip.css( 'display', 'none' );
      }
      document.body.style.cursor = 'default';
      this.svgZoom.enablePan();
    });

    paper.on('cell:pointerclick', (cellview: any) => {
      console.log('Handle click event of element ' + cellview);
    });

    this.$nextTick(() => {
      window.addEventListener('resize', () => {
        this.svgZoom.resize();
      });
    });
  }

  @Watch('layoutDirection')
  public onPropertyChanged(value: string, oldValue: string) {
    joint.layout.DirectedGraph.layout(this.graph, { ranker: 'network-simplex', rankDir: this.layoutDirection });
    this.svgZoom.updateBBox();
    this.svgZoom.center();
    this.svgZoom.fit();
  }

  public buildNodes(nodes: string[], type: 'ACT' | 'GRP' | 'RES', graph: joint.dia.Graph): void {
    const x = 10;
    const y = 10;
    const wd = 80;
    const hg = type === 'ACT' ? 40 : 80;
    const mr = 30;
    let i = 0;
    for (const node of nodes) {
      this.createCell(node, type, wd, hg, x + i * (wd + mr), y, graph);
      i++;
    }
  }

  public buildLinks(map1: any, map2: any, graph: joint.dia.Graph): void {
    for (const activity of Object.keys(map1)) {
      for (const group of map1[activity]) {
        this.createLink('ACT_' + activity, 'GRP_' + group, graph);
        for (const resource of map2[group])
          this.createLink('GRP_' + group, 'RES_' + resource, graph);
      }
    }
  }

  public createCell(name: string, type: 'ACT' | 'GRP' | 'RES', wd: number, hg: number, x: number, y: number, graph: joint.dia.Graph): void {
    let cell: any;
    let bgCol: string = '#f7d794';
    let txCol: string = 'black';
    const id = type + '_' + name;
    const wraptext = joint.util.breakText(name, {
      width: wd,
      height: hg,
    });

    if (type === 'ACT') {
      cell = new joint.shapes.standard.Rectangle({ id });
    } else if (type === 'GRP') {
      cell = new joint.shapes.standard.Polyline({ id });
      bgCol = '#2bcbba';
    } else {
      cell = new joint.shapes.standard.Circle({ id });
      bgCol = '#f8a5c2';
      txCol = 'black';
    }

    cell.position(x, y);
    cell.resize(wd, hg);
    cell.attr({
      body: {
        fill: bgCol,
        refPoints: '5,0 0,5 0,10 10,10 10,5 5,0',
        strokeWidth: 1,
      },
      label: {
        text: wraptext,
        fill: txCol,
      },
      name: {
        text: name,
      },
    });
    this.cells.push(cell);
  }

  public createLink(id1: string, id2: string, graph: joint.dia.Graph): void {
    const id = 'LNK_' + id1 + id2;
    const link: any = new joint.shapes.standard.Link({ id });
    link.source({ id: id1 });
    link.target({ id: id2 });
    this.cells.push(link);
  }
}
</script>
