import joint from 'jointjs';

/**
 * JointHasCanvas interface
 *
 * @export
 * @interface JointHasCanvasInterface
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointHasCanvasInterface {

  /**
   *
   *
   * @type {joint.dia.Graph}
   * @memberof JointHasCanvasInterface
   */
  page: joint.dia.Graph;

  /**
   *
   *
   * @type {joint.dia.Paper}
   * @memberof JointHasCanvasInterface
   */
  graph: joint.dia.Paper;

  /**
   *
   *
   * @type {joint.dia.Paper.Options}
   * @memberof JointHasCanvasInterface
   */
  options: joint.dia.Paper.Options;

  /**
   *
   *
   * @type {boolean}
   * @memberof JointHasCanvasInterface
   */
  hasMinimap: boolean;

  /**
   *
   *
   * @memberof JointHasCanvasInterface
   */
  setPage(): void;

  /**
   *
   *
   * @memberof JointHasCanvasInterface
   */
  getGraph(): void;

  /**
   *
   *
   * @memberof JointHasCanvasInterface
   */
  setOptions(): void;

  /**
   *
   *
   * @memberof JointHasCanvasInterface
   */
  setMinimap(): void;
}
