import * as joint from 'jointjs';

/**
 * Define the anchors mode of graph.
 *
 * @export
 * @class Anchors
 */
export class Anchors {

  /**
   * Skip end magnet perpendicular anchor.
   *
   * @author clientIO
   * @url https://github.com/clientIO/joint/blob/master/plugins/anchors/joint.anchors.js
   *
   * @static
   * @param {joint.dia.ElementView} endView
   * @param {SVGElement} endMagnet
   * @param {joint.g.Point} anchorReference
   * @param {{ [key: string]: any; }} args
   * @returns
   * @memberof Anchors
   */
  public static skipEndMagnetPerpendicularAnchor(endView: joint.dia.ElementView, endMagnet: SVGElement, anchorReference: joint.g.Point, args: { [key: string]: any; }) {
    const angle: number = endView.model.angle();
    // const bbox: joint.g.Rect = endView.getNodeBBox(endMagnet);                           // instead of targeting endMagnet
    const bbox: joint.g.Rect = endView.getNodeBBox(endMagnet.childNodes[0] as SVGElement);  // We target it's child (rect)
    const anchor: joint.g.Point = bbox.center();
    const topLeft: joint.g.Point = bbox.origin();
    const bottomRight: joint.g.Point = bbox.corner();

    let padding = args.padding;
    if (!isFinite(padding)) { padding = 0; }

    if ((topLeft.y + padding) <= anchorReference.y && anchorReference.y <= (bottomRight.y - padding)) {
        const dy = (anchorReference.y - anchor.y);
        anchor.x += (angle === 0 || angle === 180) ? 0 : dy * 1 / Math.tan(joint.g.toRad(angle));
        anchor.y += dy;
    } else if ((topLeft.x + padding) <= anchorReference.x && anchorReference.x <= (bottomRight.x - padding)) {
        const dx = (anchorReference.x - anchor.x);
        anchor.y += (angle === 90 || angle === 270) ? 0 : dx * Math.tan(joint.g.toRad(angle));
        anchor.x += dx;
    }
    return anchor;
  }
}
