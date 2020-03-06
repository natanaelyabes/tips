import { BaseService } from '../BaseService';

/**
 * The system service class.
 *
 * @export
 * @abstract
 * @class SystemService
 * @extends {BaseService}
 *
 * @package ts
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export abstract class SystemService extends BaseService {

  /**
   * Base URI for system service.
   *
   * @static
   * @type {string}
   * @memberof SystemService
   */
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/system';
}
