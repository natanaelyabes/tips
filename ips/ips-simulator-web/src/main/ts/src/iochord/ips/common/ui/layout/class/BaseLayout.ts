import { Component, Vue } from 'vue-property-decorator';
import { SemanticModulesIsUsed } from '@/iochord/ips/common/ui/semantic-components/SemanticModulesIsUsed';

@Component

/**
 * Implementation class to define the layout of Vue views or pages.
 *
 * @export
 * @class BaseLayout
 * @extends {Vue}
 * @implements {SemanticModulesIsUsed}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class BaseLayout extends Vue implements SemanticModulesIsUsed {

  /**
   * Re-render trials
   *
   * @type {number}
   * @memberof BaseLayout
   */
  public reRenderKey: number = 0;

  /**
   * Override Vue mounted lifecyle
   *
   * @override
   * @memberof BaseLayout
   */
  public mounted(): void {
    this.declareSemanticModules();
  }

  /**
   * Use this method to force re-render component
   *
   * @memberof BaseLayout
   */
  public forceReRender(): void {
    this.reRenderKey += 1;
  }

  /**
   * Method to declare Semantic UI jQuery Module
   *
   * @memberof BaseLayout
   */
  public declareSemanticModules(): void {
    //
  }
}
