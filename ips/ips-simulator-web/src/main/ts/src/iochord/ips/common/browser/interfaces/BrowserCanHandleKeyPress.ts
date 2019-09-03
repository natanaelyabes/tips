/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface BrowserCanHandleKeyPress {
  key: number | string;
  handleKeyPress(key: number | string): void;
}
