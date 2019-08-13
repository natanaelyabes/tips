// Vue & libraries
import { Component, Mixins } from 'vue-property-decorator';

// Classes
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

// Mixins
import NodeMixin from './toolbox/NodeMixin';




@Component
export default class PaletteMixin extends Mixins(BaseComponent, NodeMixin) {

}
