/**
 * Zoom interface of the graph.
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphPageIsZoomable {

  /**
   * Allows graph to be zoomed. False otherwise.
   *
   * @type {true}
   * @memberof GraphPageIsZoomable
   */
  zoomable: true;

  /**
   * Allow zoom tool to be displayed in the screen. False otherwise.
   *
   * @type {boolean}
   * @memberof GraphPageIsZoomable
   */
  displayZoomTool: boolean;

  /**
   * Zoom in to the graph.
   *
   * @memberof GraphPageIsZoomable
   */
  zoomIn(): void;

  /**
   * Zoom out from the graph.
   *
   * @memberof GraphPageIsZoomable
   */
  zoomOut(): void;

  /**
   * Allow canvas to be focused and zoomed towards the mouse pointer.
   *
   * @memberof GraphPageIsZoomable
   */
  focus(): void;

  /**
   * Fit the graph to screen.
   *
   * @memberof GraphPageIsZoomable
   */
  fitToScreen(): void;
}
