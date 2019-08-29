import PageLayout from '@/iochord/chdsr/common/ui/layout/classes/PageLayout';
import { Layout04HasWrapper } from '@/iochord/chdsr/common/ui/layout/interfaces/Layout04HasWrapper';
import { Component } from 'vue-property-decorator';

@Component
export default class Layout04View extends PageLayout implements Layout04HasWrapper {
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
