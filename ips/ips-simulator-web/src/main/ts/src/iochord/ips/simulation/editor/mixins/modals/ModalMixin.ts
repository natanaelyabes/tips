import { Component, Mixins } from 'vue-property-decorator';
import ActivityNodeModalMixin from './ActivityNodeModalMixin';
import StartNodeModalMixin from './StartNodeModalMixin';
import StopNodeModalMixin from './StopNodeModalMixin';
import BranchNodeModalMixin from './BranchNodeModalMixin';

@Component
export default class ModalMixin extends Mixins(ActivityNodeModalMixin, BranchNodeModalMixin, StartNodeModalMixin, StopNodeModalMixin) {
  //
}
