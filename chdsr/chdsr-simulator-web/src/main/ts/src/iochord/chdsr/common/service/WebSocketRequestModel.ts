/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export default class WebSocketRequestModel {

  public static create(uri: string): WebSocketRequestModel {
    const req = new WebSocketRequestModel();
    req.uri = uri;
    return req;
  }

  public uri: string = '';

}
