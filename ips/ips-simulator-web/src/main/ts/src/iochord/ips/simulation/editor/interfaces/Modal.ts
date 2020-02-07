import { Vue } from 'vue-property-decorator';

/**
 * Modal interface.
 *
 * @export
 * @interface Modal
 * @extends {Vue}
 * @template P
 * @template T
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export interface Modal<P, T> extends Vue {

  /**
   * Assigns the properties of object T to the field of its modal.
   *
   * @param {P} page
   * @param {T} object
   * @memberof Modal
   */
  populateProperties(page: P, object: T): void;

  /**
   * Store the properties into T data object, commit to vuex store.
   *
   * @param {P} page
   * @param {T} object
   * @memberof Modal
   */
  saveProperties(page: P, object: T): void;
}
