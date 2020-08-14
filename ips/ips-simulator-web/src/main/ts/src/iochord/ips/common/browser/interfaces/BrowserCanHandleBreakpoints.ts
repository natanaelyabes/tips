/**
 * Interface to define actions when
 * browser width or height met certain breakpoint.
 *
 * @export
 * @interface BrowserCanHandleBreakpoints
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface BrowserCanHandleBreakpoints {
  width: number;
  handleBreakpoints(width: number): void;
}
