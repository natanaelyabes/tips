/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface BrowserCanHandleBreakpoints {
  width: number;
  handleBreakpoints(width: number): void;
}