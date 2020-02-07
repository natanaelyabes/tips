/**
 * The web socket status class.
 *
 * @export
 * @class WebServiceStatus
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class WebServiceStatus {

  /**
   * Errors object retreive from web socket.
   *
   * @type {*}
   * @memberof WebServiceStatus
   */
  public errors: any = null;

  /**
   * Status of web socket.
   *
   * @type {string}
   * @memberof WebServiceStatus
   */
  public status: string = '';

  /**
   * Progress of the web socket.
   *
   * @type {string}
   * @memberof WebServiceStatus
   */
  public progressWsUri: string = '';

  /**
   * Response from the web socket after complete listening.
   *
   * @type {string}
   * @memberof WebServiceStatus
   */
  public completeWsUri: string = '';
}
