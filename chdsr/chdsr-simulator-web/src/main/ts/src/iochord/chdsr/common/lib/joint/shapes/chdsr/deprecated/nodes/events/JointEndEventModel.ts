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
       * @class EndEvent
       * @extends {joint.shapes.chdsr.Event}
       */
      class JointEndEventModel extends joint.shapes.chdsr.JointEventModel {
        public fn_chdsr_get_type(): string;
      }
    }
  }
}


/**
 * 2. Define custom element according to Joint.js pattern
 *
 */
joint.shapes.chdsr.JointEventModel.define('chdsr.JointEndEventModel', {}, {
  fn_chdsr_get_type: (): string => {
    return 'End Event';
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
   * @class EndEventView
   * @extends {joint.dia.ElementView}
   */
  export class JointEndEventView extends joint.dia.ElementView {
    public initialize() {
      super.initialize.apply(this, arguments as any);
    }
  }
}

(Object as any).assign(joint.shapes.chdsr, JointViews);
