import { GraphDataGenerator } from './../interfaces/components/GraphDataGenerator';
import { GraphDataObjectType } from './../interfaces/components/GraphDataObjectType';
import { GraphPage } from './../interfaces/GraphPage';
import { Graph } from '../interfaces/Graph';
import { GraphFactory } from '../interfaces/GraphFactory';
import { GraphFactoryImpl } from '../classes/GraphFactoryImpl';
import { GraphImpl } from '../classes/GraphImpl';

class GraphExample {
  public static create(): void {
    const graphFactory: GraphFactory = GraphFactoryImpl.getInstance();
    const net: GraphImpl = graphFactory.create() as GraphImpl;

    const page = net.getPages()!.values().next();
    const customer: GraphDataObjectType = graphFactory.addObjectType(page.value) as GraphDataObjectType;
    customer.setLabel('Customer');

    const customerGenerator: GraphDataGenerator = graphFactory.addGenerator(page.value) as GraphDataGenerator;
    customerGenerator.setLabel(customer.getLabel() + ' Generator');
    customerGenerator.setObjectType(customer);
    customerGenerator.setExpression('Exp(20');
    customerGenerator.setMaxArrival(100);

    console.log((net.getPages()!.get('MODEL-0') as GraphPage).getData());
    console.log(customerGenerator);
  }
}

GraphExample.create();
