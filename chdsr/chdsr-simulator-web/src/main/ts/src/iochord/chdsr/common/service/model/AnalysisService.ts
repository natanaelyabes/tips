import { BaseService } from '../BaseService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class AnalysisService extends BaseService {

  public static readonly BASE_URI: string = BaseService.BASE_URI + '/analysis';

  public static getInstance(): AnalysisService {
    if (AnalysisService.__INSTANCE == null) {
      AnalysisService.__INSTANCE = new AnalysisService();
    }
    return AnalysisService.__INSTANCE;
  }

  private static __INSTANCE: AnalysisService;

}
