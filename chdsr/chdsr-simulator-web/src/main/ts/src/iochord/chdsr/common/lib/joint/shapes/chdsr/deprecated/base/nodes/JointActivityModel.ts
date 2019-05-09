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
       * @class Activity
       * @extends {joint.shapes.basic.Generic}
       */
      class JointActivityModel extends joint.shapes.basic.Generic {}
    }
  }
}


/**
 * 2. Define custom element according to Joint.js pattern
 *
 */
joint.shapes.basic.Generic.define('chdsr.JointActivityModel', {
  size: { width: 80, height: 40 },
  attrs: {
    '.root' : {
      refWidth: '100%',
      refHeight: '100%',
      stroke: 'black',
      strokeWidth: 2,
      transform: 'translate(-40, 0)',
    },
    '.label' : {
      ref: '.root',
      refY: '130%',
      textAnchor: 'middle',
      yAlignment: 'middle',
      fill: 'black',
    },
  },
}, {
  markup: '<g class="rotatable"><g class="scalable"><rect class="root"/></g></g><text class="label"/>',
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
   * @class ActivityView
   * @extends {joint.dia.ElementView}
   */
  export class JointActivityView extends joint.dia.ElementView {
    public initialize() {
      super.initialize.apply(this, arguments as any);
    }
  }
}

(Object as any).assign(joint.shapes.chdsr, JointViews);
