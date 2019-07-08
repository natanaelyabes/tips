import { BaseService } from '../BaseService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class DataService extends BaseService {
  //*/
  // PRODUCTION / COMMIT
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model';
  /*/
  // DEVELOPMENT / LOCAL
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model/simulation';
  //*/
  
  private static __INSTANCE: DataService;

  public static getInstance(): DataService {
    if (DataService.__INSTANCE == null) {
      DataService.__INSTANCE = new DataService();
    }
    return DataService.__INSTANCE;
  }

}
