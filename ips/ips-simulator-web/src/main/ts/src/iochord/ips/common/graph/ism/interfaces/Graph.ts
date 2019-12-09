import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphData } from './GraphData';
import { GraphControl } from './components/GraphControl';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface Graph extends GraphElement {
  getVersion(): string | null;
  getPages(): TSMap<string, GraphPage> | null;
  setPages(pages: TSMap<string, GraphPage>): void;
  getDefaultPage(): GraphPage | null;
  setDefaultPage(page: GraphPage): void;
  getConfigurations(): TSMap<string, GraphConfiguration> | null;
  setConfigurations(configurations: TSMap<string, GraphConfiguration>): void;
  getControl(): GraphControl | null;
  setControl(control: GraphControl): void;
}
