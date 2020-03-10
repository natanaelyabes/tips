import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';
import { ExplorerLayout } from '@/iochord/ips/common/ui/layout/interfaces/ExplorerLayout';
import { Component } from 'vue-property-decorator';

@Component

/**
 * Implementations of ExplorerLayoutView interface
 *
 * @export
 * @class ExplorerLayoutView
 * @extends {PageLayout}
 * @implements {ExplorerLayout}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class ExplorerLayoutView extends PageLayout implements ExplorerLayout {

  /**
   * The title of the layout.
   *
   * @type {string}
   * @memberof ExplorerLayoutView
   */
  public title: string = '';

  /**
   * Breadcrumb items.
   *
   * @type {*}
   * @memberof ExplorerLayoutView
   */
  public breadcrumbs: any;

  /**
   * Title menu bar.
   *
   * @type {*}
   * @memberof ExplorerLayoutView
   */
  public titleMenuBarItems: any;

  /**
   * Left menu bar items.
   *
   * @type {*}
   * @memberof ExplorerLayoutView
   */
  public leftMenuBarItems: any;

  /**
   * Content of the layout.
   *
   * @type {*}
   * @memberof ExplorerLayoutView
   */
  public content: any;

  /**
   * Menu items for depth two left menu bar.
   *
   * @type {*}
   * @memberof ExplorerLayoutView
   */
  public depthTwoLeftMenuBarItems: any;

  /**
   * Vue mounted lifecyle.
   *
   * @memberof ExplorerLayoutView
   */
  public mounted(): void {
    this.setWrapperProperties();
  }

  /**
   * Assigns a title to the layout.
   *
   * @memberof DiffLayoutView
   */
  public setTitle(): void {
    // console.warn('Method setTitle not implemented.');
  }

  /**
   * Assigns breadcrumb to its slot.
   *
   * @memberof DiffLayoutView
   */
  public setBreadcrumb(): void {
    // console.warn('Method setBreadcrumb not implemented.');
  }

  /**
   * Assigns the title menu bar within current layout.
   *
   * @memberof DiffLayoutView
   */
  public setTitleMenubar(): void {
    // console.warn('Method setTitleMenubar not implemented.');
  }

  /**
   * Assigns left menu side bar within current layout.
   *
   * @memberof DiffLayoutView
   */
  public setLeftMenuSidebar(): void {
    // console.warn('Method setLeftMenuSidebar not implemented.');
  }

  /**
   * Assigns content of the layout.
   *
   * @memberof DiffLayoutView
   */
  public setContent(): void {
    // console.warn('Method setContent not implemented.');
  }

  /**
   * Assigns depth two left menu side bar.
   *
   * @memberof ExplorerLayoutView
   */
  public setDepthTwoLeftMenuSidebar(): void {
    // console.warn('Method setDepthTwoMenuSidebar not implemented.');
  }

  /**
   * Assigns wrapper properties of the layout.
   *
   * @memberof DiffLayoutView
   */
  public setWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setLeftMenuSidebar();
    this.setDepthTwoLeftMenuSidebar();
    this.setContent();
  }
}
