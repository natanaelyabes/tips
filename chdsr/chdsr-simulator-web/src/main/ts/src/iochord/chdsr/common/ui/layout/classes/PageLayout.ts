import BaseLayout from './BaseLayout';
import { BaseUrlEnum, ApplicationEnum } from '@/iochord/chdsr/common/enums';
import { BrowserHasProperties } from '@/iochord/chdsr/common/browser/interfaces/BrowserHasProperties';
import { Component } from 'vue-property-decorator';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class PageLayout extends BaseLayout
implements BrowserHasProperties {
  /** @Override */
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
