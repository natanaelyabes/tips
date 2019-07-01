import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';
import { Component } from 'vue-property-decorator';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic/SemanticModulesIsUsed';

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
