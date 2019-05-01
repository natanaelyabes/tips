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
  fn_canvas_set_page(): void;
  fn_canvas_set_graph(): void;
  fn_canvas_set_options(): void;
  fn_canvas_set_minimap(): void;
}
