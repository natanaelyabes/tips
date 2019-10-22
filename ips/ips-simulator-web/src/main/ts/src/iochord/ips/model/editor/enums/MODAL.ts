// Node Modals
import ActivityNodeModal from '../components/modals/ActivityNodeModal.vue';
import BranchNodeModal from '../components/modals/BranchNodeModal.vue';
import StartNodeModal from '../components/modals/StartNodeModal.vue';
import StopNodeModal from '../components/modals/StopNodeModal.vue';

// Data Modals
import ObjectTypeDataModal from '../components/modals/ObjectTypeDataModal.vue';
import QueueDataModal from '../components/modals/QueueDataModal.vue';
import ResourceDataModal from '../components/modals/ResourceDataModal.vue';
import GeneratorDataModal from '../components/modals/GeneratorDataModal.vue';
import FunctionDataModal from '../components/modals/FunctionDataModal.vue';

export const MODAL_TYPE = {
  activity: ActivityNodeModal,
  branch: BranchNodeModal,
  start: StartNodeModal,
  stop: StopNodeModal,
  objecttype: ObjectTypeDataModal,
  queue: QueueDataModal,
  resource: ResourceDataModal,
  generator: GeneratorDataModal,
  function: FunctionDataModal,
};
