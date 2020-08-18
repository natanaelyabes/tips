import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';
import { VisualizerLayout } from '@/iochord/ips/common/ui/layout/interfaces/VisualizerLayout';
import { Component } from 'vue-property-decorator';

@Component

/**
 * Implementation of visualizer layout interface.
 *
 * @export
 * @class VisualizerLayoutView
 * @extends {PageLayout}
 * @implements {VisualizerLayout}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class VisualizerLayoutView extends PageLayout implements VisualizerLayout {

  /**
   * The title of visualizer layout view.
   *
   * @type {string}
   * @memberof VisualizerLayoutView
   */
  public title: string = '';

  /**
   * Breadcrumbs of visualizer layout view.
   *
   * @type {*}
   * @memberof VisualizerLayoutView
   */
  public breadcrumbs: any;

  /**
   * Title menu bar items.
   *
   * @type {*}
   * @memberof VisualizerLayoutView
   */
  public titleMenuBarItems: any;

  /**
   * Settings bar items.
   *
   * @type {*}
   * @memberof VisualizerLayoutView
   */
  public settingsBarItems: any;

  /**
   * Content of visualizer layout.
   *
   * @type {*}
   * @memberof VisualizerLayoutView
   */
  public content: any;

  /**
   * Vue mounted lifecyle.
   *
   * @override
   * @memberof VisualizerLayoutView
   */
  public mounted(): void {
    this.setWrapperProperties();
  }

  /**
   * Assigns title to current visualizer layout.
   *
   * @memberof VisualizerLayoutView
   */
  public setTitle(): void {
    // console.warn('Method setTitle not implemented.');
  }

  /**
   * Assigns breadcrumb to current visualizer layout.
   *
   * @memberof VisualizerLayoutView
   */
  public setBreadcrumb(): void {
    // console.warn('Method setBreadcrumb not implemented.');
  }

  /**
   * Assigns title menu bar of current visualizer layout.
   *
   * @memberof VisualizerLayoutView
   */
  public setTitleMenubar(): void {
    // console.warn('Method setTitleMenubar not implemented.');
  }

  /**
   * Assigns the settings bar of current visualizer layout.
   *
   * @memberof VisualizerLayoutView
   */
  public setSettingsBar(): void {
    // console.warn('Method setTitleMenubar not implemented.');
  }

  /**
   * Assigns content of current visualizer layout.
   *
   * @memberof VisualizerLayoutView
   */
  public setContent(): void {
    // console.warn('Method setContent not implemented.');
  }

  /**
   * Assigns the wrapper properties of current visualizer layout.
   *
   * @memberof VisualizerLayoutView
   */
  public setWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setContent();
  }
}
