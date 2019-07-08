import { BaseService } from '../BaseService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class DataService extends BaseService {

  public static readonly BASE_URI: string = BaseService.BASE_URI + '/data';

  public static getInstance(): DataService {
    if (DataService.__INSTANCE == null) {
      DataService.__INSTANCE = new DataService();
    }
    return DataService.__INSTANCE;
  }

  private static __INSTANCE: DataService;

}
