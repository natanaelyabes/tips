import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';

@Component
export default class ActivityNodeModalMixin extends BaseComponent {

  // Parent activity
  public parentActNodeSelectedActivityType: string = '';
  public parentActNodeReport: boolean = false;
  public parentActNodeCustomMonitor: string = '';
  public parentActNodeProcessingTime: string = '';
  public parentActNodeParameter1: string = '';
  public parentActNodeSetupTime: string = '';
  public parentActNodeParameter2: string = '';
  public parentActNodeUnit: string = '';
  public parentActNodeQueueLabel: string = '';
  public parentActNodeInputType: string = '';
  public parentActNodeOutputType: string = '';
  public parentActNodeCodeSegment: string = '';

  /*
    Activity Node functions
    - Send changes from parent to child
    - Retrieve changes from child to parent
  */
  public changeActNodeSelectedActivityType(newVal: string): void {
    this.parentActNodeSelectedActivityType = newVal;
  }

  public changeActNodeReport(newVal: boolean): void {
    this.parentActNodeReport = newVal;
  }

  public changeActNodeCustomMonitor(newVal: string): void {
    this.parentActNodeCustomMonitor = newVal;
  }

  public changeActNodeProcessingTime(newVal: string): void {
    this.parentActNodeSelectedActivityType = newVal;
  }

  public changeActNodeParameter1(newVal: string): void {
    this.parentActNodeParameter1 = newVal;
  }

  public changeActNodeSetupTime(newVal: string): void {
    this.parentActNodeSetupTime = newVal;
  }

  public changeActNodeParameter2(newVal: string): void {
    this.parentActNodeParameter2 = newVal;
  }

  public changeActNodeChangeUnit(newVal: string): void {
    this.parentActNodeUnit = newVal;
  }

  public changeActNodeQueueLabel(newVal: string): void {
    this.parentActNodeQueueLabel = newVal;
  }

  public changeActNodeInputType(newVal: string): void {
    this.parentActNodeInputType = newVal;
  }

  public changeActNodeOutputType(newVal: string): void {
    this.parentActNodeOutputType = newVal;
  }

  public changeActNodeCodeSegment(newVal: string): void {
    this.parentActNodeCodeSegment = newVal;
  }

  /* From Child */
  public changeActNodeSelectedActivityTypeFromChild(e: any) {
    this.parentActNodeSelectedActivityType = e;
  }

  public changeActNodeReportFromChild(e: any) {
    this.parentActNodeReport = e;
  }

  public changeActNodeCustomMonitorFromChild(e: any) {
    this.parentActNodeCustomMonitor = e;
  }

  public changeActNodeProcessingTimeFromChild(e: any) {
    this.parentActNodeProcessingTime = e;
  }

  public changeActNodeParameter1FromChild(e: any) {
    this.parentActNodeParameter1 = e;
  }

  public changeActNodeSetupTimeFromChild(e: any) {
    this.parentActNodeSetupTime = e;
  }

  public changeActNodeParameter2FromChild(e: any) {
    this.parentActNodeParameter2 = e;
  }

  public changeActNodeUnitFromChild(e: any) {
    this.parentActNodeUnit = e;
  }

  public changeActNodeQueueLabelFromChild(e: any) {
    this.parentActNodeQueueLabel = e;
  }

  public changeActNodeInputTypeFromChild(e: any) {
    this.parentActNodeInputType = e;
  }
  public changeActNodeOutputTypeFromChild(e: any) {
    this.parentActNodeOutputType = e;
  }
  public changeActNodeCodeSegmentFromChild(e: any) {
    this.parentActNodeCodeSegment = e;
  }
}
