import DataService from '../../services/DataService';
import MappingResource from '../models/MappingResource';

export default class MappingService extends DataService {
  public static readonly BASE_URI: string = DataService.BASE_URI + '';

  public static getInstance(): MappingService {
    if (MappingService.__INSTANCE == null) {
      MappingService.__INSTANCE = new MappingService();
    }
    return MappingService.__INSTANCE;
  }

  private static __INSTANCE: MappingService;

  public getMappingSettingsWs(datasetId: string, completeCallback: any, progressCallback: any) {
    return this.webserviceGet(MappingService.BASE_URI + '/mapping/' + datasetId, completeCallback, progressCallback);
  }

  public async getMappingSettings(datasetId: string) {
    const response = await this.remoteGet(MappingService.BASE_URI + '/mapping/' + datasetId);
    const data = response.data.data;
    const mapping: MappingResource = new MappingResource();
    mapping.setTechnicalNames(data.technicalNames);
    mapping.setColHeaders(data.colHeaders);
    mapping.setMapSettings(data.mapSettings);
    mapping.setFirstNRows(data.firstNRows);
    return mapping;
  }

  public postMappingSettings(datasetId: string, mapping: string, completeCallback: any, progressCallback: any) {
    return this.webservicePost(MappingService.BASE_URI + '/mapping/' + datasetId, JSON.parse(mapping), completeCallback, progressCallback);
  }
}
