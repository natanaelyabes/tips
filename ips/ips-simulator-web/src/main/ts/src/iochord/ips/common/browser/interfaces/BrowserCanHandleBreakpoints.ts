/**
 * Interface to define actions when
 * browser width or height met certain breakpoint.
 *
 * @export
 * @interface BrowserCanHandleBreakpoints
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface BrowserCanHandleBreakpoints {
  width: number;
  handleBreakpoints(width: number): void;
}
