import * as joint from 'jointjs';

export const NODE_TYPE = {
  activity: {
    image: require('@/assets/images/icons/chdsr/toolbox_activity.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
    attr: {
      '.root' : {
        refWidth: '100%',
        refHeight: '100%',
        // stroke: 'black',
        // fill: 'white',
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
    image: require('@/assets/images/icons/chdsr/toolbox_branch.png'),
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
    image: require('@/assets/images/icons/chdsr/toolbox_start.png'),
    markup: '<g class="rotatable"><g class="scalable"><circle class="root"/></g></g><text class="label"/>',
    size: { width: 50, height: 50 },
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
    image: require('@/assets/images/icons/chdsr/toolbox_stop.png'),
    markup: '<g class="rotatable"><g class="scalable"><circle class="root"/></g></g><text class="label"/>',
    size: { width: 50, height: 50 },
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
    image: require('@/assets/images/icons/chdsr/data_kpi.png'),
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
