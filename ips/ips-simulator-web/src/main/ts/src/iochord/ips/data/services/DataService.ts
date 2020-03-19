import { BaseService } from '../../common/services/BaseService';

/**
 * The data service class.
 *
 * @export
 * @abstract
 * @class DataService
 * @extends {BaseService}
 *
 * @package ts
 * @author  I. R. Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export abstract class DataService extends BaseService {

  /**
   * Base URI for data service class.
   *
   * @static
   * @type {string}
   * @memberof DataService
   */
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/data';
}

export default DataService;
