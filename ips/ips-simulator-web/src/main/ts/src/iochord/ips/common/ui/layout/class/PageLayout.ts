import BaseLayout from './BaseLayout';
import { BaseUrlEnum, ApplicationEnum } from '@/iochord/ips/common/enums';
import { BrowserHasProperties } from '@/iochord/ips/common/browser/interfaces/BrowserHasProperties';
import { Component } from 'vue-property-decorator';

@Component

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class PageLayout extends BaseLayout implements BrowserHasProperties {

  /**
   *
   * @override
   * @memberof PageLayout
   */
  public mounted(): void {
    this.overrideBrowserProperties();
  }

  /**
   * Method to override document properties e.g. document title
   *
   * @memberof PageLayout
   */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Untitled');
  }

  /**
   * Given a title, set the document title according to the template and given title.
   *
   * @param {string} title
   * @memberof PageLayout
   */
  public setDocumentTitle(title: string): void {
    document.title = `${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME.toUpperCase()} · ${title}`;
  }
}
