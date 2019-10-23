import { Vue } from 'vue-property-decorator';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';

export interface Modal<P, T> extends Vue {
  populateProperties(page: P, object: T): void;
  saveProperties(page: P, object: T): void;
}
