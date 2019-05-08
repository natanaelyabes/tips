import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataFunction } from '../../interfaces/components/GraphDataFunction';
import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';

export class GraphDataFunctionImpl extends GraphDataImpl implements GraphDataFunction {
  public static readonly TYPE: 'function' = 'function';

  /** @Override */
  public static fn_object_deserialize(object: any): GraphDataFunction {
    const graphData: GraphDataFunction = new GraphDataFunctionImpl();
    // console.log(object);
    return graphData;
  }

  private inputParameters?: Map<string, GraphDataObjectType>;
  private code?: string;
  private outputVariables?: Map<string, GraphDataObjectType>;

  constructor();
  constructor(inputParameters: Map<string, GraphDataObjectType>, code: string, outputVariables: Map<string, GraphDataObjectType>);
  constructor(inputParameters?: Map<string, GraphDataObjectType>, code?: string, outputVariables?: Map<string, GraphDataObjectType>) {
    super();
    this.inputParameters = inputParameters;
    this.code = code;
    this.outputVariables = outputVariables;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_data_function_get_input_parameters(): Map<string, GraphDataObjectType> | undefined {
    return this.inputParameters;
  }

  public fn_graph_data_function_set_input_parameters(inputParameters: Map<string, GraphDataObjectType>): void {
    this.inputParameters = inputParameters;
  }

  public fn_graph_data_function_get_code(): string | undefined {
    return this.code;
  }

  public fn_graph_data_function_set_code(code: string): void {
    this.code = code;
  }

  public fn_graph_data_function_get_output_variables(): Map<string, GraphDataObjectType> | undefined {
    return this.outputVariables;
  }

  public fn_graph_data_function_set_output_variables(outputVariables: Map<string, GraphDataObjectType>): void {
    this.outputVariables = outputVariables;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
