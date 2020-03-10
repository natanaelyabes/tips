import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { Component } from 'vue-property-decorator';
import { SemanticModulesIsUsed } from '@/iochord/ips/common/ui/semantic-components/SemanticModulesIsUsed';

@Component

/**
 * Navigation bar component.
 *
 * @export
 * @class NavigationBarComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class SemanticComponent extends BaseComponent implements SemanticModulesIsUsed {

  /**
   * Re-render trials.
   *
   * @type {number}
   * @memberof SemanticComponent
   */
  public reRenderKey: number = 0;

  /**
   * Vue mounted lifecyle.
   *
   * @override
   * @memberof SemanticComponent
   */
  public mounted(): void {
    this.declareSemanticModules();
  }

  /**
   * Declare semantic modules.
   *
   * @override
   * @memberof SemanticComponent
   */
  public declareSemanticModules(): void {
    // console.warn('declareSemanticModules() not implemented.');
  }

  /**
   * Force re-render component.
   *
   * @memberof SemanticComponent
   */
  public forceReRender(): void {
    this.reRenderKey += 1;
  }
}
