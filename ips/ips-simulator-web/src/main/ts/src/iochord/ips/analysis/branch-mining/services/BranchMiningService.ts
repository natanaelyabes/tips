import { AnalysisService } from '@/iochord/ips/analysis/services/AnalysisService';
import ResourceMiningService from '../../resource-mining/services/ResourceMiningService';
import BranchMiningConfiguration from '../models/BranchMiningConfiguration';


/**
 * The branch mining service class
 *
 * @export
 * @class BranchMiningService
 * @extends {AnalysisService}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 */
export default class BranchMiningService extends AnalysisService {

  /**
   * BASE URI for branch mining service class
   *
   * @static
   * @type {string}
   * @memberof BranchMiningService
   */
  public static readonly BASE_URI: string = AnalysisService.BASE_URI + '/discover';

  /**
   * Returns the instance of branch mining service.
   *
   * @static
   * @returns {BranchMiningService}
   * @memberof BranchMiningService
   */
  public static getInstance(): BranchMiningService {
    if (BranchMiningService.__INSTANCE == null) {
      BranchMiningService.__INSTANCE = new BranchMiningService();
    }
    return BranchMiningService.__INSTANCE;
  }

  /**
   * The instance of branch mining service
   *
   * @private
   * @static
   * @type {ResourceMiningService}
   * @memberof BranchMiningService
   */
  private static __INSTANCE: BranchMiningService;

  public mineDecision(config: BranchMiningConfiguration, completeCallback: any, progressCallback: any) {
    return this.webservicePost(BranchMiningService.BASE_URI + '/dtm', config, completeCallback, progressCallback);
  }
}
