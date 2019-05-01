// Imports
import * as joint from 'jointjs';
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import { GraphPage } from '@/iochord/chdsr/common/graph/interfaces/pages/GraphPage';
import { GraphConfiguration } from '@/iochord/chdsr/common/graph/interfaces/GraphConfiguration';
import { GraphDataNode } from '@/iochord/chdsr/common/graph/interfaces/base/nodes/GraphDataNode';

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
      class JointGraphModel extends joint.dia.Paper implements Graph {
        public fn_graph_element_get_id(): string;
        public fn_graph_element_get_name(): string;
        public fn_graph_element_get_type(): string;
        public fn_graph_element_get_attributes(): Map<string, string>;
        public fn_graph_get_version(): string;
        public fn_graph_get_pages(): Map<string, GraphPage>;
        public fn_graph_get_configurations(): Map<string, GraphConfiguration>;
        public fn_graph_get_data(): Map<string, GraphDataNode>;
      }
    }
  }
}

joint.shapes.chdsr.JointGraphModel = joint.dia.Paper.extend({

});
