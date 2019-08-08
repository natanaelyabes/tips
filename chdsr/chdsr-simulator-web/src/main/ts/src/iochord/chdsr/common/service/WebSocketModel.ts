import axios, { AxiosResponse } from 'axios';
import { BaseService } from './BaseService';
import WebSocketRequestModel from './WebSocketRequestModel';
import WebSocketResponseModel from './WebSocketResponseModel';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export default class WebSocketModel extends BaseService {

  public async invoke(req: WebSocketRequestModel) {
    const response = await this.remoteGet(req.uri);
    console.log(response);
    return response;
  }

  public onSuccess(res: WebSocketResponseModel) {
    //
  }

  public onUpdated(res: WebSocketResponseModel) {
    //
  }

  public onCanceled(res: WebSocketResponseModel) {
    //
  }

  public onError(res: WebSocketResponseModel) {
    //
  }
}
