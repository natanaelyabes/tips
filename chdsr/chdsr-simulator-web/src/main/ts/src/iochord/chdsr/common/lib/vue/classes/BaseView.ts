import { Component, Vue } from 'vue-property-decorator';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic/SemanticModulesIsUsed';


/**
 *
 *
 * @export
 * @class BaseView
 * @extends {Vue}
 * @implements {SemanticModulesIsUsed}
 */
@Component
export default class BaseView extends Vue
implements SemanticModulesIsUsed {
  public reRenderKey: number = 0;

  /** @Override */
  public mounted(): void {
    this.declareSemanticModules();
  }

  /**
   * Use this method to force re-render component
   *
   * @memberof BaseView
   */
  public forceReRender(): void {
    this.reRenderKey += 1;
  }

  /**
   * Method to declare Semantic UI jQuery Module
   *
   * @memberof BaseView
   */
  public declareSemanticModules(): void {
    console.warn('declareSemanticModules() not implemented.');
  }
}
