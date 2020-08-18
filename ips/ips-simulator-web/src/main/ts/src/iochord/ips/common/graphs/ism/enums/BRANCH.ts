/**
 * Enumeration for types of branch gate.
 *
 * @export
 * @class GraphPageImpl
 * @extends {GraphElementImpl}
 * @implements {GraphPage}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export enum BRANCH_GATE {

  /**
   * Mutually inclusive operator for branching tokens of event.
   */
  AND = 'AND',

  /**
   * Mutually exclusive operator for branching tokens of event.
   */
  XOR = 'XOR',
}

/**
 * Enumeration for types of branch gate.
 *
 * @export
 * @class GraphPageImpl
 * @extends {GraphElementImpl}
 * @implements {GraphPage}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export enum BRANCH_TYPE {

  /**
   * Seperate the serial workflow into branches of workflow.
   */
  SPLIT = 'SPLIT',

  /**
   * Merge branches of workflow into serial workflow.
   */
  JOIN = 'JOIN',
}

/**
 * Enumeration for types of branch rule.
 *
 * @export
 * @class GraphPageImpl
 * @extends {GraphElementImpl}
 * @implements {GraphPage}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export enum BRANCH_RULE {

  /**
   * Routing into branches by chance.
   */
  PROBABILITY = 'PROBABILITY',

  /**
   * Routing into branches by condition.
   */
  CONDITION = 'CONDITION',

  /**
   * Routing into branches by data.
   */
  DATA = 'DATA',
}
