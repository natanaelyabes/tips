/**
 * App layout interface.
 *
 * @export
 * @interface AppLayout
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface AppLayout {

  /**
   * Title of the layout
   *
   * @type {string}
   * @memberof AppLayout
   */
  title: string;

  /**
   * Breadcrumbs of the layout
   *
   * @type {*}
   * @memberof AppLayout
   */
  breadcrumbs: any;

  /**
   * The title menu bar items.
   *
   * @type {*}
   * @memberof AppLayout
   */
  titleMenuBarItems: any;

  /**
   * Left menu bar items.
   *
   * @type {*}
   * @memberof AppLayout
   */
  leftMenuBarItems: any;

  /**
   * Right menu bar items.
   *
   * @type {*}
   * @memberof AppLayout
   */
  rightMenuBarItems: any;

  /**
   * Ribbon menu items.
   *
   * @type {*}
   * @memberof AppLayout
   */
  ribbonMenuItems: any;

  /**
   * The content of the app layout.
   *
   * @type {*}
   * @memberof AppLayout
   */
  content: any;

  /**
   * Assigns title to the layout.
   *
   * @memberof AppLayout
   */
  setTitle(): void;

  /**
   * Assigns breadcrumbs to the layout.
   *
   * @memberof AppLayout
   */
  setBreadcrumb(): void;

  /**
   * Assigns the menu bar title.
   *
   * @memberof AppLayout
   */
  setTitleMenubar(): void;

  /**
   * Assign left menu sidebar to the layout.
   *
   * @memberof AppLayout
   */
  setLeftMenuSidebar(): void;

  /**
   * Assigns left menu sidebar to the layout.
   *
   * @memberof AppLayout
   */
  setRightMenuSidebar(): void;

  /**
   * Assigns ribbon menu item.
   *
   * @memberof AppLayout
   */
  setRibbonMenuItem(): void;

  /**
   * Assigns the content of the layout.
   *
   * @memberof AppLayout
   */
  setContent(): void;

  /**
   * Assigns the wrapper properties of the layout.
   *
   * @memberof AppLayout
   */
  setWrapperProperties(): void;
}
