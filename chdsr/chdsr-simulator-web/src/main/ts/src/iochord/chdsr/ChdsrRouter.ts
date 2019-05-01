// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Router
import ChdsrHomeRouter from './home/routes/HomeRouter';
import SimulationEditorRouter from '@/iochord/chdsr/simulation/editor/routes/SimulationEditorRouter';

// Route config
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    component: () => import(/* webpackChunkName: "chdsr-view-[request]" */ '@/iochord/chdsr/ChdsrIndexView.vue'),
    children: [
      ...ChdsrHomeRouter as RouteConfig[],
      ...SimulationEditorRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
