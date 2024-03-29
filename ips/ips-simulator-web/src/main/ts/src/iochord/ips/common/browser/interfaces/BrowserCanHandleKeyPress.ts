/**
 * Listener interface to handle actions after certain key press event.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface BrowserCanHandleKeyPress {
  key: number | string;
  handleKeyPress(key: number | string): void;
}
