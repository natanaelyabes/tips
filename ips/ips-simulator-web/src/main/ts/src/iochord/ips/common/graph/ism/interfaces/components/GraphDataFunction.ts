import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataFunction extends GraphData {
  getInputParameters(): TSMap<string, GraphDataObjectType> | null;
  setInputParameters(inputParameters: TSMap<string, GraphDataObjectType>): void;
  getInputParametersRefs(): TSMap<string, string | null> | null;
  getCode(): string | null;
  setCode(code: string): void;
  getOutputVariables(): TSMap<string, GraphDataObjectType> | null;
  setOutputVariables(outputVariables: TSMap<string, GraphDataObjectType>): void;
  getOutputVariablesRefs(): TSMap<string, string | null> | null;
}
