import axios, { AxiosResponse } from 'axios';

import SockJS from 'sockjs-client';
import Stomp, { Client, Subscription } from 'webstomp-client';

/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class BaseService {
  public static readonly BASE_HTTP_URI: string = `${process.env.VUE_APP_BASE_URI}`;
  public static readonly BASE_URI: string = '/ips/api/v1';

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

  public webserviceGet(url: string, completeCallback: any, progressCallback: any): void {
    // const self = this;
    axios.get(BaseService.BASE_HTTP_URI + url)
    .then((rawResponse) => {
      const response = rawResponse.data;
      if (response.status.status === 'completed') {
        completeCallback(response);
      } else {
        this.getWsClient((client: Client) => {
          let subProgress: Subscription | null = null;
          if (completeCallback !== null && progressCallback !== null) {
            subProgress = client.subscribe(response.status.progressWsUri, (tick) => {
              progressCallback(tick);
            });
          }
          if (completeCallback !== null) {
            const subComplete = client.subscribe(response.status.completeWsUri, (tick) => {
              client.unsubscribe((subProgress as Subscription).id);
              client.unsubscribe((subComplete as Subscription).id);
              completeCallback(tick);
            });
          }
        });
      }
    });
  }

  public webservicePost(url: string, data: any, completeCallback: any, progressCallback: any): void {
    // const self = this;
    axios.post(BaseService.BASE_HTTP_URI + url, JSON.stringify(data), {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
    }).then((rawResponse) => {
      const response = rawResponse.data;
      if (response.status.status === 'completed') {
        completeCallback(response);
      } else {
        this.getWsClient((client: Client) => {
          let subProgress: Subscription | null = null;
          if (completeCallback !== null && progressCallback !== null) {
            subProgress = client.subscribe(response.status.progressWsUri, (tick) => {
              progressCallback(tick);
            });
          }
          if (completeCallback !== null) {
            const subComplete = client.subscribe(response.status.completeWsUri, (tick) => {
              client.unsubscribe((subProgress as Subscription).id);
              client.unsubscribe((subComplete as Subscription).id);
              completeCallback(tick);
            });
          }
        });
      }
    });
  }

  public webserviceUpload(url: string, data: FormData, completeCallback: any, progressCallback: any): void {
    // const self = this;
    axios.post(BaseService.BASE_HTTP_URI + url, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    }).then((rawResponse) => {
      const response = rawResponse.data;
      if (response.status.status === 'completed') {
        completeCallback(response);
      } else {
        this.getWsClient((client: Client) => {
          let subProgress: Subscription | null = null;
          if (completeCallback !== null && progressCallback !== null) {
            subProgress = client.subscribe(response.status.progressWsUri, (tick) => {
              progressCallback(tick);
            });
          }
          if (completeCallback !== null) {
            const subComplete = client.subscribe(response.status.completeWsUri, (tick) => {
              client.unsubscribe((subProgress as Subscription).id);
              client.unsubscribe((subComplete as Subscription).id);
              completeCallback(tick);
            });
          }
        });
      }
    });
  }

  public getWsClient(callback: any) {
    const self = this;
    if (self.wsConnected) {
      callback(self.wsClient);
    } else {
      self.wsSocket = new SockJS(BaseService.BASE_HTTP_URI + '/ws-ips');
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