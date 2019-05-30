import { GraphElement } from './GraphElement';

export interface GraphConnector extends GraphElement {
  getSource(): GraphElement | null;
  setSource(source: GraphElement): void;
  getSourceIndex(): number | null;
  setSourceIndex(index: number): void;
  getSourceRef(): string | null;
  getTarget(): GraphElement | null;
  setTarget(target: GraphElement): void;
  getTargetIndex(): number | null;
  setTargetIndex(index: number): void;
  getTargetRef(): string | null;
}
