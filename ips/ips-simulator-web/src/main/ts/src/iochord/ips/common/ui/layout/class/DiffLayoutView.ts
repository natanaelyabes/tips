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
  public title: string = '';
  public breadcrumbs: any;
  public titleMenuBarItems: any;
  public leftMenuBarItems: any;
  public leftContent: any;
  public rightContent: any;

  /** @Override */
  public mounted(): void {
    this.setWrapperProperties();
  }

  public setTitle(): void {
    console.warn('Method setTitle not implemented.');
  }

  public setBreadcrumb(): void {
    console.warn('Method setBreadcrumb not implemented.');
  }

  public setTitleMenuBar(): void {
    console.warn('Method setTitleMenubar not implemented.');
  }

  public setLeftMenuSideBar(): void {
    console.warn('Method setLeftMenuSidebar not implemented.');
  }

  public setLeftContent(): void {
    console.warn('Method setContent not implemented.');
  }

  public setRightContent(): void {
    console.warn('Method setContent not implemented.');
  }

  public setWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenuBar();
    this.setLeftMenuSideBar();
    this.setRightContent();
    this.setLeftContent();
  }
}
