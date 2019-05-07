import { Graph } from '../interfaces/Graph';
import { GraphFactory } from '../interfaces/GraphFactory';
import { GraphFactoryImpl } from '../classes/GraphFactoryImpl';
import { GraphImpl } from '../classes/GraphImpl';

class GraphExample {
  public static create(): Graph {
    const graphFactory: GraphFactory = GraphFactoryImpl.fn_graph_factory_get_instance();
    const net: GraphImpl = graphFactory.fn_graph_factory_create() as GraphImpl;

    const page = net.fn_graph_get_pages()!.values().next();

    return net;
  }
}

console.log(GraphExample.create());
