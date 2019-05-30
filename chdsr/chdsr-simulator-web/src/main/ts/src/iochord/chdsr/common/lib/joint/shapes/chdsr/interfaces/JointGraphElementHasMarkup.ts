export interface JointGraphElementHasMarkup {
  getMarkup(): string | null;
  setMarkup(markup: string): void;
  getAttr(): joint.dia.Cell.Selectors | null;
  setAttr(attr: joint.dia.Cell.Selectors): void;
}
