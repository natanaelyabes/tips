import { GraphData } from './../interfaces/GraphData';
import { GraphElementImpl } from './GraphElementImpl';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataImpl extends GraphElementImpl implements GraphData {
  public static instance: Map<string, GraphData> = new Map<string, GraphData>();

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
