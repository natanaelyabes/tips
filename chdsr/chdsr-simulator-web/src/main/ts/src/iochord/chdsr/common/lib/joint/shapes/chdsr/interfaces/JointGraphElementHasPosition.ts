export interface JointGraphElementHasPosition {
  getPosition(): { x: number, y: number } | null;
  setPosition(position: { x: number, y: number }): void;
}
