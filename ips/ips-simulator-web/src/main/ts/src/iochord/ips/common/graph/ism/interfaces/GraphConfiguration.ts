import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphConfiguration extends GraphElement {
  getPages(): TSMap<string, GraphPage> | null;
  setPages(pages: TSMap<string, GraphPage>): void;
}
