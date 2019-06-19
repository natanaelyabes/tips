export interface KPIInterface {
  test?: string | undefined;
  getTest(): string | undefined;
  setTest(test: string): void;
}
