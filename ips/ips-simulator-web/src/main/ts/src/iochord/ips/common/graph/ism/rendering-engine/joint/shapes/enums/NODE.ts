import * as joint from 'jointjs';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export const NODE_TYPE = {
  activity: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/toolbox/toolbox_activity_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
    attr: {
      '.root' : {
        refWidth: '100%',
        refHeight: '100%',
        strokeWidth: 2,
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
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/toolbox/toolbox_branch_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
    attr: {
      '.root' : {
        refWidth: '100%',
        refHeight: '100%',
        stroke: 'black',
        fill: 'white',
        strokeWidth: 2,
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
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/toolbox/toolbox_start_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><circle class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
    attr: {
      '.root': {
        r: 10,
        strokeWidth: 2,
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
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/toolbox/toolbox_stop_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><circle class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
    attr: {
      '.root': {
        r: 10,
        strokeWidth: 2,
        stroke: '#db2828',
        fill: '#db2828',
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
};
