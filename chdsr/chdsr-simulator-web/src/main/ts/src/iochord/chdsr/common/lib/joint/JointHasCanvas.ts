import joint from 'jointjs';
/**
 *
 *
 * @export
 * @interface JointHasCanvasInterface
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
