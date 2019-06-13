import { GraphDataFunction } from './../../interfaces/components/GraphDataFunction';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { GraphUtil } from '../GraphUtil';

export class GraphDataFunctionImpl extends GraphDataImpl implements GraphDataFunction {
  public static readonly TYPE: string | null = 'function';
  public static instance: Map<string, GraphDataFunction> = new Map<string, GraphDataFunction>();

  /** @Override */
  public static deserialize(object: any): GraphDataFunction | null {
    const graphData: GraphDataFunction = new GraphDataFunctionImpl();
    graphData.setId(object.id);
    graphData.setLabel(object.label);
    graphData.setType(object.elementType);
    graphData.setAttributes(object.attributes as Map<string, string>);
    graphData.setInputParameters(object.inputParameters as Map<string, GraphDataObjectType>);
    graphData.setCode(object.code);
    graphData.setOutputVariables(object.outputVariables as Map<string, GraphDataObjectType>);
    GraphDataFunctionImpl.instance.set(graphData.getId() as string, graphData);
    return graphData;
  }

  private inputParameters: Map<string, GraphDataObjectType> | null;
  private code: string | null;
  private outputVariables: Map<string, GraphDataObjectType> | null;

  constructor();
  constructor(inputParameters: Map<string, GraphDataObjectType>, code: string, outputVariables: Map<string, GraphDataObjectType>);
  constructor(inputParameters?: Map<string, GraphDataObjectType>, code?: string, outputVariables?: Map<string, GraphDataObjectType>) {
    super();
    this.inputParameters = inputParameters || new Map<string, GraphDataObjectType>() || null;
    this.code = code || null;
    this.outputVariables = outputVariables || new Map<string, GraphDataObjectType>() || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getInputParameters(): Map<string, GraphDataObjectType> | null {
    return this.inputParameters;
  }

  public setInputParameters(inputParameters: Map<string, GraphDataObjectType>): void {
    this.inputParameters = inputParameters;
  }

  public getInputParametersRefs(): Map<string, string | null> | null {
    return GraphUtil.generateRefs(this.getInputParameters());
  }

  public getCode(): string | null {
    return this.code;
  }

  public setCode(code: string): void {
    this.code = code;
  }

  public getOutputVariables(): Map<string, GraphDataObjectType> | null {
    return this.outputVariables;
  }

  public setOutputVariables(outputVariables: Map<string, GraphDataObjectType>): void {
    this.outputVariables = outputVariables;
  }

  public getOutputVariablesRefs(): Map<string, string | null> | null {
    return GraphUtil.generateRefs(this.getOutputVariables());
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
