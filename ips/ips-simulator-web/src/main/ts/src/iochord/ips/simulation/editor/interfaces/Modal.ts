import { Vue } from 'vue-property-decorator';

export interface Modal<P, T> extends Vue {
  populateProperties(page: P, object: T): void;
  saveProperties(page: P, object: T): void;
}
