import { ModelService } from './ModelService';
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import { GraphImpl } from './../../graph/classes/GraphImpl';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class SbpnetModelService extends ModelService {

  public static readonly BASE_URI: string = ModelService.BASE_URI + '/sbpnet';

  public static getInstance(): SbpnetModelService {
    if (SbpnetModelService.__INSTANCE == null) {
      SbpnetModelService.__INSTANCE = new SbpnetModelService();
    }
    return SbpnetModelService.__INSTANCE;
  }

  private static __INSTANCE: SbpnetModelService;

  public async getExampleModel(): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/example');
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getCreateDefault(): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/create');
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getCreate(defaultNodes: string): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/create/' + defaultNodes);
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getEdit(modelId: string): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/edit/' + modelId);
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getView(modelId: string): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/view/' + modelId);
    return GraphImpl.deserialize(response.data) as Graph;
  }

}
