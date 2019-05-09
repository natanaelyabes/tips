import * as joint from 'jointjs';

export const NODE_TYPE = {
  activity: {
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 50, height: 50 },
    attr: {
      '.root' : {
        refWidth: '100%',
        refHeight: '100%',
        stroke: 'black',
        fill: 'white',
        strokeWidth: 2,
        transform: 'translate(-25, 0)',
      },
      '.label' : {
        ref: '.root',
        refY: '130%',
        text: 'Your text here',
        textAnchor: 'middle',
        yAlignment: 'middle',
        fill: 'black',
      },
    } as joint.dia.Cell.Selectors,
  },
  branch: {
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 50, height: 50 },
    attr: {
      '.root' : {
        refWidth: '100%',
        refHeight: '100%',
        stroke: 'black',
        fill: 'white',
        strokeWidth: 2,
        transform: 'translate(0, 0); rotate(45)',
      },
      '.label' : {
        ref: '.root',
        refY: '130%',
        textAnchor: 'middle',
        text: 'Your text here',
        yAlignment: 'middle',
        fill: 'black',
      },
    } as joint.dia.Cell.Selectors,
  },
  start: {
    markup: '<g class="rotatable"><g class="scalable"><circle class="root"/></g></g><text class="label"/>',
    size: { width: 25, height: 25 },
    attr: {
      '.root': {
        r: 10,
        strokeWidth: 2,
        transform: 'translate(0, 20)',
        stroke: '#21ba45',
        fill: 'white',
      },
      '.label': {
        ref: '.root',
        refY: '150%',
        textAnchor: 'middle',
        yAlignment: 'middle',
        fill: 'black',
        text: 'Start',
      },
    } as joint.dia.Cell.Selectors,
  },
  stop: {
    markup: '<g class="rotatable"><g class="scalable"><circle class="root"/></g></g><text class="label"/>',
    size: { width: 25, height: 25 },
    attr: {
      '.root': {
        r: 10,
        strokeWidth: 2,
        stroke: '#db2828',
        fill: '#db2828',
        transform: 'translate(0, 20)',
      },
      '.label': {
        ref: '.root',
        refY: '150%',
        textAnchor: 'middle',
        yAlignment: 'middle',
        fill: 'black',
        text: 'End',
      },
    } as joint.dia.Cell.Selectors,
  },
  monitor: {
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 50, height: 50 },
    attr: {
      '.root' : {
        refWidth: '100%',
        refHeight: '100%',
        stroke: 'black',
        strokeWidth: 2,
        fill: 'red',
        transform: 'translate(-40, 0)',
      },
      '.label' : {
        ref: '.root',
        refY: '130%',
        textAnchor: 'middle',
        yAlignment: 'middle',
        fill: 'black',
      },
    } as joint.dia.Cell.Selectors,
  },
};
