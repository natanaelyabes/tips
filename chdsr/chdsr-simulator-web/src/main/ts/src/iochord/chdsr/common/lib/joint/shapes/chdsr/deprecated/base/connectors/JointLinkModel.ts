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
       * @class Link
       * @extends {joint.shapes.standard.Link}
       */
      class JointLinkModel extends joint.dia.Link {}
    }
  }
}


/**
 * 2. Define custom element according to Joint.js pattern
 *
 */
joint.dia.Link.define('chdsr.JointLinkModel', {
  attrs: {
    '.marker-target': { d: 'M 18 -7 0 0 18 7 Z' },
    'line': {
      stroke: '#222138',
      strokeDasharray: '0',
      strokeWidth: 1,
      fill: 'none',
    },
  },
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
   * @class LinkView
   * @extends {joint.dia.ElementView}
   */
  export class JointLinkView extends joint.dia.ElementView {
    public initialize() {
      super.initialize.apply(this, arguments as any);
    }
  }
}

(Object as any).assign(joint.shapes.chdsr, JointViews);
