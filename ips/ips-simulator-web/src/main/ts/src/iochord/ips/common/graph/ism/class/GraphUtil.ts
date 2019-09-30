import { GraphElement } from '../interfaces/GraphElement';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphUtil {
  public static generateRef<T extends GraphElement>(e: T | null): string | null {
    if (e !== null) {
      return e.getId();
    }
    return null;
  }

  public static generateRefs<T extends GraphElement>(map: TSMap<string, T> | null): TSMap<string, string | null> | null {
    const refs: TSMap<string, string | null> = new TSMap<string, string | null>();
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
