import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphData } from './GraphData';
import { GraphControl } from './components/GraphControl';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface Graph extends GraphElement {
  getVersion(): string | null;
  getPages(): Map<string, GraphPage> | null;
  setPages(pages: Map<string, GraphPage>): void;
  getDefaultPage(): GraphPage | null;
  setDefaultPage(page: GraphPage): void;
  getConfigurations(): Map<string, GraphConfiguration> | null;
  setConfigurations(configurations: Map<string, GraphConfiguration>): void;
  getControl(): GraphControl | null;
  setControl(control: GraphControl): void;
  getData(): Map<string, GraphData> | null;
  setData(data: Map<string, GraphData>): void;
}
