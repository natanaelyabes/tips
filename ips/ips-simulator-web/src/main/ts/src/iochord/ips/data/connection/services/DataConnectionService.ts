import DataService from '../../services/DataService';
import ImportCsvConfiguration from '../models/ImportCsvConfiguration';

export { ImportCsvConfiguration };

/**
 * The data connection service class.
 *
 * @export
 * @class DataConnectionService
 * @extends {DataService}
 *
 * @package ts
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 */
export default class DataConnectionService extends DataService {

  /**
   * Base URI for data connection service.
   *
   * @static
   * @type {string}
   * @memberof DataConnectionService
   */
  public static readonly BASE_URI: string = DataService.BASE_URI + '';

  /**
   * Returns the instance of data connection service.
   *
   * @static
   * @returns {DataConnectionService}
   * @memberof DataConnectionService
   */
  public static getInstance(): DataConnectionService {
    if (DataConnectionService.__INSTANCE == null) {
      DataConnectionService.__INSTANCE = new DataConnectionService();
    }
    return DataConnectionService.__INSTANCE;
  }

  /**
   * The instance of data connection service.
   *
   * @private
   * @static
   * @type {DataConnectionService}
   * @memberof DataConnectionService
   */
  private static __INSTANCE: DataConnectionService;

  /**
   * Returns the data connection list.
   *
   * @param {*} completeCallback
   * @param {*} progressCallback
   * @returns
   * @memberof DataConnectionService
   */
  public getDataConnections(completeCallback: any, progressCallback: any) {
    return this.webserviceGet(DataConnectionService.BASE_URI + '/connection/list', completeCallback, progressCallback);
  }

  public getFilteredDataConnections(type: any, completeCallback: any, progressCallback: any) {
    return this.webserviceGet(DataConnectionService.BASE_URI + '/connection/list/' + type, completeCallback, progressCallback);
  }

  /**
   * Import CSV to the web service.
   *
   * @param {ImportCsvConfiguration} config
   * @param {*} file
   * @param {*} completeCallback
   * @param {*} progressCallback
   * @returns
   * @memberof DataConnectionService
   */
  public importCsv(config: ImportCsvConfiguration, file: any, completeCallback: any, progressCallback: any) {
    const req = new FormData();
    req.append('config', JSON.stringify(config));
    req.append('file', file);
    return this.webserviceUpload(DataConnectionService.BASE_URI + '/import/csv', req, completeCallback, progressCallback);
  }

}
