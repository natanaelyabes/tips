
export default interface IMappingResource {
  technicalNames: string[];
  mapSettings: Array<Map<string, string>>;
  firstNRows: Array<Map<string, string>>;
}
