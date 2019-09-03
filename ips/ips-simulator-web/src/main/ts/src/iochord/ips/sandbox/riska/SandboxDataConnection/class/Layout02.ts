import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';
import { Layout02HasWrapper } from '../interfaces/Layout02HasWrapper';
import { Component } from 'vue-property-decorator';


@Component
export default class Layout02View extends PageLayout implements Layout02HasWrapper {
  public title: string = '';
  public breadcrumbs: any;
  public titleMenuBarItems: any;
  public leftMenuBarItems: any;
  public depthTwoLeftMenuBarItems: any;
  public content: any;


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

  public setTitleMenubar(): void {
    console.warn('Method setTitleMenubar not implemented.');
  }

  public setLeftMenuSidebar(): void {
    console.warn('Method setLeftMenuSidebar not implemented.');
  }

  public setDepthTwoLeftMenuSidebar(): void {
    console.warn('Method setDepthTwoMenuSidebar not implemented.');
  }

  public setContent(): void {
    console.warn('Method setContent not implemented.');
  }

  public setWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setLeftMenuSidebar();
    this.setDepthTwoLeftMenuSidebar();
    this.setContent();
  }
}
