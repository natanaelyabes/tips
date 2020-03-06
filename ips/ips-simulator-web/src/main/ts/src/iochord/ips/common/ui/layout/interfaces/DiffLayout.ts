/**
 * Diff layout interface.
 *
 * @export
 * @interface DiffLayout
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface DiffLayout {

  /**
   * The title of the layout
   *
   * @type {string}
   * @memberof DiffLayout
   */
  title: string;

  /**
   * The breadcrumbs of the layout.
   *
   * @type {*}
   * @memberof DiffLayout
   */
  breadcrumbs: any;

  /**
   * The title menu bar items.
   *
   * @type {*}
   * @memberof DiffLayout
   */
  titleMenuBarItems: any;

  /**
   * The left menu bar items.
   *
   * @type {*}
   * @memberof DiffLayout
   */
  leftMenuBarItems: any;

  /**
   * The left content of the layout.
   *
   * @type {*}
   * @memberof DiffLayout
   */
  leftContent: any;

  /**
   * The right content of the layout.
   *
   * @type {*}
   * @memberof DiffLayout
   */
  rightContent: any;

  /**
   * Assigns title of the layout.
   *
   * @memberof DiffLayout
   */
  setTitle(): void;

  /**
   * Assigns breadcrumbs to the layout.
   *
   * @memberof DiffLayout
   */
  setBreadcrumb(): void;

  /**
   * Assigns title menu bar of the layout.
   *
   * @returns {*}
   * @memberof DiffLayout
   */
  setTitleMenuBar(): any;

  /**
   * Assigns left menu sidebar to the layout.
   *
   * @returns {*}
   * @memberof DiffLayout
   */
  setLeftMenuSideBar(): any;

  /**
   * Assigns left content of the layout.
   *
   * @returns {*}
   * @memberof DiffLayout
   */
  setLeftContent(): any;

  /**
   * Assigns right content of the layout.
   *
   * @returns {*}
   * @memberof DiffLayout
   */
  setRightContent(): any;

  /**
   * Assigns wrapper properties to the layout.
   *
   * @returns {*}
   * @memberof DiffLayout
   */
  setWrapperProperties(): any;
}
