import { first } from 'rxjs-compat/operator/first';
import { TSMap } from 'typescript-map';
import IMappingResource from '../interfaces/IMappingResource';

export default class MappingResource implements IMappingResource {
  public technicalNames: string[] = [];
  public colHeaders: Map<string, string> = new Map<string, string>();
  public mapSettings: Map<string, string> = new Map<string, string>();
  public firstNRows: Array<Map<string, string>> = new Array<Map<string, string>>();

  constructor() {
    //
  }

  public getTechnicalNames(): string[] {
    return this.technicalNames;
  }

  public setTechnicalNames(technicalNames: string[]) {
    this.technicalNames = technicalNames;
  }

  public getColHeaders(): Map<string, string> {
    return this.colHeaders;
  }

  public setColHeaders(colHeaders: Map<string, string>) {
    this.colHeaders = colHeaders;
  }

  public getMapSettings(): Map<string, string> {
    return this.mapSettings;
  }

  public setMapSettings(mapSettings: Map<string, string>) {
    this.mapSettings = mapSettings;
  }

  public getFirstNRows(): Array<Map<string, string>> {
    return this.firstNRows;
  }

  public setFirstNRows(firstNRows: Array<Map<string, string>>) {
    this.firstNRows = firstNRows;
  }

  public toJson() {
    const colHeaders = new TSMap(Object.entries(this.colHeaders)).toJSON();
    const mapSettings = new TSMap(Object.entries(this.mapSettings)).toJSON();
    const firstNRows =  new Array<{ [key: string]: any; }>();

    this.firstNRows.forEach((row) => {
      const map = new TSMap(Object.entries(row));
      firstNRows.push(map.toJSON());
    });

    return {
      technicalNames: this.technicalNames,
      colHeaders,
      mapSettings,
      firstNRows,
    };
  }
}

