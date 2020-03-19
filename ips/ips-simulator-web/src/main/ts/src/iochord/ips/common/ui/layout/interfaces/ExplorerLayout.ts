/**
 * Explorer layout interface.
 *
 * @export
 * @interface ExplorerLayout
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export interface ExplorerLayout {

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
   * The content of the app layout.
   *
   * @type {*}
   * @memberof AppLayout
   */
  content: any;

  /**
   * The depth two left menu bar items.
   *
   * @type {*}
   * @memberof ExplorerLayout
   */
  depthTwoLeftMenuBarItems: any;

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

  /**
   * Assigns the depth two left menu bar items.
   *
   * @memberof ExplorerLayout
   */
  setDepthTwoLeftMenuSidebar(): void;
}
