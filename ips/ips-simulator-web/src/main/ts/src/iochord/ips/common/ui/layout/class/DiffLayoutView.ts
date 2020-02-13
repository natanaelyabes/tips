import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';
import { DiffLayout } from '@/iochord/ips/common/ui/layout/interfaces/DiffLayout';
import { Component } from 'vue-property-decorator';

@Component

/**
 * Implementations of DiffLayout interface
 *
 * @export
 * @class DiffLayoutView
 * @extends {PageLayout}
 * @implements {DiffLayout}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class DiffLayoutView extends PageLayout implements DiffLayout {

  /**
   * The title of the layout.
   *
   * @type {string}
   * @memberof DiffLayoutView
   */
  public title: string = '';

  /**
   * Breadcrumb items.
   *
   * @type {*}
   * @memberof DiffLayoutView
   */
  public breadcrumbs: any;

  /**
   * Title menu bar.
   *
   * @type {*}
   * @memberof DiffLayoutView
   */
  public titleMenuBarItems: any;

  /**
   * Left menu bar items.
   *
   * @type {*}
   * @memberof DiffLayoutView
   */
  public leftMenuBarItems: any;

  /**
   * Left content of the layout.
   *
   * @type {*}
   * @memberof DiffLayoutView
   */
  public leftContent: any;

  /**
   * Right content of the layout.
   *
   * @type {*}
   * @memberof DiffLayoutView
   */
  public rightContent: any;

  /**
   * Mounted Vue lifecycle.
   *
   * @memberof DiffLayoutView
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
    console.warn('Method setTitle not implemented.');
  }

  /**
   * Assigns breadcrumb to its slot.
   *
   * @memberof DiffLayoutView
   */
  public setBreadcrumb(): void {
    console.warn('Method setBreadcrumb not implemented.');
  }

  /**
   * Assigns the title menu bar within current layout.
   *
   * @memberof DiffLayoutView
   */
  public setTitleMenuBar(): void {
    console.warn('Method setTitleMenubar not implemented.');
  }

  /**
   * Assigns left menu side bar within current layout.
   *
   * @memberof DiffLayoutView
   */
  public setLeftMenuSideBar(): void {
    console.warn('Method setLeftMenuSidebar not implemented.');
  }

  /**
   * Assigns left content of the layout.
   *
   * @memberof DiffLayoutView
   */
  public setLeftContent(): void {
    console.warn('Method setContent not implemented.');
  }

  /**
   * Assigns right content of the layout.
   *
   * @memberof DiffLayoutView
   */
  public setRightContent(): void {
    console.warn('Method setContent not implemented.');
  }

  /**
   * Assigns wrapper properties of the layout.
   *
   * @memberof DiffLayoutView
   */
  public setWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenuBar();
    this.setLeftMenuSideBar();
    this.setRightContent();
    this.setLeftContent();
  }
}
