import { BaseService } from '../BaseService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class SimulatorService extends BaseService {
  //*/
  // PRODUCTION / COMMIT
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model';
  /*/
  // DEVELOPMENT / LOCAL
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model/simulation';
  //*/
  
  private static __INSTANCE: SimulatorService;

  public static getInstance(): SimulatorService {
    if (SimulatorService.__INSTANCE == null) {
      SimulatorService.__INSTANCE = new SimulatorService();
    }
    return SimulatorService.__INSTANCE;
  }

}
