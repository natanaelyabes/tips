export interface JointGraphElementHasSize {
  getSize(): { width: number, height: number } | null;
  setSize(size: { width: number, height: number }): void;
}
