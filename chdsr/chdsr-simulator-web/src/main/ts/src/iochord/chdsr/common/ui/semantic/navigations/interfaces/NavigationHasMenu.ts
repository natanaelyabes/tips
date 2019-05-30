/**
 *
 *
 * @export
 * @interface NavigationHasMenu
 */
export interface NavigationHasMenu {
  menuIsOpen: boolean;
  toggleMenu(): void;
  closeMenu(): void;
  openMenu(): void;
}
