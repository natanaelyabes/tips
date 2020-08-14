import axios, { AxiosResponse } from 'axios';

import SockJS from 'sockjs-client';
import Stomp, { Client, Subscription } from 'webstomp-client';


/**
 * The base service class.
 *
 * @export
 * @class BaseService
 *
 * @package ts
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export class BaseService {

  /**
   * Base HTTP URI.
   *
   * @static
   * @type {string}
   * @memberof BaseService
   */
  public static readonly BASE_HTTP_URI: string = `${process.env.VUE_APP_BASE_URI}`;

  /**
   * Base URI of the API.
   *
   * @static
   * @type {string}
   * @memberof BaseService
   */
  public static readonly BASE_URI: string = '/ips/api/v1';

  /**
   * Web socket end point.
   *
   * @static
   * @type {string}
   * @memberof BaseService
   */
  public static readonly WS_ENDPOINT: string = '/ws-ipr';

  /**
   * Web socket request URI.
   *
   * @static
   * @type {string}
   * @memberof BaseService
   */
  public static readonly WS_REQUEST_URI: string = '/q';

  /**
   * Web socket response URI.
   *
   * @static
   * @type {string}
   * @memberof BaseService
   */
  public static readonly WS_RESPONSE_URI: string = '/r';

  /**
   * The web service response progress URI.
   *
   * @static
   * @type {string}
   * @memberof BaseService
   */
  public static readonly WS_RESPONSE_PROGRESS_URI: string = BaseService.WS_RESPONSE_URI + '/p';

  /**
   * The web service response completed URI.
   *
   * @static
   * @type {string}
   * @memberof BaseService
   */
  public static readonly WS_RESPONSE_COMPLETED_URI: string = BaseService.WS_RESPONSE_URI + '/c';

  /**
   * Boolean variables indicating the application connected to the web socket.
   *
   * @private
   * @type {boolean}
   * @memberof BaseService
   */
  private wsConnected: boolean = false;

  /**
   * The web socket object.
   *
   * @private
   * @type {*}
   * @memberof BaseService
   */
  private wsSocket: any = null;

  /**
   * The web socket client object.
   *
   * @private
   * @type {*}
   * @memberof BaseService
   */
  private wsClient: any = null;

  /**
   * Retreive data provided by the service at specified URL endpoint.
   *
   * @param {string} url
   * @returns {Promise<AxiosResponse>}
   * @memberof BaseService
   */
  public async remoteGet(url: string): Promise<AxiosResponse> {
    return await axios.get(BaseService.BASE_HTTP_URI + url, {
      headers: {
        'Accept': 'application/json',
        'X-IOCHORD-WSA': 'true',
      },
    });
  }

  /**
   * Send data object to the service provided at specified URL endpoint.
   *
   * @param {string} url
   * @param {*} data
   * @returns {Promise<AxiosResponse>}
   * @memberof BaseService
   */
  public async remotePost(url: string, data: any): Promise<AxiosResponse> {
    return await axios.post(BaseService.BASE_HTTP_URI + url, JSON.stringify(data), {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
    });
  }

  /**
   * Listen to the web socket object.
   *
   * @param {string} url
   * @param {(tick: any) => void} completeCallback
   * @param {(tick: any) => void} progressCallback
   * @memberof BaseService
   */
  public webserviceGet(url: string, completeCallback: (tick: any) => void, progressCallback: (tick: any) => void): void {
    // const self = this;
    axios.get(BaseService.BASE_HTTP_URI + url, {
      headers: {
        'Accept': 'application/json',
        'X-IOCHORD-WSA': 'true',
      },
    })
    .then((rawResponse) => {
      const response = rawResponse.data;
      if (response.info.state === 'COMPLETED') {
        completeCallback(response);
      } else {
        this.getWsClient((client: Client) => {
          let subProgress: Subscription | null = null;
          if (completeCallback !== null && progressCallback !== null) {
            subProgress = client.subscribe(BaseService.WS_RESPONSE_PROGRESS_URI + '/' + response.id, (tick) => {
              const tickData = JSON.parse(tick.body);
              progressCallback(tickData);
            });
          }
          if (completeCallback !== null) {
            const subComplete = client.subscribe(BaseService.WS_RESPONSE_COMPLETED_URI + '/' + response.id, (tick) => {
              client.unsubscribe((subProgress as Subscription).id);
              client.unsubscribe((subComplete as Subscription).id);
              completeCallback(tick);
            });
          }
        });
      }
    });
  }

  /**
   * Put data to the web socket and listen to its amended response.
   *
   * @param {string} url
   * @param {*} data
   * @param {(tick: any) => void} completeCallback
   * @param {(tick: any) => void} progressCallback
   * @memberof BaseService
   */
  public webservicePost(url: string, data: any, completeCallback: (tick: any) => void, progressCallback: (tick: any) => void): void {
    // const self = this;
    axios.post(BaseService.BASE_HTTP_URI + url, JSON.stringify(data), {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'X-IOCHORD-WSA': 'true',
      },
    }).then((rawResponse) => {
      const response = rawResponse.data;
      if (response.info.state === 'COMPLETED') {
        completeCallback(response);
      } else {
        this.getWsClient((client: Client) => {
          let subProgress: Subscription | null = null;
          if (completeCallback !== null && progressCallback !== null) {
            subProgress = client.subscribe(BaseService.WS_RESPONSE_PROGRESS_URI + '/' + response.id, (tick) => {
              const tickData = JSON.parse(tick.body);
              progressCallback(tickData);
            });
          }
          if (completeCallback !== null) {
            const subComplete = client.subscribe(BaseService.WS_RESPONSE_COMPLETED_URI + '/' + response.id, (tick) => {
              client.unsubscribe((subProgress as Subscription).id);
              client.unsubscribe((subComplete as Subscription).id);
              completeCallback(tick);
            });
          }
        });
      }
    });
  }

  /**
   * Web socket service to upload form data.
   *
   * @param {string} url
   * @param {FormData} data
   * @param {(tick: any) => void} completeCallback
   * @param {(tick: any) => void} progressCallback
   * @memberof BaseService
   */
  public webserviceUpload(url: string, data: FormData, completeCallback: (tick: any) => void, progressCallback: (tick: any) => void): void {
    // const self = this;
    axios.post(BaseService.BASE_HTTP_URI + url, data, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'multipart/form-data',
        'X-IOCHORD-WSA': 'true',
      },
    }).then((rawResponse) => {
      const response = rawResponse.data;
      console.log(rawResponse);
      if (response.info.state === 'COMPLETED') {
        completeCallback(response);
      } else {
        this.getWsClient((client: Client) => {
          let subProgress: Subscription | null = null;
          if (completeCallback !== null && progressCallback !== null) {
            subProgress = client.subscribe(BaseService.WS_RESPONSE_PROGRESS_URI + '/' + response.id, (tick) => {
              const tickData = JSON.parse(tick.body);
              progressCallback(tickData);
            });
          }
          if (completeCallback !== null) {
            const subComplete = client.subscribe(BaseService.WS_RESPONSE_COMPLETED_URI + '/' + response.id, (tick) => {
              client.unsubscribe((subProgress as Subscription).id);
              client.unsubscribe((subComplete as Subscription).id);
              completeCallback(tick);
            });
          }
        });
      }
    });
  }

  /**
   * Returns the web socket client.
   *
   * @param {*} callback
   * @memberof BaseService
   */
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
