import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { Component } from 'vue-property-decorator';

@Component
export default class BranchNodeModalMixins extends BaseComponent {
  // Parent branch
  public parentBranchLabel: string = '';
  public parentBranchSelectedGate: string = '';
  public parentBranchSelectedType: string = '';
  public parentBranchSelectedRule: string = '';

  /*
    Branch Node functions
    - Send changes from parent to child
    - Retrieve changes from child to parent
  */
  public changeBranchLabel(newVal: string): void {
    this.parentBranchLabel = newVal;
  }

  public changeBranchSelectedGate(newVal: string): void {
    this.parentBranchSelectedGate = newVal;
  }

  public changeBranchSelectedType(newVal: string): void {
    this.parentBranchSelectedType = newVal;
  }

  public changeBranchSelectedRule(newVal: string): void {
    this.parentBranchSelectedRule = newVal;
  }

  /* Branch updated from Child */
  public changeBranchLabelFromChild(e: any) {
    this.parentBranchLabel = e;
  }

  public changeBranchSelectedGateFromChild(e: any) {
    this.parentBranchSelectedGate = e;
  }

  public changeBranchSelectedTypeFromChild(e: any) {
    this.parentBranchSelectedType = e;
  }

  public changeBranchSelectedRuleFromChild(e: any) {
    this.parentBranchSelectedRule = e;
  }
}
