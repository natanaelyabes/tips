import { Vue } from 'vue-property-decorator';

export interface DeletableModal extends Vue {
  populateNode(node: any): void;
  reset(): void;
}
