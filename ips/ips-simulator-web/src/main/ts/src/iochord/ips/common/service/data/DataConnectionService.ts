import DataService from './DataService';
import ImportCsvConfiguration from './models/ImportCsvConfiguration';

/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export { ImportCsvConfiguration };

export default class DataConnectionService extends DataService {

  public static readonly BASE_URI: string = DataService.BASE_URI + '';

  public static getInstance(): DataConnectionService {
    if (DataConnectionService.__INSTANCE == null) {
      DataConnectionService.__INSTANCE = new DataConnectionService();
    }
    return DataConnectionService.__INSTANCE;
  }

  private static __INSTANCE: DataConnectionService;

  public getDataConnections(completeCallback: any, progressCallback: any) {
    return this.webserviceGet(DataConnectionService.BASE_URI + '/connection/list', completeCallback, progressCallback);
  }

  public importCsv(config: ImportCsvConfiguration, file: any, completeCallback: any, progressCallback: any) {
    const req = new FormData();
    req.append('config', JSON.stringify(config));
    req.append('file', file);
    return this.webserviceUpload(DataConnectionService.BASE_URI + '/import/csv', req, completeCallback, progressCallback);
  }

}
