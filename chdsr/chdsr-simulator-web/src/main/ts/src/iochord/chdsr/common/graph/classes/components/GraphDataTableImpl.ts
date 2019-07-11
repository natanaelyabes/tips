import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataTableImpl extends GraphDataImpl implements GraphDataTable {
  public static TYPE: string = 'datatable';

  // public static instance: Map<number, Map<string, GraphDataTable>> = new Map<number, Map<string, GraphDataTable>>();

  /** @Override */
  public static deserialize(object: any): Map<string, GraphDataTable> | null {
    const graphDataTableMap: Map<string, GraphDataTable> = new Map<string, GraphDataTable>();
    // GraphDataTableImpl.instance.set(GraphDataTableImpl.instance.size, graphDataTableMap);
    return graphDataTableMap;
  }


  private fields?: Map<string, string> | null;
  private data?: Map<string, Map<string, object>> | null;

  constructor() {
    super();
  }

  public getFields(): Map<string, string> | null {
    return this.fields as Map<string, string> | null;
  }

  public setFields(fields: Map<string, string>): void {
    this.fields = fields || this.fields;
  }

  public getData(): Map<string, Map<string, object>> | null {
    return this.data as Map<string, Map<string, object>> | null;
  }

  public setData(data: Map<string, Map<string, object>>): void {
    this.data = data || this.data;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
