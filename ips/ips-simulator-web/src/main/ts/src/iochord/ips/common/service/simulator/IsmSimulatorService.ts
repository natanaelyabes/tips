import { SimulatorService } from './SimulatorService';
import { Client } from 'webstomp-client';

/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class IsmSimulatorService extends SimulatorService {

  public static readonly BASE_URI: string = SimulatorService.BASE_URI + '/ism';

  public static getInstance(): IsmSimulatorService {
    if (IsmSimulatorService.__INSTANCE == null) {
      IsmSimulatorService.__INSTANCE = new IsmSimulatorService();
    }
    return IsmSimulatorService.__INSTANCE;
  }

  private static __INSTANCE: IsmSimulatorService;

  public callRunSimulation(graph: any, callback: any) {
    this.getWsClient((wsc: Client) => {
      const wsUri = '/simulation/init';
      const jsonReq = {
        id: (new Date().getTime()),
        method: wsUri,
        params: JSON.stringify(graph),
      };
      console.log(JSON.stringify(graph));
      wsc.subscribe('/response/simulation/step', (tick: any) => {
        callback(tick);
      });
      wsc.send(wsUri, JSON.stringify(jsonReq));
    });
  }
}
