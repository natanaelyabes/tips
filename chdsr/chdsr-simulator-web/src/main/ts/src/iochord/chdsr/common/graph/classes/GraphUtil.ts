import { GraphElement } from './../interfaces/GraphElement';
export class GraphUtil {
  public static generateRef<T extends GraphElement>(e: T | null): string | null {
    if (e !== null) {
      return e.getId();
    }
    return null;
  }

  public static generateRefs<T extends GraphElement>(map: Map<string, T> | null): Map<string, string | null> | null {
    const refs: Map<string, string | null> = new Map<string, string | null>();
    if (map !== null) {
      for (const k in map) {
        if (map.hasOwnProperty(k)) {
          const v: T = map.get(k) as T;
          if (v !== null) {
            refs.set(k, v.getId());
          } else {
            refs.set(k, null);
          }
        }
      }
    }
    return refs;
  }
}
