import axios, { AxiosResponse } from 'axios';

import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class BaseService {

  public static readonly BASE_HTTP_URI: string = `${process.env.VUE_APP_BASE_URI}`;
  public static readonly BASE_URI: string = '/chdsr/api/v1';

  private wsConnected: boolean = false;
  private wsSocket: any = null;
  private wsClient: any = null;

  public async remoteGet(url: string): Promise<AxiosResponse> {
    return await axios.get(BaseService.BASE_HTTP_URI + url);
  }

  public async remotePost(url: string, data: any): Promise<AxiosResponse> {
    return await axios.post(BaseService.BASE_HTTP_URI + url, JSON.stringify(data), {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
    });
  }

  public getWsClient(callback: any) {
    console.log('aaa');
    const self = this;
    if (self.wsConnected) {
      callback(self.wsClient);
    } else {
      self.wsSocket = new SockJS(BaseService.BASE_HTTP_URI + '/ws-chdsr');
      self.wsClient = Stomp.over(self.wsSocket);
      self.wsClient.connect(
        {},
        (frame: any) => {
          self.wsConnected = true;
          callback(self.wsClient);
          console.log(frame);
        },
        (error: any) => {
          console.log(error);
        },
      );
    }
  }

}
