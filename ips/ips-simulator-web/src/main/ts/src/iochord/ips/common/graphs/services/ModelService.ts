import { BaseService } from '../../services/BaseService';

/**
 *
 * @export
 * @abstract
 * @class ModelService
 * @extends {BaseService}
 *
 * @package ts
 * @author  I. R. Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export abstract class ModelService extends BaseService {

  /**
   * Base URI for model service.
   *
   * @static
   * @type {string}
   * @memberof ModelService
   */
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model';
}
