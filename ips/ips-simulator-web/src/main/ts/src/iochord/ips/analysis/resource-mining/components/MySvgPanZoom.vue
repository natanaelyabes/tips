<!--
  @package ips
  @author N. I. Utama <ichsan83@gmail.com>
  @since 2020
-->
<template>
  <div id="parentContainer">
    <div id="containerSvg">
    </div>
    <select id="overlaySelect" v-model="layoutDirection">
      <option value="TB" selected>Vertical</option>
      <option value="LR">Horizontal</option>
    </select>
  </div>
</template>

<style>
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
    });

    this.buildActivities(this.resMiningResult.activities, this.graph);
    this.buildGroups(this.resMiningResult.groups, this.graph);
    this.buildResources(this.resMiningResult.resources, this.graph);
    this.buildLinks(this.resMiningResult.mactgroup, this.resMiningResult.mgroupres, this.graph);

    joint.layout.DirectedGraph.layout(this.graph, { ranker: 'network-simplex', rankDir: this.layoutDirection });

    this.svgZoom = SvgPanZoom('#containerSvg svg',
      {
        zoomEnabled: this.zoomEnabled,
        controlIconsEnabled: this.controlIconsEnabled,
        fit: this.fit,
        center: this.center,
        minZoom: this.minZoom,
      });

    paper.on('cell:mouseover', () => {
      document.body.style.cursor = 'all-scroll';
    });

    paper.on('cell:mouseout', () => {
      document.body.style.cursor = 'default';
    });

    paper.on('cell:pointerdown', () => {
      document.body.style.cursor = 'grabbing';
      this.svgZoom.disablePan();
    });

    paper.on('cell:pointerup', () => {
      document.body.style.cursor = 'default';
      this.svgZoom.enablePan();
    });

    paper.on('cell:pointerclick', (e: any) => {
      console.log('Handle click event of element ' + e);
    });
  }

  @Watch('layoutDirection')
  public onPropertyChanged(value: string, oldValue: string) {
    joint.layout.DirectedGraph.layout(this.graph, { ranker: 'network-simplex', rankDir: this.layoutDirection });
  }

  public buildActivities(activities: string[], graph: joint.dia.Graph): void {
    const x = 10;
    const y = 10;
    const wd = 80;
    const hg = 30;
    const mr = 30;
    let i = 0;
    for (const activity of activities) {
      this.createCell(activity, 'ACT', wd, hg, x + i * (wd + mr), y, graph);
      i++;
    }
  }

  public buildGroups(groups: string[], graph: joint.dia.Graph): void {
    const x = 10;
    const y = 150;
    const wd = 80;
    const hg = 30;
    const mr = 30;
    let i = 0;
    for (const group of groups) {
      this.createCell(group, 'GRP', wd, hg, x + i * (wd + mr), y, graph);
      i++;
    }
  }

  public buildResources(resources: string[], graph: joint.dia.Graph): void {
    const x = 10;
    const y = 290;
    const wd = 80;
    const hg = 80;
    const mr = 30;
    let i = 0;
    for (const resource of resources) {
      this.createCell(resource, 'RES', wd, hg, x + i * (wd + mr), y, graph);
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
    let cell: any = undefined;
    let bgCol: string = '#85C1E9';
    let txCol: string = 'black';
    const id = type + '_' + name;
    const wraptext = joint.util.breakText(name, {
      width: wd,
      height: hg,
    });
    
    if (type == 'ACT') { 
      cell = new joint.shapes.standard.Rectangle({ id: id });
    } else if (type == 'GRP') {
      cell = new joint.shapes.standard.Polyline({ id: id });
      bgCol = 'yellow';
    } else {
      cell = new joint.shapes.standard.Circle({ id: id });
      bgCol = 'red';
      txCol = 'white';
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
    });
    cell.addTo(graph);
  }
  
  public createLink(id1: string, id2: string, graph: joint.dia.Graph): void {
    const link: any = new joint.shapes.standard.Link();
    link.source({ id: id1 });
    link.target({ id: id2 });
    link.addTo(graph);
  }
}
</script>
