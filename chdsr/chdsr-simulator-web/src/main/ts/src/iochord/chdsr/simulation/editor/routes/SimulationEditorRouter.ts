// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Router


// Route config
export default [
  {
    path: `simulation-editor`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/simulation-editor`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/simulation/editor/views/SimulationEditorIndexView.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-simulation-editor`,
        component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/simulation/editor/views/SimulationEditorView.vue'),
      },
    ],
  },
] as RouteConfig[];
