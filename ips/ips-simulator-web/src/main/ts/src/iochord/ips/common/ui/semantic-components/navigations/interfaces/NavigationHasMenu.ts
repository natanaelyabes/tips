/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface NavigationHasMenu {
  menuIsOpen: boolean;
  toggleMenu(): void;
  closeMenu(): void;
  openMenu(): void;
}
