import { Component, Mixins } from 'vue-property-decorator';
import ActivityNodeModalMixin from './ActivityNodeModalMixin';
import StartNodeModalMixin from './StartNodeModalMixin';
import StopNodeModalMixin from './StopNodeModalMixin';
import BranchNodeModalMixin from './BranchNodeModalMixin';
import FunctionDataModalMixin from './FunctionDataModalMixin';
import GeneratorDataModalMixin from './GeneratorDataModalMixin';
import ObjectTypeDataModalMixin from './ObjectTypeDataModalMixin';
// import QueueDataModalMixin from './QueueDataModalMixin';
// import ResourceDataModalMixin from './ResourceDataModalMixin';

@Component
export default class ModalMixin extends Mixins(
  ActivityNodeModalMixin,
  BranchNodeModalMixin,
  StartNodeModalMixin,
  StopNodeModalMixin,
  FunctionDataModalMixin,
  // GeneratorDataModalMixin,
  // ObjectTypeDataModalMixin,
  // QueueDataModalMixin,
  // ResourceDataModalMixin,
) {

}
