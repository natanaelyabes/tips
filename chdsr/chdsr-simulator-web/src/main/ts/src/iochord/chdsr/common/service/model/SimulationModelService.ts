import { BaseService } from '../BaseService';
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import { GraphImpl } from './../../graph/classes/GraphImpl';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class SimulationModelService extends BaseService {
  //*/
  // PRODUCTION / COMMIT
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model';
  /*/
  // DEVELOPMENT / LOCAL
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model/simulation';
  //*/
  
  private static __INSTANCE: SimulationModelService;

  public static getInstance(): SimulationModelService {
    if (SimulationModelService.__INSTANCE == null) {
      SimulationModelService.__INSTANCE = new SimulationModelService();
    }
    return SimulationModelService.__INSTANCE;
  }

  public async getExampleModel(): Promise<Graph> {
    const response = await this.remoteGet(SimulationModelService.BASE_URI + '/example');
    return GraphImpl.deserialize(response.data) as Graph;
  }
  
  public async getCreateDefault(): Promise<Graph> {
    const response = await this.remoteGet(SimulationModelService.BASE_URI + '/create');
    return GraphImpl.deserialize(response.data) as Graph;
  }
  
  public async getCreate(defaultNodes: string): Promise<Graph> {
    const response = await this.remoteGet(SimulationModelService.BASE_URI + '/create/' + defaultNodes);
    return GraphImpl.deserialize(response.data) as Graph;
  }
  
  public async getEdit(modelId: string): Promise<Graph> {
    const response = await this.remoteGet(SimulationModelService.BASE_URI + '/edit/' + modelId);
    return GraphImpl.deserialize(response.data) as Graph;
  }
  
  public async getView(modelId: string): Promise<Graph> {
    const response = await this.remoteGet(SimulationModelService.BASE_URI + '/view/' + modelId);
    return GraphImpl.deserialize(response.data) as Graph;
  }
  
}
