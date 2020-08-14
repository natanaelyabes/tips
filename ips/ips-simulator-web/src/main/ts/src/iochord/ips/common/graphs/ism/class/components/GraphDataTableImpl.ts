import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphDataTableImpl interface.
 *
 * @export
 * @class GraphDataTableImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphDataTableImpl}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export class GraphDataTableImpl extends GraphDataImpl implements GraphDataTable {

  /**
   * Field to identify the type of the node.
   *
   * @static
   * @type {string}
   * @memberof GraphDataTableImpl
   */
  public static TYPE: string = 'datatable';

  /**
   * Deserialize JSON object as GraphDataTableImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphDataTable | null)}
   * @memberof GraphDataTableImpl
   */
  public static deserialize(object: any): GraphDataTable | null {
    const graphDataTable: GraphDataTable = new GraphDataTableImpl();
    GraphDataTableImpl.instance.set(graphDataTable.getId() as string, graphDataTable);
    return graphDataTable;
  }

  /**
   * Fields' or columns' name of data table.
   *
   * @private
   * @type {(TSMap<string, string> | null)}
   * @memberof GraphDataTableImpl
   */
  private fields?: TSMap<string, string> | null;

  /**
   * The data or rows of data table.
   *
   * @private
   * @type {(TSMap<string, TSMap<string, any>> | null)}
   * @memberof GraphDataTableImpl
   */
  private data?: TSMap<string, TSMap<string, any>> | null;

  /**
   * Creates an instance of GraphDataTableImpl.
   *
   * @memberof GraphDataTableImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the fields of current data table node.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphDataTableImpl
   */
  public getFields(): TSMap<string, string> | null {
    return this.fields as TSMap<string, string>;
  }

  /**
   * Assigns the fields of current data table node.
   *
   * @param {TSMap<string, string>} fields
   * @memberof GraphDataTableImpl
   */
  public setFields(fields: TSMap<string, string>): void {
    this.fields = fields as TSMap<string, string>;
  }

  /**
   * Returns the rows for current data table node.
   *
   * @returns {(TSMap<string, TSMap<string, any>> | null)}
   * @memberof GraphDataTableImpl
   */
  public getData(): TSMap<string, TSMap<string, any>> | null {
    return this.data as TSMap<string, TSMap<string, any>>;
  }

  /**
   * Assigns rows to current data table node.
   *
   * @param {TSMap<string, TSMap<string, any>>} data
   * @memberof GraphDataTableImpl
   */
  public setData(data: TSMap<string, TSMap<string, any>>): void {
    this.data = data as TSMap<string, TSMap<string, any>>;
  }

  /**
   * Serialize GraphDataTableImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphDataTableImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
