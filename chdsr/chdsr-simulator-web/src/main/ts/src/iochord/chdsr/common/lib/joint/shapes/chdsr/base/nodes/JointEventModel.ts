// Imports
import * as joint from 'jointjs';


/**
 * Steps:
 * 1. Providing types for custom element by injecting namespace in the Joint.js module.
 */
declare module 'jointjs' {
  namespace shapes {
    namespace chdsr {

      /**
       *
       *
       * @class Event
       * @extends {joint.shapes.basic.Generic}
       */
      class JointEventModel extends joint.shapes.basic.Generic {}
    }
  }
}


/**
 * 2. Define custom element according to Joint.js pattern
 *
 */
joint.shapes.basic.Generic.define('chdsr.JointEventModel', {
  size: { width: 20, height: 20 },
  attrs: {
    '.root': {
      r: 10,
      strokeWidth: 2,
      transform: 'translate(0, 20)',
    },
    '.label': {
      ref: '.root',
      refY: '150%',
      textAnchor: 'middle',
      yAlignment: 'middle',
      fill: 'black',
    },
  },
}, {
  markup: '<g class="rotatable"><g class="scalable"><circle class="root"/></g></g><text class="label"/>',
});


/**
 * 3. Declare view for the custom element
 *
 */
namespace JointViews {

  /**
   *
   *
   * @export
   * @class EventView
   * @extends {joint.dia.ElementView}
   */
  export class EventView extends joint.dia.ElementView {
    public initialize() {
      super.initialize.apply(this, arguments as any);
    }
  }
}

(Object as any).assign(joint.shapes.chdsr, JointViews);
