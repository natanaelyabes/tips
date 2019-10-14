import { BaseService } from '../BaseService';

/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export abstract class AnalysisService extends BaseService {

  public static readonly BASE_URI: string = BaseService.BASE_URI + '/analysis';

}

export default AnalysisService;
