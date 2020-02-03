import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { Component } from 'vue-property-decorator';
import { SemanticModulesIsUsed } from '@/iochord/ips/common/ui/semantic-components/SemanticModulesIsUsed';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class SemanticComponent extends BaseComponent
implements SemanticModulesIsUsed {
  public reRenderKey: number = 0;


  public mounted(): void {
    this.declareSemanticModules();
  }

  public declareSemanticModules(): void {
    // console.warn('declareSemanticModules() not implemented.');
  }

  public forceReRender(): void {
    this.reRenderKey += 1;
  }
}
