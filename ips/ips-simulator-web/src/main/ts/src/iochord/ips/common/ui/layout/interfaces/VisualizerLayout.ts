/**
 * Visualizer layout interface.
 *
 * @export
 * @interface VisualizerLayout
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export interface VisualizerLayout {

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
   * The settings bar items.
   *
   * @type {*}
   * @memberof VisualizerLayout
   */
  settingsBarItems: any;

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
   * Assigns the settings bar
   *
   * @memberof VisualizerLayout
   */
  setSettingsBar(): void;

  /**
   * Assigns the content of the layout.
   *
   * @memberof VisualizerLayout
   */
  setContent(): void;

  /**
   * Assigns the wrapper properties of the layout.
   *
   * @memberof AppLayout
   */
  setWrapperProperties(): void;
}
