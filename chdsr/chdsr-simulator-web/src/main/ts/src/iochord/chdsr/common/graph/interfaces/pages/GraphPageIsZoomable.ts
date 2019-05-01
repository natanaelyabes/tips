/**
 *
 *
 * @export
 * @interface GraphPageIsZoomable
 */
export interface GraphPageIsZoomable {
  zoomable: true;
  displayZoomTool: boolean;
  fn_graph_page_zoom_in(): void;
  fn_graph_page_zoom_out(): void;
  fn_graph_page_focus(): void;
  fn_graph_page_fit_to_screen(): void;
}
