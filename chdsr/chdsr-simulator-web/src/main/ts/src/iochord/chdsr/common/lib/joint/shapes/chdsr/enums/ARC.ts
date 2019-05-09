import * as joint from 'jointjs';

export const ARC_TYPE = {
  connector: {
    attr: {
      '.connection': {
        stroke: '#005891',
        connection: true,
        strokeDasharray: '0',
        strokeWidth: 1,
        fill: 'none',
      },
      '.marker-target': {
        d: 'M 18 -7 0 0 18 7 Z',
        fill: '#005891',
        stroke: '#005891',
      },
    },
  },
};
