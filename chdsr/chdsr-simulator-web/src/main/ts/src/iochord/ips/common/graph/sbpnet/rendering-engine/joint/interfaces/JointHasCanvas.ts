import joint from 'jointjs';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointHasCanvasInterface {
  page: joint.dia.Graph;
  graph: joint.dia.Paper;
  options: joint.dia.Paper.Options;
  hasMinimap: boolean;
  setPage(): void;
  getGraph(): void;
  setOptions(): void;
  setMinimap(): void;
}
