import { SimulatorService } from '../../services/SimulatorService';
import { Client } from 'webstomp-client';
import { Graph } from '@/iochord/ips/common/graphs/ism/interfaces/Graph';
import { GraphImpl } from '@/iochord/ips/common/graphs/ism/class/GraphImpl';
import IsmDiscoveryConfiguration from '@/iochord/ips/analysis/process-discovery/models/IsmDiscoveryConfiguration';


/**
 * The ISM simulator service class.
 *
 * @export
 * @class IsmSimulatorService
 * @extends {SimulatorService}
 *
 * @package ts
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 */
export class IsmSimulatorService extends SimulatorService {

  /**
   * Base URI for ISM simulator service.
   *
   * @static
   * @type {string}
   * @memberof IsmSimulatorService
   */
  public static readonly BASE_URI: string = SimulatorService.BASE_URI + '/cpnscala';

  /**
   * Returns the instance of ISM simulator service.
   *
   * @static
   * @returns {IsmSimulatorService}
   * @memberof IsmSimulatorService
   */
  public static getInstance(): IsmSimulatorService {
    if (IsmSimulatorService.__INSTANCE == null) {
      IsmSimulatorService.__INSTANCE = new IsmSimulatorService();
    }
    return IsmSimulatorService.__INSTANCE;
  }

  /**
   * The instance of ISM simulator service.
   *
   * @private
   * @static
   * @type {IsmSimulatorService}
   * @memberof IsmSimulatorService
   */
  private static __INSTANCE: IsmSimulatorService;

  /**
   * Run simulation through web socket.
   *
   * @param {*} graph
   * @param {*} callback
   * @memberof IsmSimulatorService
   */
  public callRunSimulation(graph: any, callback: any) {
    this.getWsClient((wsc: Client) => {
      const wsUri = '/simulation/init';
      const jsonReq = {
        id: (new Date().getTime()),
        method: wsUri,
        params: JSON.stringify(graph),
      };
      wsc.subscribe('/response/simulation/step', (tick: any) => {
        callback(tick);
      });
      wsc.send(wsUri, JSON.stringify(jsonReq));
    });
  }

  /**
   * Load the simulation model to the engine and run simulation.
   *
   * @param {(Graph|GraphImpl)} graph
   * @returns {Promise<string>}
   * @memberof IsmSimulatorService
   */
  public async postLoadNPlay(graph: Graph|GraphImpl): Promise<string> {
    const page = (graph as any).pages.get('0');
    (page as any).connectors = page.connectors;
    const response = await this.remotePost(IsmSimulatorService.BASE_URI + '/loadnplay', graph);
    return response.data;
  }

  public async postLoadNPlayWithDataset(config: IsmDiscoveryConfiguration, datasetId: string): Promise<string> {
    config.datasetId = datasetId;
    const response = await this.remotePost(IsmSimulatorService.BASE_URI + '/loadnplay/' + datasetId, config);
    return response.data;
  }
  public postLoadNPlayWS(graph: Graph|GraphImpl, completeCallback: any, progressCallback: any): Promise<string> {
    const page = (graph as any).pages.get('0');
    (page as any).connectors = page.connectors;
    return this.webservicePost(IsmSimulatorService.BASE_URI + '/loadnplay', graph, completeCallback, progressCallback);
  }

  public postLoadNPlayWithDatasetWS(config: IsmDiscoveryConfiguration, datasetId: string, completeCallback: any, progressCallback: any): Promise<string> {
    config.datasetId = datasetId;
    return this.webservicePost(IsmSimulatorService.BASE_URI + '/loadnplay/' + datasetId, config, completeCallback, progressCallback);
  }

}
