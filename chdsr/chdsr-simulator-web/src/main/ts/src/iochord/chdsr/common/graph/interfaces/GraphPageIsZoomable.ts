/**
 *
 *
 * @export
 * @interface GraphPageIsZoomable
 */
export interface GraphPageIsZoomable {
  zoomable: true;
  displayZoomTool: boolean;
  zoomIn(): void;
  zoomOut(): void;
  focus(): void;
  fitToScreen(): void;
}
