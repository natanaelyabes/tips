/**
 * Parameter settings for importing CSV file.
 *
 * @export
 * @class ImportCsvConfiguration
 *
 * @package ts
 * @author I. R. Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 */
export default class ImportCsvConfiguration {

  /**
   * The name of CSV file
   *
   * @type {string}
   * @memberof ImportCsvConfiguration
   */
  public name: string = '';

  /**
   * Delimiter of the csv file.
   *
   * @type {string}
   * @memberof ImportCsvConfiguration
   */
  public delimeter: string = ';';

  /**
   * The index of header row.
   *
   * @type {number}
   * @memberof ImportCsvConfiguration
   */
  public headerRowIdx: number = 0;
}
