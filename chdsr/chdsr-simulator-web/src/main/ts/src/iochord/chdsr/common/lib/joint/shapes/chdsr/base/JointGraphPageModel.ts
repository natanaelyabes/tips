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
      class JointGraphPageModel extends joint.dia.Graph {
      }
    }
  }
}

/**
 * 2. Provide the implementation directly to the Joint.js library
 *
 */
joint.shapes.chdsr.JointGraphPageModel = joint.dia.Graph.extend({

});
