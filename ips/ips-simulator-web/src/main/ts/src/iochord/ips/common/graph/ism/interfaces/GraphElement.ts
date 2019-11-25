import { ObjectSerializable } from '../../../interfaces/ObjectSerializable';
import { BRANCH_TYPE } from '../enums/BRANCH';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
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
