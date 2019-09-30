import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataTableImpl extends GraphDataImpl implements GraphDataTable {
  public static TYPE: string = 'datatable';

  // public static instance: TSMap<number, TSMap<string, GraphDataTable>> = new TSMap<number, TSMap<string, GraphDataTable>>();

  /** @Override */
  public static deserialize(object: any): TSMap<string, GraphDataTable> | null {
    const graphDataTableMap: TSMap<string, GraphDataTable> = new TSMap<string, GraphDataTable>();
    // GraphDataTableImpl.instance.set(GraphDataTableImpl.instance.size, graphDataTableMap);
    return graphDataTableMap;
  }


  private fields?: TSMap<string, string> | null;
  private data?: TSMap<string, TSMap<string, object>> | null;

  constructor() {
    super();
  }

  public getFields(): TSMap<string, string> | null {
    return this.fields as TSMap<string, string> | null;
  }

  public setFields(fields: TSMap<string, string>): void {
    this.fields = fields || this.fields;
  }

  public getData(): TSMap<string, TSMap<string, object>> | null {
    return this.data as TSMap<string, TSMap<string, object>> | null;
  }

  public setData(data: TSMap<string, TSMap<string, object>>): void {
    this.data = data || this.data;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
