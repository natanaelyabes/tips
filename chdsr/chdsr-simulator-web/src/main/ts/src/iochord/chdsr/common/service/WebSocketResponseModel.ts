/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export default class WebSocketResponseModel {

  public static create(uri: string): WebSocketResponseModel {
    const req = new WebSocketResponseModel();
    req.uri = uri;
    return req;
  }

  public uri: string = '';

}
