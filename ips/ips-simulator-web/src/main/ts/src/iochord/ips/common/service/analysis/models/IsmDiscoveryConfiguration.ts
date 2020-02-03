/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class IsmDiscoveryConfiguration {
  public datasetId: string = '';

  public colCaseId: string = 'c0';

  public colEventActivity: string = 'c1';

  public colEventTimestamp: string = 'c121';

  public skipRows: number = 1;
}
