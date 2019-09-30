/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export enum BRANCH_GATE {
  AND = 'AND',
  XOR = 'XOR',
}

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export enum BRANCH_TYPE {
  SPLIT = 'SPLIT',
  JOIN = 'JOIN',
}

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export enum BRANCH_RULE {
  PROBABILITY = 'PROBABILITY',
  CONDITION = 'CONDITION',
  DATA = 'DATA',
}
