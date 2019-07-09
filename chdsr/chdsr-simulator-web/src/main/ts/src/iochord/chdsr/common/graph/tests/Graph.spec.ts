// import { GraphDataGenerator } from './../interfaces/components/GraphDataGenerator';
// import { GraphDataObjectType } from './../interfaces/components/GraphDataObjectType';
// import { GraphPage } from './../interfaces/GraphPage';
// import { Graph } from '../interfaces/Graph';
// import { GraphFactory } from '../interfaces/GraphFactory';
// import { GraphFactoryImpl } from '../classes/GraphFactoryImpl';
// import { GraphImpl } from '../classes/GraphImpl';

// class GraphExample {
//   public static create(): void {
//     const graphFactory: GraphFactory = GraphFactoryImpl.fn_graph_factory_get_instance();
//     const net: GraphImpl = graphFactory.fn_graph_factory_create() as GraphImpl;

//     const page = net.fn_graph_get_pages()!.values().next();
//     const customer: GraphDataObjectType = graphFactory.fn_graph_factory_add_object_type(page.value) as GraphDataObjectType;
//     customer.fn_graph_element_set_label('Customer');

//     const customerGenerator: GraphDataGenerator = graphFactory.fn_graph_factory_add_generator(page.value) as GraphDataGenerator;
//     customerGenerator.fn_graph_element_set_label(customer.fn_graph_element_get_label() + ' Generator');
//     customerGenerator.fn_graph_data_generator_set_object_type(customer);
//     customerGenerator.fn_graph_data_generator_set_expression('Exp(20');
//     customerGenerator.fn_graph_data_generator_set_max_arrival(100);

//     console.log(net);
//     console.log(customerGenerator);
//     // return net;
//   }
// }

// GraphExample.create();
