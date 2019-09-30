import { GraphData } from '../interfaces/GraphData';
import { GraphElementImpl } from './GraphElementImpl';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataImpl extends GraphElementImpl implements GraphData {
  public static instance: TSMap<string, GraphData> = new TSMap<string, GraphData>();

  public static deserialize(object: any): any | null {
    const graphData: TSMap<string, GraphData> = new TSMap<string, GraphData>();
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
