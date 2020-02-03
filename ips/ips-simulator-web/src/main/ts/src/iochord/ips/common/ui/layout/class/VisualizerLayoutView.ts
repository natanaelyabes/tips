import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';
import { VisualizerLayout } from '@/iochord/ips/common/ui/layout/interfaces/VisualizerLayout';
import { Component } from 'vue-property-decorator';


@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class VisualizerLayoutView extends PageLayout implements VisualizerLayout {
  public title: string = '';
  public breadcrumbs: any;
  public titleMenuBarItems: any;
  public settingsBarItems: any;
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
  public setSettingsBar(): void {
    console.warn('Method setTitleMenubar not implemented.');
  }
  public setContent(): void {
    console.warn('Method setContent not implemented.');
  }

  public setWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setContent();
  }
}
