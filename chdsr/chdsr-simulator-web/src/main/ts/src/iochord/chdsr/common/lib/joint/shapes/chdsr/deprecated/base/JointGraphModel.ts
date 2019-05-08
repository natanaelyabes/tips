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
       * @class JointGraphModel
       * @extends {joint.dia.Paper}
       */
      class JointGraphModel extends joint.dia.Paper {
      }
    }
  }
}

joint.shapes.chdsr.JointGraphModel = joint.dia.Paper.extend({

});
