import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { GraphUtil } from '../GraphUtil';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataObjectTypeImpl extends GraphDataImpl implements GraphDataObjectType {
  public static TYPE: string = 'objecttype';

  /** @Override */
  public static deserialize(object: any): GraphDataObjectType | null {
    const graphDataObjectType: GraphDataObjectType = new GraphDataObjectTypeImpl();
    graphDataObjectType.setId(object.id);
    graphDataObjectType.setLabel(object.label);
    graphDataObjectType.setType(object.elementType);
    graphDataObjectType.setAttributes(object.attributes);
    graphDataObjectType.setTypes(object.types);
    GraphDataObjectTypeImpl.instance.set(graphDataObjectType.getId() as string, graphDataObjectType);
    return graphDataObjectType;
  }

  private types?: TSMap<string, GraphDataTable> | null = new TSMap<string, GraphDataTable>();

  constructor() {
    super();
  }

  public getTypes(): TSMap<string, GraphDataTable> | null {
    return this.types as TSMap<string, GraphDataTable> | null;
  }

  public setTypes(types: TSMap<string, GraphDataTable>): void {
    this.types = types || this.types;
  }

  public getTypeRefs(): TSMap<string, string | null> | null {
    return GraphUtil.generateRefs(this.getTypes());
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
