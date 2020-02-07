import { AppLayout } from '../interfaces/AppLayout';
import { Component } from 'vue-property-decorator';

import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';

@Component

/**
 * Wiki menu component.
 *
 * @export
 * @class WikiMenuComponent
 * @extends {BaseComponent}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class AppLayoutView extends PageLayout implements AppLayout {
  public title: string = '';
  public breadcrumbs: any;
  public titleMenuBarItems: any;
  public leftMenuBarItems: any;
  public rightMenuBarItems: any;
  public ribbonMenuItems: any;
  public content: any;

  /** @Override */
  public mounted(): void {
    this.setWrapperProperties();
  }

  public setTitle(): void {
    // console.warn('Method setTitle not implemented.');
  }

  public setBreadcrumb(): void {
    // console.warn('Method setBreadcrumb not implemented.');
  }

  public setTitleMenubar(): void {
    // console.warn('Method setTitleMenubar not implemented.');
  }

  public setLeftMenuSidebar(): void {
    // console.warn('Method setLeftMenuSidebar not implemented.');
  }

  public setRightMenuSidebar(): void {
    // console.warn('Method setRightMenuSidebar not implemented.');
  }

  public setRibbonMenuItem(): void {
    // console.warn('Method setRibbonMenuItem not implemented.');
  }

  public setContent(): void {
    // console.warn('Method setContent not implemented.');
  }

  public setWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setLeftMenuSidebar();
    this.setRightMenuSidebar();
    this.setRibbonMenuItem();
    this.setContent();
  }
}
