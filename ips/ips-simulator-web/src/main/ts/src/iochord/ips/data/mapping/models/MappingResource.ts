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

  // public getTSColHeaders(): Array<TSMap<string, string>> {
  //   const tsColHeaders = new Array<TSMap<string, string>>();
  //   this.colHeaders.forEach((value: Map<string, string>) => {
  //     const map = new TSMap<string, string>();
  //     const entry = new Map(Object.entries(value));
  //     map.set(entry.keys().next().value, entry.values().next().value);
  //     tsColHeaders.push(map);
  //   });
  //   return tsColHeaders;
  // }

  public setColHeaders(colHeaders: Map<string, string>) {
    this.colHeaders = colHeaders;
  }

  public getMapSettings(): Map<string, string> {
    return this.mapSettings;
  }

  // public getTSMapSettings(): Array<TSMap<string, string>> {
  //   const tsMapSettings = new Array<TSMap<string, string>>();
  //   this.mapSettings.forEach((value: Map<string, string>) => {
  //     const map = new TSMap<string, string>();
  //     const entry = new Map(Object.entries(value));
  //     map.set(entry.keys().next().value, entry.values().next().value);
  //     tsMapSettings.push(map);
  //   });
  //   return tsMapSettings;
  // }

  public setMapSettings(mapSettings: Map<string, string>) {
    this.mapSettings = mapSettings;
  }

  public getFirstNRows(): Array<Map<string, string>> {
    return this.firstNRows;
  }

  // public getTSFirstNRows(): Array<TSMap<string, string>> {
  //   const tsFirstNRows = new Array<TSMap<string, string>>();
  //   this.mapSettings.map((value: Map<string, string>) => {
  //     const map = new TSMap<string, string>();
  //     const entry = new Map(Object.entries(value));
  //     map.set(entry.keys().next().value, entry.values().next().value);
  //     tsFirstNRows.push(map);
  //   });
  //   return tsFirstNRows;
  // }

  public setFirstNRows(firstNRows: Array<Map<string, string>>) {
    this.firstNRows = firstNRows;
  }
}

