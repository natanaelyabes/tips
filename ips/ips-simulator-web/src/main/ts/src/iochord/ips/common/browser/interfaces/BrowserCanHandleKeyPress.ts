/**
 * Listener interface to handle actions after certain key press event.
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface BrowserCanHandleKeyPress {
  key: number | string;
  handleKeyPress(key: number | string): void;
}
