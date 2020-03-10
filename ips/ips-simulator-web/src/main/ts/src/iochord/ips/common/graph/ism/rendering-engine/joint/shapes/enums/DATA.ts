import * as joint from 'jointjs';

/**
 *
 * Enumerations for data node type.
 *
 * @export
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export const DATA_TYPE = {
  datatable: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/data_toolbox/datatable_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
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
  generator: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/data_toolbox/generator_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
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
  function: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/data_toolbox/function_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
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
  resource: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/data_toolbox/resources_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
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
  queue: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/data_toolbox/queue_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
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
  objecttype: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/data_toolbox/object_type_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
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
  monitor: {
    image: require('@/assets/images/icons/20190626-FIX_ICON/64x64/data_toolbox/kpi_64.png'),
    markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
    size: { width: 64, height: 64 },
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
