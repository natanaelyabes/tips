import { GraphDataObjectType } from './../../interfaces/components/GraphDataObjectType';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { GraphUtil } from '../GraphUtil';

export class GraphDataObjectTypeImpl extends GraphDataImpl implements GraphDataObjectType {
  public static instance: Map<string, GraphDataObjectType> = new Map<string, GraphDataObjectType>();

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

  private types?: Map<string, GraphDataTable> | null = new Map<string, GraphDataTable>();

  constructor() {
    super();
  }

  public getTypes(): Map<string, GraphDataTable> | null {
    return this.types as Map<string, GraphDataTable> | null;
  }

  public setTypes(types: Map<string, GraphDataTable>): void {
    this.types = types || this.types;
  }

  public getTypeRefs(): Map<string, string | null> | null {
    return GraphUtil.generateRefs(this.getTypes());
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
