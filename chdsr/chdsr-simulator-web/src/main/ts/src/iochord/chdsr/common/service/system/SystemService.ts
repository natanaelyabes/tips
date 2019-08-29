import { BaseService } from '../BaseService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export abstract class SystemService extends BaseService {

  public static readonly BASE_URI: string = BaseService.BASE_URI + '/system';

}