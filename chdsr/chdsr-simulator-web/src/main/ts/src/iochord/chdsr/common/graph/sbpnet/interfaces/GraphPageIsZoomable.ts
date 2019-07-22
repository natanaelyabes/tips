/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphPageIsZoomable {
  zoomable: true;
  displayZoomTool: boolean;
  zoomIn(): void;
  zoomOut(): void;
  focus(): void;
  fitToScreen(): void;
}
