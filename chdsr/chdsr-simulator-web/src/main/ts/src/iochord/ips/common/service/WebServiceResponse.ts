import { WebServiceStatus } from './WebServiceStatus';

/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class WebServiceResponse {
  public data: any = null;
  public status: WebServiceStatus = new WebServiceStatus();
}
