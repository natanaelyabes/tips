// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Router


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `simulation-editor`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/simulation-editor`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/simulation/editor/views/SimulationEditorIndexView.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-simulation-editor`,
        component: () => import(/* webpackChunkName: "ips-simulation-e1ditor-view" */ '@/iochord/ips/simulation/editor/views/SimulationEditorView.vue'),
      },
      {
        path: 'kpi',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-kpi`,
        component: () => import(/* webpackChunkName: "ips-simulation-editor-kpi-view" */ '@/iochord/ips/simulation/editor/views/SimulationEditorKpiView.vue'),
      },
      {
        path: 'ui',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-ui`,
        component: () => import(/* webpackChunkName: "ips-simulation-editor-ui-view" */ '@/iochord/ips/simulation/editor/views/SimulationEditorUIView.vue'),
      },
    ],
  },
] as RouteConfig[];
