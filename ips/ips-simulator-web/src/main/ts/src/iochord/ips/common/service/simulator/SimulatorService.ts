import { BaseService } from '../BaseService';

/**
 *
 * @export
 * @abstract
 * @class SimulatorService
 * @extends {BaseService}
 *
 * @package ts
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export abstract class SimulatorService extends BaseService {

  /**
   * Base URI for simulator service.
   *
   * @static
   * @type {string}
   * @memberof SimulatorService
   */
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/simulator';
}
