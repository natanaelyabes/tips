import { DataService } from './DataService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export default class DataConnectionService extends DataService {

  public static readonly BASE_URI: string = DataService.BASE_URI + '';

  public static getInstance(): DataConnectionService {
    if (DataConnectionService.__INSTANCE == null) {
      DataConnectionService.__INSTANCE = new DataConnectionService();
    }
    return DataConnectionService.__INSTANCE;
  }

  private static __INSTANCE: DataConnectionService;

  public test(completeCallback: any, progressCallback: any) {
    this.webserviceGet(DataConnectionService.BASE_URI + '/import/test', completeCallback, progressCallback);
  }

  public importCsv(data: FormData, completeCallback: any, progressCallback: any) {
    this.webserviceUpload(DataConnectionService.BASE_URI + '/import/csv', data, completeCallback, progressCallback);
  }
}
