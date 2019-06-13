import { GraphDataObjectType } from './../../interfaces/components/GraphDataObjectType';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { GraphUtil } from '../GraphUtil';

export class GraphDataObjectTypeImpl extends GraphDataImpl implements GraphDataObjectType {
  public static readonly TYPE: string | null = 'objecttype';
  public static instance: Map<string, GraphDataObjectType> = new Map<string, GraphDataObjectType>();

  /** @Override */
  public static deserialize(object: any): GraphDataObjectType | null {
    const graphDataObjectType: GraphDataObjectType = new GraphDataObjectTypeImpl();
    graphDataObjectType.setId(object.id);
    graphDataObjectType.setLabel(object.label);
    graphDataObjectType.setType(object.type);
    graphDataObjectType.setAttributes(object.attributes);
    graphDataObjectType.setTypes(object.types);
    GraphDataObjectTypeImpl.instance.set(graphDataObjectType.getId() as string, graphDataObjectType);
    return graphDataObjectType;
  }

  private types: Map<string, GraphDataTable> | null = new Map<string, GraphDataTable>() || null;

  constructor();
  constructor(types?: Map<string, GraphDataTable>) {
    super();
    this.types = types || new Map<string, GraphDataTable>() || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getTypes(): Map<string, GraphDataTable> | null {
    return this.types;
  }

  public setTypes(types: Map<string, GraphDataTable>): void {
    this.types = types;
  }

  public getTypeRefs(): Map<string, string | null> | null {
    return GraphUtil.generateRefs(this.getTypes());
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
