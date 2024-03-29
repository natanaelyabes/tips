import { Vue } from 'vue-property-decorator';

/**
 * Deleteable modal interface.
 *
 * @export
 * @interface DeletableModal
 * @extends {Vue}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export interface DeletableModal extends Vue {

  /**
   * Put the nodes and its depended nodes to the list of deleted nodes.
   *
   * @param {*} node
   * @memberof DeleteModal
   */
  populateNode(node: any): void;

  /**
   * Reset the list of deleted nodes.
   *
   * @memberof DeletableModal
   */
  reset(): void;
}
