import { Graph } from '@/iochord/ips/common/graph/ism/interfaces/Graph';
import { Observable, Subject } from 'rxjs';

/**
 * A class to define graph as a rxjs subject.
 *
 * @export
 * @class GraphSubject
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default class GraphSubject {

  public static toObservable(): Observable<Graph> {
    return this.graphSubject.asObservable();
  }

  public static update(graph: Graph): void {
    this.graphSubject.next(graph);
  }

  public static clear(): void {
    this.graphSubject.next();
  }

  private static graphSubject: Subject<Graph> = new Subject<Graph>();
}
