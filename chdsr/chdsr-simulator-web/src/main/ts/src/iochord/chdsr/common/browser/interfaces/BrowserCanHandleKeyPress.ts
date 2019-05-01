/**
 *
 *
 * @export
 * @interface BrowserCanHandleKeyPress
 */
export interface BrowserCanHandleKeyPress {
  key: number | string;
  fn_browser_handle_key_press(key: number | string): void;
}
