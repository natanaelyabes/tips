import { BaseService } from '../BaseService';
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import { GraphImpl } from './../../graph/classes/GraphImpl';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export abstract class ModelService extends BaseService {

  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model';

}
