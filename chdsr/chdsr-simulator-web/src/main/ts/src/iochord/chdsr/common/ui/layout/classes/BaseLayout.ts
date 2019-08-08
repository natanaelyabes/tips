import { Component, Vue } from 'vue-property-decorator';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic-components/SemanticModulesIsUsed';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class BaseLayout extends Vue
implements SemanticModulesIsUsed {
  public reRenderKey: number = 0;

  /** @Override */
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
    // console.warn('declareSemanticModules() not implemented.');
  }
}
