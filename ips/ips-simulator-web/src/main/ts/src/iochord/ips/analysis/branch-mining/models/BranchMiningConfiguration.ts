import { DecisionTreeSplitter } from './../enums/DecisionTreeSplitter';
import { DecisionTreeStrategy } from '../enums/DecisionTreeStrategy';

/**
 * Algorithm option and case mapping settings for branch mining API.
 *
 * @export
 * @class BranchMiningConfiguration
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 */
export default class BranchMiningConfiguration {

  /**
   * The dataset schema name to refer from.
   *
   * @type {string}
   * @memberof BranchMiningConfiguration
   */
  public schemaName: string = 'public';

  /**
   * The dataset id of the event log.
   *
   * @type {string}
   * @memberof BranchMiningConfiguration
   */
  public datasetId: string = '';

  /**
   * Set observation threshold for process discovery.
   *
   * @type {number}
   * @memberof BranchMiningConfiguration
   */
  public pdPositiveObservationThreshold: number = 0;

  /**
   * Set dependency threshold for process discovery.
   *
   * @type {number}
   * @memberof BranchMiningConfiguration
   */
  public pdDependencyThreshold: number = 0.9;

  /**
   * Set decision tree strategy for branch mining.
   *
   * @type {DecisionTreeStrategy}
   * @memberof BranchMiningConfiguration
   */
  public strategy: string = DecisionTreeStrategy.GINI;

  /**
   * Set decision tree splitter criterion.
   *
   * @type {string}
   * @memberof BranchMiningConfiguration
   */
  public splitter: string = DecisionTreeSplitter.BEST;

  /**
   * Set max depth of decision tree.
   *
   * @type {(number | null)}
   * @memberof BranchMiningConfiguration
   */
  public maxDepth: number | null = null;

  /**
   * Set random state of decision tree.
   *
   * @type {(number | null)}
   * @memberof BranchMiningConfiguration
   */
  public randomState: number | null = null;

  /**
   * Set random state of train test set.
   *
   * @type {(number | null)}
   * @memberof BranchMiningConfiguration
   */
  public trainTestRandomState: number | null = 1;

  /**
   * Set test size.
   *
   * @type {number}
   * @memberof BranchMiningConfiguration
   */
  public testSize: number = 0.3;

  /**
   * Number of rows to be skipped.
   *
   * @type {number}
   * @memberof BranchMiningConfiguration
   */
  public skipRows: number = 1;
}
