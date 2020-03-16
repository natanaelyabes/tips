import { GraphDataFunction } from '../../interfaces/components/GraphDataFunction';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphDataFunctionImpl interface.
 *
 * @export
 * @class GraphDataFunctionImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphDataFunctionImpl}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphDataFunctionImpl extends GraphDataImpl implements GraphDataFunction {

  /**
   * Field to identify the type of the node.
   *
   * @static
   * @type {string}
   * @memberof GraphBranchNodeImpl
   */
  public static TYPE: string = 'function';

  /**
   * Deserialize JSON object as GraphDataFunctionImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphBranchNode | null)}
   * @memberof GraphBranchNodeImpl
   */
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

  /**
   * The input parameters for current function data node.
   *
   * @private
   * @type {(TSMap<string, GraphDataObjectType> | null)}
   * @memberof GraphDataFunctionImpl
   */
  private inputParameters?: TSMap<string, GraphDataObjectType> | null = new TSMap<string, GraphDataObjectType>();

  /**
   * The input parameters for current function data node as string reference.
   *
   * @private
   * @type {(TSMap<string, string> | null)}
   * @memberof GraphDataFunctionImpl
   */
  private inputParametersRefs?: TSMap<string, string> | null = new TSMap<string, string>();

  /**
   * The code block of current function data node.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphDataFunctionImpl
   */
  private code?: string | null;

  /**
   * The object type of current function data node output variables.
   *
   * @private
   * @type {(TSMap<string, GraphDataObjectType> | null)}
   * @memberof GraphDataFunctionImpl
   */
  private outputVariables?: TSMap<string, GraphDataObjectType> | null = new TSMap<string, GraphDataObjectType>();

  /**
   * The object type of current function data node output variables as string reference.
   *
   * @private
   * @type {(TSMap<string, string> | null)}
   * @memberof GraphDataFunctionImpl
   */
  private outputVariablesRefs?: TSMap<string, string> | null = new TSMap<string, string>();

  /**
   * Creates an instance of GraphDataFunctionImpl.
   *
   * @memberof GraphDataFunctionImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the input parameters of current function data node.
   *
   * @returns {(TSMap<string, GraphDataObjectType> | null)}
   * @memberof GraphDataFunctionImpl
   */
  public getInputParameters(): TSMap<string, GraphDataObjectType> | null {
    return this.inputParameters as TSMap<string, GraphDataObjectType> | null;
  }

  /**
   * Assigns input parameters to the current function data node.
   *
   * @param {TSMap<string, GraphDataObjectType>} inputParameters
   * @memberof GraphDataFunctionImpl
   */
  public setInputParameters(inputParameters: TSMap<string, GraphDataObjectType>): void {
    this.inputParameters = inputParameters as TSMap<string, GraphDataObjectType>;
  }

  /**
   * Returns the input parameters of current function data node as string reference.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphDataFunctionImpl
   */
  public getInputParametersRefs(): TSMap<string, string> | null {
    return this.inputParametersRefs as TSMap<string, string>;
  }

  /**
   * Assigns input parameters to the current function data node as string reference.
   *
   * @param {(TSMap<string, string> | null)} inputParametersRefs
   * @memberof GraphDataFunctionImpl
   */
  public setInputParametersRefs(inputParametersRefs: TSMap<string, string> | null) {
    this.inputParametersRefs = inputParametersRefs as TSMap<string, string>;
  }

  /**
   * Returns the code block of current function data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataFunctionImpl
   */
  public getCode(): string | null {
    return this.code as string | null;
  }

  /**
   * Assigns the code block of current function data node.
   *
   * @param {string} code
   * @memberof GraphDataFunctionImpl
   */
  public setCode(code: string): void {
    this.code = code as string;
  }

  /**
   * Returns output variables of current function data node.
   *
   * @returns {(TSMap<string, GraphDataObjectType> | null)}
   * @memberof GraphDataFunctionImpl
   */
  public getOutputVariables(): TSMap<string, GraphDataObjectType> | null {
    return this.outputVariables as TSMap<string, GraphDataObjectType> | null;
  }

  /**
   * Assigns output variabels to current function data node.
   *
   * @param {TSMap<string, GraphDataObjectType>} outputVariables
   * @memberof GraphDataFunctionImpl
   */
  public setOutputVariables(outputVariables: TSMap<string, GraphDataObjectType>): void {
    this.outputVariables = outputVariables as TSMap<string, GraphDataObjectType>;
  }

  /**
   * Returns output variables of current function data node as string reference.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphDataFunctionImpl
   */
  public getOutputVariablesRefs(): TSMap<string, string> | null {
    return this.outputVariablesRefs as TSMap<string, string>;
  }

  /**
   * Assigns output variables of current function data node as string reference.
   *
   * @param {(TSMap<string, string> | null)} outputVariablesRefs
   * @memberof GraphDataFunctionImpl
   */
  public setOutputVariablesRefs(outputVariablesRefs: TSMap<string, string> | null) {
    this.outputVariablesRefs = outputVariablesRefs as TSMap<string, string>;
  }

  /**
   * Serialize GraphDataFunctionImpl as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphDataFunctionImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
