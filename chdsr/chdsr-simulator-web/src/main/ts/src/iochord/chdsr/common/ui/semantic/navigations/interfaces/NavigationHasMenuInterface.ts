/**
 *
 *
 * @export
 * @interface NavigationHasMenuInterface
 */
export interface NavigationHasMenuInterface {
  menuIsOpen: boolean;
  fn_navigation_toggle_menu(): void;
  fn_navigation_close_menu(): void;
  fn_navigation_open_menu(): void;
}
