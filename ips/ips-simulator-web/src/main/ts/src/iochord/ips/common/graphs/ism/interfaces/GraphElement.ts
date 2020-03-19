import { ObjectSerializable } from '../../../lang/interfaces/ObjectSerializable';
import { BRANCH_TYPE } from '../enums/BRANCH';
import { TSMap } from 'typescript-map';

/**
 * The interface of the graph element.
 *
 * @export
 * @interface Graph
 * @extends {GraphElement}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface GraphElement extends ObjectSerializable {
  getId(): string | null;
  setId(id: string): void;
  getLabel(): string | null;
  setLabel(label: string): void;
  getType(): string | null;
  setType(type: string): void;
  getImageIcon(): string;
  setImageIcon(image: string): void;
  getAttributes(): TSMap<string, string> | null;
  setAttributes(attributes: TSMap<string, string>): void;
}
