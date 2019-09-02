import { SimulatorService } from './SimulatorService';
import { Graph } from '@/iochord/ips/common/graph/sbpnet/interfaces/Graph';
import { GraphImpl } from '@/iochord/ips/common/graph/sbpnet/classes/GraphImpl';
import { Client } from 'webstomp-client';
import { Observable } from 'rxjs';
/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class SbpnetSimulatorService extends SimulatorService {

  public static readonly BASE_URI: string = SimulatorService.BASE_URI + '/sbpnet';

  public static getInstance(): SbpnetSimulatorService {
    if (SbpnetSimulatorService.__INSTANCE == null) {
      SbpnetSimulatorService.__INSTANCE = new SbpnetSimulatorService();
    }
    return SbpnetSimulatorService.__INSTANCE;
  }

  private static __INSTANCE: SbpnetSimulatorService;

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
      // this.remotePost(wsUri, graph);
    });
  }

}
