/**
 *
 *
 * @export
 * @interface NavigationHasMenu
 */
export interface NavigationHasMenu {
  menuIsOpen: boolean;
  fn_navigation_toggle_menu(): void;
  fn_navigation_close_menu(): void;
  fn_navigation_open_menu(): void;
}
