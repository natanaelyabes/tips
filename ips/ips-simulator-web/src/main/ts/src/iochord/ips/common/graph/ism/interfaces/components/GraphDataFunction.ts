import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 * The interface of graph data function.
 *
 * @export
 * @interface GraphDataFunction
 * @extends {GraphData}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataFunction extends GraphData {

  /**
   * Returns the input parameters of current function data node.
   *
   * @returns {(TSMap<string, GraphDataObjectType> | null)}
   * @memberof GraphDataFunction
   */
  getInputParameters(): TSMap<string, GraphDataObjectType> | null;

  /**
   * Assigns input parameters to the current function data node.
   *
   * @param {TSMap<string, GraphDataObjectType>} inputParameters
   * @memberof GraphDataFunction
   */
  setInputParameters(inputParameters: TSMap<string, GraphDataObjectType>): void;

  /**
   * Returns the input parameters of current function data node as string reference.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphDataFunction
   */
  getInputParametersRefs(): TSMap<string, string> | null;

  /**
   * Returns the code block of current function data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataFunction
   */
  getCode(): string | null;

  /**
   * Assigns the code block of current function data node.
   *
   * @param {string} code
   * @memberof GraphDataFunction
   */
  setCode(code: string): void;

  /**
   * Returns output variables of current function data node.
   *
   * @returns {(TSMap<string, GraphDataObjectType> | null)}
   * @memberof GraphDataFunction
   */
  getOutputVariables(): TSMap<string, GraphDataObjectType> | null;

  /**
   * Assigns output variabels to current function data node.
   *
   * @param {TSMap<string, GraphDataObjectType>} outputVariables
   * @memberof GraphDataFunction
   */
  setOutputVariables(outputVariables: TSMap<string, GraphDataObjectType>): void;

  /**
   * Returns output variables of current function data node as string reference.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphDataFunction
   */
  getOutputVariablesRefs(): TSMap<string, string> | null;
}
