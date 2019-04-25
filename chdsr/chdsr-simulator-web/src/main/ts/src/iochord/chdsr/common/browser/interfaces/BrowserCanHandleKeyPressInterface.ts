/**
 *
 *
 * @export
 * @interface BrowserCanHandleKeyPressInterface
 */
export interface BrowserCanHandleKeyPressInterface {
  key: number | string;
  fn_browser_handle_key_press(key: number | string): void;
}
