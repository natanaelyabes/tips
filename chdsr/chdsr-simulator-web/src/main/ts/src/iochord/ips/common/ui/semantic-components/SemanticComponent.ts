import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';
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
export default class SemanticComponent extends BaseComponent
implements SemanticModulesIsUsed {

  public mounted(): void {
    this.declareSemanticModules();
  }

  public declareSemanticModules(): void {
    // console.warn('declareSemanticModules() not implemented.');
  }
}
