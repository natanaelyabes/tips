
export default interface IMappingResource {
  technicalNames: string[];
  colHeaders: Map<string, string>;
  mapSettings: Map<string, string>;
  firstNRows: Array<Map<string, string>>;
}
