import { GraphDataFunction } from '../../interfaces/components/GraphDataFunction';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { GraphUtil } from '../GraphUtil';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataFunctionImpl extends GraphDataImpl implements GraphDataFunction {
  public static TYPE: string = 'function';

  /** @Override */
  public static deserialize(object: any): GraphDataFunction | null {
    const graphData: GraphDataFunction = new GraphDataFunctionImpl();
    graphData.setId(object.id);
    graphData.setLabel(object.label);
    graphData.setType(object.elementType);
    graphData.setAttributes(object.attributes as TSMap<string, string>);
    graphData.setInputParameters(object.inputParameters as TSMap<string, GraphDataObjectType>);
    graphData.setCode(object.code);
    graphData.setOutputVariables(object.outputVariables as TSMap<string, GraphDataObjectType>);
    GraphDataFunctionImpl.instance.set(graphData.getId() as string, graphData);
    return graphData;
  }

  private inputParameters?: TSMap<string, GraphDataObjectType> | null = new TSMap<string, GraphDataObjectType>();
  private inputParametersRefs?: TSMap<string, string> | null = new TSMap<string, string>();
  private code?: string | null;
  private outputVariables?: TSMap<string, GraphDataObjectType> | null = new TSMap<string, GraphDataObjectType>();
  private outputVariablesRefs?: TSMap<string, string> | null = new TSMap<string, string>();

  constructor() {
    super();
  }

  public getInputParameters(): TSMap<string, GraphDataObjectType> | null {
    return this.inputParameters as TSMap<string, GraphDataObjectType> | null;
  }

  public setInputParameters(inputParameters: TSMap<string, GraphDataObjectType>): void {
    this.inputParameters = inputParameters as TSMap<string, GraphDataObjectType>;
  }

  public getInputParametersRefs(): TSMap<string, string> | null {
    return this.inputParametersRefs as TSMap<string, string>;
  }

  public setInputParametersRefs(inputParametersRefs: TSMap<string, string> | null) {
    this.inputParametersRefs = inputParametersRefs as TSMap<string, string>;
  }

  public getCode(): string | null {
    return this.code as string | null;
  }

  public setCode(code: string): void {
    this.code = code || this.code;
  }

  public getOutputVariables(): TSMap<string, GraphDataObjectType> | null {
    return this.outputVariables as TSMap<string, GraphDataObjectType> | null;
  }

  public setOutputVariables(outputVariables: TSMap<string, GraphDataObjectType>): void {
    this.outputVariables = outputVariables as TSMap<string, GraphDataObjectType>;
  }

  public getOutputVariablesRefs(): TSMap<string, string> | null {
    return this.outputVariablesRefs as TSMap<string, string>;
  }

  public setOutputVariablesRefs(outputVariablesRefs: TSMap<string, string> | null) {
    this.outputVariablesRefs = outputVariablesRefs as TSMap<string, string>;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
