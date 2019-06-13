import { GraphData } from '../GraphData';

export interface GraphDataTable extends GraphData {
  getFields(): Map<string, string> | null;
  setFields(fields: Map<string, string>): void;
  getData(): Map<string, Map<string, object>> | null;
  setData(data: Map<string, Map<string, object>>): void;
}
