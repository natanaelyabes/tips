// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/enums/index';

// Router
import ChdsrHomeRouter from './ChdsrHomeRouter';
import SimulationEditorRouter from '@/iochord/chdsr/simulation/editor/routes/SimulationEditorRouter';

// Route config
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    component: () => import(/* webpackChunkName: "chdsr-view-[request]" */ '@/iochord/chdsr/views/ChdsrIndexView.vue'),
    children: [
      ...ChdsrHomeRouter as RouteConfig[],
      ...SimulationEditorRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
