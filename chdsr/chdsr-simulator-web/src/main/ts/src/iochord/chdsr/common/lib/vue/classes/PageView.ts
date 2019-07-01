import BaseView from './BaseView';
import { BaseUrlEnum, ApplicationEnum } from '@/iochord/chdsr/common/enums';
import { BrowserHasProperties } from '@/iochord/chdsr/common/browser/interfaces/BrowserHasProperties';
import { Component } from 'vue-property-decorator';


/**
 *
 *
 * @export
 * @class PageView
 * @extends {BaseView}
 * @implements {BrowserHasProperties}
 */
@Component
export default class PageView extends BaseView
implements BrowserHasProperties {
  /** @Override */
  public mounted(): void {
    this.overrideBrowserProperties();
  }

  /**
   * Method to override document properties e.g. document title
   *
   * @memberof PageView
   */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Untitled');
  }

  /**
   * Given a title, set the document title according to the template and given title.
   *
   * @param {string} title
   * @memberof PageView
   */
  public setDocumentTitle(title: string): void {
    document.title = `${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME.toUpperCase()} · ${title}`;
  }
}
