import axios, { AxiosResponse } from 'axios';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class BaseService {

  public static readonly BASE_URI: string = `${process.env.VUE_APP_BASE_URI}/chdsr/api/v1`;

  public async remoteGet(url: string): Promise<AxiosResponse> {
    return await axios.get(url);
  }

  public async remotePost(url: string, data: object): Promise<AxiosResponse> {
    return await axios.post(url, data);
  }
}
