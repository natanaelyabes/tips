import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';

export interface GraphDataObjectType extends GraphData {
  getTypes(): Map<string, GraphDataTable> | null;
  setTypes(fields: Map<string, GraphDataTable>): void;
  getTypeRefs(): Map<string, string | null> | null;
}
