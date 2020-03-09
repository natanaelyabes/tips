import { BaseService } from '../BaseService';

/**
 * The analysis service class
 *
 * @export
 * @abstract
 * @class AnalysisService
 * @extends {BaseService}
 *
 * @package ts
 * @author  I. R. Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export abstract class AnalysisService extends BaseService {

  /**
   * Base URI for analysis service
   *
   * @static
   * @type {string}
   * @memberof AnalysisService
   */
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/analysis';
}

export default AnalysisService;
