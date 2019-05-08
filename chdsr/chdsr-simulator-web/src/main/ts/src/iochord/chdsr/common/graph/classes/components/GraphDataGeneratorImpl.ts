import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';

export class GraphDataGeneratorImpl extends GraphDataImpl implements GraphDataGenerator {
  public static readonly TYPE: 'generator' = 'generator';

  /** @Override */
  public static fn_object_deserialize(object: any): GraphDataGenerator {
    const graphDataGenerator: GraphDataGenerator = new GraphDataGeneratorImpl();
    graphDataGenerator.fn_graph_element_set_id(object.id);
    graphDataGenerator.fn_graph_element_set_label(object.label);
    graphDataGenerator.fn_graph_element_set_type(object.type);
    graphDataGenerator.fn_graph_element_set_attributes(object.attributes as Map<string, string>);
    return graphDataGenerator;
  }

  private objectType: GraphDataObjectType | null;
  private expression: string | null;
  private entitiesPerArrival: number | null;
  private maxArrival: number | null;
  private firstCreation: number | null;

  constructor();
  constructor(objectType: GraphDataObjectType, expression: string, entitiesPerArrival: number, maxArrival: number, firstCreation: number);
  constructor(objectType?: GraphDataObjectType, expression?: string, entitiesPerArrival?: number, maxArrival?: number, firstCreation?: number) {
    super();
    this.objectType = objectType || null;
    this.expression = expression || null;
    this.entitiesPerArrival = entitiesPerArrival || null;
    this.maxArrival = maxArrival || null;
    this.firstCreation = firstCreation || null;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_data_generator_get_object_type(): GraphDataObjectType | null {
    return this.objectType;
  }

  public fn_graph_data_generator_set_object_type(objectType: GraphDataObjectType): void {
    this.objectType = objectType;
  }

  public fn_graph_data_generator_get_expression(): string | null {
    return this.expression;
  }

  public fn_graph_data_generator_set_expression(expression: string): void {
    this.expression = expression;
  }

  public fn_graph_data_generator_get_entities_per_arrival(): number | null {
    return this.entitiesPerArrival;
  }

  public fn_graph_data_generator_set_entities_per_arrival(entitiesPerArrival: number): void {
    this.entitiesPerArrival = entitiesPerArrival;
  }

  public fn_graph_data_generator_get_max_arrival(): number | null {
    return this.maxArrival;
  }

  public fn_graph_data_generator_set_max_arrival(maxArrival: number): void {
    this.maxArrival = maxArrival;
  }

  public fn_graph_data_generator_get_first_creation(): number | null {
    return this.firstCreation;
  }

  public fn_graph_data_generator_set_first_creation(firstCreation: number): void {
    this.firstCreation = firstCreation;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
