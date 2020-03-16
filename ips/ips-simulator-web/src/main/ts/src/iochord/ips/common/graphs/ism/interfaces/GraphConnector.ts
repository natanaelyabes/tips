import { GraphElement } from './GraphElement';

/**
 * The interface of graph configuration.
 *
 * @export
 * @interface GraphConnector
 * @extends {GraphElement}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface GraphConnector extends GraphElement {
  getSource(): GraphElement | null;
  setSource(source: GraphElement): void;
  getSourceRef(): string | null;
  setSourceRef(source: string): void;
  getSourceIndex(): number | null;
  setSourceIndex(index: number): void;
  getTarget(): GraphElement | null;
  setTarget(target: GraphElement): void;
  getTargetRef(): string | null;
  setTargetRef(target: string): void;
  getTargetIndex(): number | null;
  setTargetIndex(index: number): void;
}
