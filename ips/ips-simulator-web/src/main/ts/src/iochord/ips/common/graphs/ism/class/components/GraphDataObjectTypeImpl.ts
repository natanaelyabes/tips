import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphDataObjectTypeImpl interface.
 *
 * @export
 * @class GraphDataObjectTypeImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphDataObjectTypeImpl}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export class GraphDataObjectTypeImpl extends GraphDataImpl implements GraphDataObjectType {

  /**
   * Field to identify the type of the node.
   *
   * @static
   * @type {string}
   * @memberof GraphDataObjectTypeImpl
   */
  public static TYPE: string = 'objecttype';

  /**
   * Deserialize JSON object as GraphDataObjectTypeImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphDataObjectType | null)}
   * @memberof GraphDataObjectTypeImpl
   */
  public static deserialize(object: any): GraphDataObjectType | null {
    const graphDataObjectType: GraphDataObjectType = new GraphDataObjectTypeImpl();
    graphDataObjectType.setId(object.id);
    graphDataObjectType.setLabel(object.label);
    graphDataObjectType.setType(object.elementType);
    graphDataObjectType.setAttributes(object.attributes);
    graphDataObjectType.setTypeRefs(object.types);
    GraphDataObjectTypeImpl.instance.set(graphDataObjectType.getId() as string, graphDataObjectType);
    return graphDataObjectType;
  }

  /**
   * Types of the object, specified as GraphDataTable.
   *
   * @private
   * @type {(TSMap<string, GraphDataTable> | null)}
   * @memberof GraphDataObjectTypeImpl
   */
  private types?: TSMap<string, GraphDataTable> | null = new TSMap<string, GraphDataTable>();

  /**
   * Types of the object, specified as string reference.
   *
   * @private
   * @type {string}
   * @memberof GraphDataObjectTypeImpl
   */
  private typeRef: string = '';

  /**
   * Creates an instance of GraphDataObjectTypeImpl.
   *
   * @memberof GraphDataObjectTypeImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the types of current object type data node.
   *
   * @returns {(TSMap<string, GraphDataTable> | null)}
   * @memberof GraphDataObjectTypeImpl
   */
  public getTypes(): TSMap<string, GraphDataTable> | null {
    return this.types as TSMap<string, GraphDataTable> | null;
  }

  /**
   * Assigns the types of current object type data node.
   *
   * @param {TSMap<string, GraphDataTable>} types
   * @memberof GraphDataObjectTypeImpl
   */
  public setTypes(types: TSMap<string, GraphDataTable>): void {
    this.types = types || this.types;
  }

  /**
   * Returns the types of current object type data node as string reference.
   *
   * @returns {string}
   * @memberof GraphDataObjectTypeImpl
   */
  public getTypeRefs(): string {
    return this.typeRef;
  }

  /**
   * Assigns the types of current object type data node as string reference.
   *
   * @param {string} ref
   * @memberof GraphDataObjectTypeImpl
   */
  public setTypeRefs(ref: string): void {
    this.typeRef = ref;
  }

  /**
   * Serialize GraphDataObjectTypeImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphDataObjectTypeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
