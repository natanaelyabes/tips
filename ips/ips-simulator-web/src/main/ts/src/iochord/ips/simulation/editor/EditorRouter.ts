import { RouteConfig } from 'vue-router';
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `editor`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-simulator-editor" */ './views/SimulationEditorView.vue'),
      },
      {
        path: `kpi`,
        component: () => import(/* webpackChunkName: "ips-simulator-editor-kpi" */ './views/SimulationEditorKpiView.vue'),
      },
      {
        path: `ui`,
        component: () => import(/* webpackChunkName: "ips-simulator-editor-ui" */ './views/SimulationEditorUIView.vue'),
      },
    ],
  },
] as RouteConfig[];
