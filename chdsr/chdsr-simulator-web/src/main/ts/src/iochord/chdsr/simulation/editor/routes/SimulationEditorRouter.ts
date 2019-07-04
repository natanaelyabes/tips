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
        component: () => import(/* webpackChunkName: "chdsr-simulation-e1ditor-view" */ '@/iochord/chdsr/simulation/editor/views/SimulationEditorView.vue'),
      },
      {
        path: 'kpi',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-kpi`,
        component: () => import(/* webpackChunkName: "chdsr-simulation-editor-kpi-view" */ '@/iochord/chdsr/simulation/editor/views/SimulationEditorKpiView.vue'),
      },
      {
        path: 'ui',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-ui`,
        component: () => import(/* webpackChunkName: "chdsr-simulation-editor-ui-view" */ '@/iochord/chdsr/simulation/editor/views/SimulationEditorUIView.vue'),
      },
    ],
  },
] as RouteConfig[];
