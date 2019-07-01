import { GraphData } from './../interfaces/GraphData';
import { GraphElementImpl } from './GraphElementImpl';

export class GraphDataImpl extends GraphElementImpl implements GraphData {
  public static deserialize(object: any): any | null {
    const graphData: Map<string, GraphData> = new Map<string, GraphData>();
    return graphData;
  }

  constructor() {
    super();
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
