import { Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphNode } from '@/iochord/ips/common/graph/ism/interfaces/GraphNode';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';
import { GraphNodeImpl } from '@/iochord/ips/common/graph/ism/class/GraphNodeImpl';
import { ACTIVITY_TYPE } from '@/iochord/ips/common/graph/ism/enums/ACTIVITY';
import { GraphActivityNode } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphActivityNode';
import { GraphActivityNodeImpl } from '@/iochord/ips/common/graph/ism/class/components/GraphActivityNodeImpl';
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';
import { GraphDataResource } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphDataResource';
import { DISTRIBUTION_TYPE } from '@/iochord/ips/common/graph/ism/enums/DISTRIBUTION';
import { TIME_UNIT } from '@/iochord/ips/common/graph/ism/enums/TIME_UNIT';
import { GraphDataQueue } from '@/iochord/ips/common/graph/ism/interfaces/components/GraphDataQueue';

const graphModule = getModule(GraphModule);

import { TSMap } from 'typescript-map';


@Component<ActivityNodeModalMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class ActivityNodeModalMixin extends BaseComponent {

  // Parent activity
  public parentActLabel: string = '';
  public parentActNodeSelectedActivityType: ACTIVITY_TYPE = ACTIVITY_TYPE.STANDARD;
  public parentActNodeResource: string = '';
  public parentActNodeReport: boolean = false;
  public parentActNodeCustomMonitor: string = '';
  public parentActNodeProcessingTime: DISTRIBUTION_TYPE = DISTRIBUTION_TYPE.CONSTANT;
  public parentActNodeProcessingTimeParameter: string = '';
  public parentActNodeSetupTime: DISTRIBUTION_TYPE = DISTRIBUTION_TYPE.CONSTANT;
  public parentActNodeSetupTimeParameter: string = '';
  public parentActNodeUnit: TIME_UNIT = TIME_UNIT.MINUTES;
  public parentActNodeQueueLabel: string = '';
  public parentActNodeInputType: string = '';
  public parentActNodeOutputType: string = '';
  public parentActNodeCodeSegment: string = '';

  /*
    Activity Node functions
    - Send changes from parent to child
    - Retrieve changes from child to parent
  */
  public changeActLabel(newVal: string): void {
    this.parentActLabel = newVal;
  }

  public changeActNodeSelectedActivityType(newVal: ACTIVITY_TYPE): void {
    this.parentActNodeSelectedActivityType = newVal;
  }

  public changeActNodeResource(newVal: string): void {
    this.parentActNodeResource = newVal;
  }

  public changeActNodeReport(newVal: boolean): void {
    this.parentActNodeReport = newVal;
  }

  public changeActNodeCustomMonitor(newVal: string): void {
    this.parentActNodeCustomMonitor = newVal;
  }

  public changeActNodeProcessingTime(newVal: DISTRIBUTION_TYPE): void {
    this.parentActNodeProcessingTime = newVal;
  }

  public changeActNodeProcessingTimeParameter(newVal: string): void {
    this.parentActNodeProcessingTimeParameter = newVal;
  }

  public changeActNodeSetupTime(newVal: DISTRIBUTION_TYPE): void {
    this.parentActNodeSetupTime = newVal;
  }

  public changeActNodeSetupTimeParameter(newVal: string): void {
    this.parentActNodeSetupTimeParameter = newVal;
  }

  public changeActNodeChangeUnit(newVal: TIME_UNIT): void {
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
  public changeActLabelFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActLabel = e;

    // Change label of currentSelectedElement
    currentSelectedElement.setLabel(this.parentActLabel);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: currentSelectedElement });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(currentSelectedElement.getId() as string, GraphActivityNodeImpl.deserialize(currentSelectedElement) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeSelectedActivityTypeFromChild(e: ACTIVITY_TYPE, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeSelectedActivityType = e;

    // Set activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setActivityType(this.parentActNodeSelectedActivityType);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeResourceFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeResource = e;

    // Get data based on selection value e
    const data = (activePage.getData() as TSMap<string, GraphData>).get(this.parentActNodeResource);

    // Set resource for the activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setResource(data as GraphDataResource);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeReportFromChild(e: boolean, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeReport = e;

    // Change report statistics status of currentSelectedElement
    currentSelectedElement.setReportStatistics(this.parentActNodeReport);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: currentSelectedElement });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(currentSelectedElement.getId() as string, GraphActivityNodeImpl.deserialize(currentSelectedElement) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeCustomMonitorFromChild(e: any) {
    this.parentActNodeCustomMonitor = e;
  }

  public changeActNodeProcessingTimeFromChild(e: DISTRIBUTION_TYPE, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeProcessingTime = e;

    // Set activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setProcessingTime(this.parentActNodeProcessingTime);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeProcessingTimeParameterFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeProcessingTimeParameter = e;

    // Set activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setProcessingTimeParameter(this.parentActNodeProcessingTimeParameter);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeSetupTimeFromChild(e: DISTRIBUTION_TYPE, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeSetupTime = e;

    // Set activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setProcessingTime(this.parentActNodeProcessingTime);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeSetupTimeParameterFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeSetupTimeParameter = e;

    // Set activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setSetupTimeParameter(this.parentActNodeSetupTimeParameter);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeUnitFromChild(e: TIME_UNIT, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeUnit = e;

    // Set activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setUnit(this.parentActNodeUnit);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
  }

  public changeActNodeQueueLabelFromChild(e: string, activePage: GraphPage, currentSelectedElement: GraphNode, callback: () => void) {
    this.parentActNodeQueueLabel = e;

    // Get data based on selection value e
    const data = (activePage.getData() as TSMap<string, GraphData>).get(this.parentActNodeQueueLabel);

    // Set queue for the activity node
    const actNode = (currentSelectedElement as GraphActivityNode);
    actNode.setQueue(data as GraphDataQueue);

    // Directly override currentSelectedElement node
    graphModule.overridePageNode({ page: activePage, node: actNode });

    // Save it in GraphNodeImpl instance
    GraphNodeImpl.instance.set(actNode.getId() as string, GraphActivityNodeImpl.deserialize(actNode) as GraphNode);

    // Update the rxjs observable
    GraphSubject.update(graphModule.graph);

    // Call the desired callback code
    callback();
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
