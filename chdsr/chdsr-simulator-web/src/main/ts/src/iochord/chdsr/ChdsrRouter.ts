// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Router
import ChdsrHomeRouter from '@/iochord/chdsr/home/routes/HomeRouter';
import SimulationEditorRouter from '@/iochord/chdsr/simulation/editor/routes/SimulationEditorRouter';
import SandboxTestRouter from '@/iochord/chdsr/sandbox/SandboxTestRouter';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/ChdsrIndexView.vue'),
    children: [
      ...ChdsrHomeRouter as RouteConfig[],
      ...SimulationEditorRouter as RouteConfig[],
      ...SandboxTestRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
