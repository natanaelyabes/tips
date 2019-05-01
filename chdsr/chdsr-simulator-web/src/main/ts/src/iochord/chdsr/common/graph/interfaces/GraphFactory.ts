import { Graph } from './Graph';
import { GraphPage } from './pages/GraphPage';

export interface GraphFactory {
  create(ref?: Graph): Graph;
  addPage(net: Graph): GraphPage;

}
