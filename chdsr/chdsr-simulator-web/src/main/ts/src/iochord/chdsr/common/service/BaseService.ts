import axios, { AxiosResponse } from 'axios';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class BaseService {

  // /*/
  // PRODUCTION / COMMIT
  public static readonly BASE_URI: string = `${process.env.VUE_APP_BASE_URI_PROD}/chdsr/api`;
  /*/
  // DEVELOPMENT / LOCAL
  public static readonly BASE_URI: string = `${process.env.VUE_APP_BASE_URI_DEV}/chdsr/api`;
  // /*/

  public async remoteGet(url: string): Promise<AxiosResponse> {
    return await axios.get(url);
  }

  public async remotePost(url: string, data: object): Promise<AxiosResponse> {
    return await axios.post(url, data);
  }
  // TODO: Inject Security Procedure !

}
