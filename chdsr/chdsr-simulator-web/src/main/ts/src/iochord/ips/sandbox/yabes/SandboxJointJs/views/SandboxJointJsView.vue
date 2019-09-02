<template>
  <div class="sandbox joint js view">
    <div id="canvas"></div>
  </div>
</template>

<style>
.sandbox.joint.js.view {
  height: 100%;
}

#canvas {
  height: 100%;
}
</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import PageLayout from '@/iochord/ips/common/ui/layout/classes/PageLayout';

// JointJS
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';


export class OnPlace extends joint.shapes.pn.Place {
  constructor() {
    super();
    this.attr('.label/text', 'On');
    this.attr('.root/fill', 'lightgreen');
    this.attr('.root/stroke', 'black');
  }
}

export class OffPlace extends joint.shapes.pn.Place {
  constructor() {
    super();
    this.attr('.label/text', 'Off');
    this.attr('.root/fill', 'red');
    this.attr('.root/stroke', 'black');
  }
}

export class DesertThemePaper extends joint.dia.Paper {
  constructor(options: joint.dia.Paper.Options) {
    super(options);
    this.drawBackground({
      color: '#fad390',
    });
  }
}

@Component
export default class SandboxJointJsView extends PageLayout {

  /** @Override */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Joint.js Tutorial');
  }

  /** @Override */
  public mounted(): void {
    const graph = new joint.dia.Graph();

    const paper = new joint.dia.Paper({
      el: $('#canvas'),
      model: graph,
      width: window.innerWidth,
      height: $('#canvas').innerHeight(),
      gridSize: 10,
      drawGrid: true,
    });

    const on = new joint.shapes.pn.Place();
    on.position(100, 100);
    on.addTo(graph);

    const turnOff = new joint.shapes.pn.Transition();
    turnOff.position(220, 100);
    turnOff.attr('.label/text', 'Turn Off');
    turnOff.addTo(graph);

    const off = new joint.shapes.pn.Place();
    off.position(300, 100);
    off.addTo(graph);

    const onToTurnOff = new joint.shapes.pn.Link();
    onToTurnOff.source(on);
    onToTurnOff.target(turnOff);
    onToTurnOff.addTo(graph);

    const turnOffToOff = new joint.shapes.pn.Link();
    turnOffToOff.source(turnOff);
    turnOffToOff.target(off);
    turnOffToOff.addTo(graph);

    const token: joint.Vectorizer = joint.V('circle', {
      r: 7,
      fill: 'orange',
      stroke: 'black',
    });

    (onToTurnOff.findView(paper) as joint.dia.LinkView).sendToken((token as any), { duration: 10000 }, () => {
      console.log('animation end');
    });
  }
}
</script>
