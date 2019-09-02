import { ModelService } from './ModelService';
import { Graph } from '@/iochord/ips/common/graph/sbpnet/interfaces/Graph';
import { GraphImpl } from '@/iochord/ips/common/graph/sbpnet/classes/GraphImpl';
import { Client } from 'webstomp-client';
import { Observable } from 'rxjs';

/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class SbpnetModelService extends ModelService {

  public static readonly BASE_URI: string = ModelService.BASE_URI + '/sbpnet';

  public static getInstance(): SbpnetModelService {
    if (SbpnetModelService.__INSTANCE == null) {
      SbpnetModelService.__INSTANCE = new SbpnetModelService();
    }
    return SbpnetModelService.__INSTANCE;
  }

  private static __INSTANCE: SbpnetModelService;

  public callSaveModel(graph: any, callback: any) {
    this.getWsClient((wsc: Client) => {
      const wsUri = SbpnetModelService.BASE_URI + '/edit/1';
      wsc.subscribe('/res' + wsUri, (tick: any) => {
        wsc.unsubscribe('/res' + wsUri);
        callback(tick);
      });
      this.remotePost(wsUri, graph);
    });
  }

  public getExampleModelWs(callback: any) {
    this.getWsClient((wsc: Client) => {
      const wsUri = '/res/test';
      wsc.subscribe(wsUri, (tick) => {
        wsc.unsubscribe(wsUri);
        const dfMatrix = JSON.parse(tick.body);
        const nodes: any = {};
        const arcs: any = {};
        let ni = 0;
        let ai = 0;
        for (const af in dfMatrix) {
          if (dfMatrix.hasOwnProperty(af)) {
            const ats = dfMatrix[af];
            if (!nodes.hasOwnProperty(af)) {
              nodes[af] = ni++;
            }
            for (const at in ats) {
              if (ats.hasOwnProperty(at)) {
                const f = ats[at];
                if (!nodes.hasOwnProperty(at)) {
                  nodes[at] = ni++;
                }
                arcs[ai] = [ nodes[af], nodes[at], f ];
                ai++;
              }
            }
          }
        }
        console.log(ni, ai);
        const graphNodes: any = {
          /*/
          '0-activity-1': {
            cost: 0,
            elementType: 'activity',
            id: '0-activity-1',
            label: 'Teller Service',
            processingTime: 'CONSTANT',
            processingTimeParameter: 'constant(5,35)',
            queueRef: '0-queue-2',
            reportScrap: false,
            reportStatistics: false,
            resourceRef: '0-resource-3',
            resourceSelectionMethod: 'RANDOM',
            setupTime: 'CONSTANT',
            setupTimeParameter: '0',
            type: 'STANDARD',
            unit: 'MINUTES',
          }
          //*/
        };
        let nc = 0;
        for (const na in nodes) {
          if (nodes.hasOwnProperty(na)) {
            const nin = 'node' + nodes[na];
            if (nc++ > 300) {
              break;
            }
            graphNodes[nin] = {
              cost: 0,
              elementType: 'activity',
              id: nin,
              label: na,
              processingTime: 'CONSTANT',
              processingTimeParameter: 'constant(5,35)',
              queueRef: null,
              reportScrap: false,
              reportStatistics: false,
              resourceRef: null,
              resourceSelectionMethod: 'RANDOM',
              setupTime: 'CONSTANT',
              setupTimeParameter: '0',
              type: 'STANDARD',
              unit: 'MINUTES',
            };
          }
        }
        const graphConnectors: any = {};
        let ac = 0;
        for (const aa in arcs) {
          if (arcs.hasOwnProperty(aa)) {
            break;
            const arc = arcs[aa];
            if (graphNodes.hasOwnProperty('node' + arc[0]) && graphNodes.hasOwnProperty('node' + arc[1])) {
              ac++;
              const ain = 'arc' + aa;
              graphConnectors[ain] = {
                elementType: 'connector',
                id: ain,
                sourceIndex: 0,
                sourceRef: 'node' + arc[0],
                targetIndex: 0,
                targetRef: 'node' + arc[1],
              };
            }
          }
        }
        console.log(nc + ' nodes', ac + ' arcs', (nc + ac) + ' objects');
        const graphJson = {
          configurations: {},
          control: {},
          elementType: 'net',
          id: 'model-0',
          pages: {
            0: {
              elementType: 'page',
              id: '0',
              data: {},
              nodes: graphNodes,
              connectors: graphConnectors,
            },
          },
          version: '1.0',
        };
        const g = GraphImpl.deserialize(JSON.parse(JSON.stringify(graphJson))) as Graph;
        callback(g);
      });
      wsc.send('/req/mining/process/df/log_197073551257900/c0/c85/c1');
    });
  }

  public async getExampleModel(): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/example');
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getCreateDefault(): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/create');
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getCreate(defaultNodes: string): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/create/' + defaultNodes);
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getEdit(modelId: string): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/edit/' + modelId);
    return GraphImpl.deserialize(response.data) as Graph;
  }

  public async getView(modelId: string): Promise<Graph> {
    const response = await this.remoteGet(SbpnetModelService.BASE_URI + '/view/' + modelId);
    return GraphImpl.deserialize(response.data) as Graph;
  }

}
