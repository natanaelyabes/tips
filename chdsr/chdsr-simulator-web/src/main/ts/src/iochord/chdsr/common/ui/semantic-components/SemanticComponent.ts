import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import { Component } from 'vue-property-decorator';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic-components/SemanticModulesIsUsed';


/**
 *
 * @package chdsr
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
    console.warn('declareSemanticModules() not implemented.');
  }
}
