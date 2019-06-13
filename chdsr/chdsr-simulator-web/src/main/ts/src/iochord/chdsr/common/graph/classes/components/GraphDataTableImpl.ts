import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';

export class GraphDataTableImpl extends GraphDataImpl implements GraphDataTable {
  public static readonly TYPE: string | null = 'datatable';
  public static instance: Map<number, Map<string, GraphDataTable>> = new Map<number, Map<string, GraphDataTable>>();

  /** @Override */
  public static deserialize(object: any): Map<string, GraphDataTable> | null {
    const graphDataTableMap: Map<string, GraphDataTable> = new Map<string, GraphDataTable>();
    GraphDataTableImpl.instance.set(GraphDataTableImpl.instance.size, graphDataTableMap);
    return graphDataTableMap;
  }

  private fields: Map<string, string> | null;
  private data: Map<string, Map<string, object>> | null;

  constructor();
  constructor(fields: Map<string, string>, data: Map<string, Map<string, object>>);
  constructor(fields?: Map<string, string>, data?: Map<string, Map<string, object>>) {
    super();
    this.fields = fields || null || new Map<string, string>();
    this.data = data || null || new Map<string, Map<string, object>>();
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getFields(): Map<string, string> | null {
    return this.fields;
  }

  public setFields(fields: Map<string, string>): void {
    this.fields = fields;
  }

  public getData(): Map<string, Map<string, object>> | null {
    return this.data;
  }

  public setData(data: Map<string, Map<string, object>>): void {
    this.data = data;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
