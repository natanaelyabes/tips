import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';

export interface GraphDataFunction extends GraphData {
  getInputParameters(): Map<string, GraphDataObjectType> | null;
  setInputParameters(inputParameters: Map<string, GraphDataObjectType>): void;
  getInputParametersRefs(): Map<string, string | null> | null;
  getCode(): string | null;
  setCode(code: string): void;
  getOutputVariables(): Map<string, GraphDataObjectType> | null;
  setOutputVariables(outputVariables: Map<string, GraphDataObjectType>): void;
  getOutputVariablesRefs(): Map<string, string | null> | null;
}
