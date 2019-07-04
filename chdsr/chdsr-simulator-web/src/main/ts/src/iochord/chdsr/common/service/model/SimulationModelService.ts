import { GraphImpl } from './../../graph/classes/GraphImpl';
import { Graph } from '@/iochord/chdsr/common/graph/interfaces/Graph';
import axios from 'axios';
import { BaseService } from '../BaseService';

export class SimulationModelService extends BaseService {
  constructor(url: string) {
    super(url);
  }

  public getGraph(): Graph {
    return GraphImpl.deserialize(this.response.data) as Graph;
  }
}
