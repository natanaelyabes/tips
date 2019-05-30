/**
 *
 *
 * @export
 * @interface BrowserCanHandleBreakpoints
 */
export interface BrowserCanHandleBreakpoints {
  width: number;
  handleBreakpoints(width: number): void;
}
