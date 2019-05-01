// Imports
import * as joint from 'jointjs';
import { GraphPage } from '@/iochord/chdsr/common/graph/interfaces/pages/GraphPage';
import { GraphNode } from '@/iochord/chdsr/common/graph/interfaces/base/nodes/GraphNode';
import { GraphConnector } from '@/iochord/chdsr/common/graph/interfaces/base/arcs/GraphConnector';


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
      class JointGraphPageModel extends joint.dia.Graph implements GraphPage {
        public fn_graph_element_get_id(): string;
        public fn_graph_element_get_name(): string;
        public fn_graph_element_get_type(): string;
        public fn_graph_element_get_attributes(): Map<string, string>;
        public fn_graph_page_get_nodes(): Map<string, GraphNode>;
        public fn_graph_page_get_arcs(): Map<string, GraphConnector>;
      }
    }
  }
}

/**
 * 2. Provide the implementation directly to the Joint.js library
 *
 */
joint.shapes.chdsr.JointGraphPageModel = joint.dia.Graph.extend({
  fn_graph_element_get_id(): string {
    const _super: joint.dia.Graph = this; // get the super method
    console.log(_super);
    return '1';
  },
  fn_graph_element_get_name(): string {
    return 'test';
  },
  fn_graph_element_get_type(): string {
    return 'type: test';
  },
  fn_graph_element_get_attribute(): Map<string, string> {
    return new Map<string, string>().set('test-key', 'test-value');
  },
  fn_graph_page_get_nodes(): Map<string, GraphNode> {
    return new Map<string, GraphNode>().set('node-key', {} as GraphNode);
  },
  fn_graph_page_get_arcs(): Map<string, GraphConnector> {
    return new Map<string, GraphConnector>().set('arc-key', {} as GraphConnector);
  },
});
