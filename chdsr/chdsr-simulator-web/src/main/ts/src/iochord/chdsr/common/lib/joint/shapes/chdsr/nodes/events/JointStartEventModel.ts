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
       * @class StartEvent
       * @extends {joint.shapes.chdsr.Event}
       */
      class JointStartEventModel extends joint.shapes.chdsr.JointEventModel {
        public fn_chdsr_get_type(): string;
      }
    }
  }
}


/**
 * 2. Define custom element according to Joint.js pattern
 *
 */
joint.shapes.chdsr.JointEventModel.define('chdsr.JointStartEventModel', {}, {
  fn_chdsr_get_type: (): string => {
    return 'Start Event';
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
   * @class StartEventView
   * @extends {joint.dia.ElementView}
   */
  export class JointStartEventView extends joint.dia.ElementView {
    public initialize() {
      super.initialize.apply(this, arguments as any);
    }
  }
}

(Object as any).assign(joint.shapes.chdsr, JointViews);
