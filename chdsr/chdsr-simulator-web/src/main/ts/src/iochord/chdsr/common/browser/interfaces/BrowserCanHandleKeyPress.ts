/**
 *
 *
 * @export
 * @interface BrowserCanHandleKeyPress
 */
export interface BrowserCanHandleKeyPress {
  key: number | string;
  handleKeyPress(key: number | string): void;
}
