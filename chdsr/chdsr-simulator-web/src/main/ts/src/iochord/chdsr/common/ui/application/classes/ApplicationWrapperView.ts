import { ApplicationHasWrapper } from './../interfaces/ApplicationHasWrapper';
import { Component } from 'vue-property-decorator';

import { Breadcrumb } from '../../semantic/breadcrumbs/interfaces/Breadcrumb';
import PageView from '@/iochord/chdsr/common/lib/vue/classes/PageView';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class ApplicationWrapperView extends PageView
implements ApplicationHasWrapper {
  public title: string = '';
  public breadcrumbs!: Breadcrumb[];
  public titleMenuBarItems: any;
  public leftMenuBarItems: any;
  public rightMenuBarItems: any;
  public ribbonMenuItems: any;
  public content: any;

  /** @Override */
  public mounted(): void {
    this.setApplicationWrapperProperties();
  }

  public setTitle(): void {
    console.warn('Method setTitle not implemented.');
  }

  public setBreadcrumb(): void {
    console.warn('Method setBreadcrumb not implemented.');
  }

  public setTitleMenubar(): void {
    console.warn('Method setTitleMenubar not implemented.');
  }

  public setLeftMenuSidebar(): void {
    console.warn('Method setLeftMenuSidebar not implemented.');
  }

  public setRightMenuSidebar(): void {
    console.warn('Method setRightMenuSidebar not implemented.');
  }

  public setRibbonMenuItem(): void {
    console.warn('Method setRibbonMenuItem not implemented.');
  }

  public setContent(): void {
    console.warn('Method setContent not implemented.');
  }

  public setApplicationWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setLeftMenuSidebar();
    this.setRightMenuSidebar();
    this.setRibbonMenuItem();
    this.setContent();
  }
}
