export interface GraphRule {
  validateInputNodes(): Error | null;
  validateOutputNodes(): Error | null;
}
