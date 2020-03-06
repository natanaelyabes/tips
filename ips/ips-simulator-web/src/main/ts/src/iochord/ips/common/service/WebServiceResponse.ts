import { WebServiceStatus } from './WebServiceStatus';

/**
 * The web socket response class
 *
 * @export
 * @class WebServiceResponse
 *
 * @package ts
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export class WebServiceResponse {
  /**
   * Data response from the web socket.
   *
   * @type {*}
   * @memberof WebServiceResponse
   */
  public data: any = null;

  /**
   * The status of web socket response.
   *
   * @type {WebServiceStatus}
   * @memberof WebServiceResponse
   */
  public status: WebServiceStatus = new WebServiceStatus();
}
