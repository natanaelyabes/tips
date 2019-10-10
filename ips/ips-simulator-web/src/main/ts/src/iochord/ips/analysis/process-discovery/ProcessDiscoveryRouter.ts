import { RouteConfig } from 'vue-router';

/**
 *
 * @package ips
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `process`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/layouts/BaseLayout.vue'),
    children: [
      {
        path: `discovery`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-process-discovery" */ './views/AnalysisPMD.vue'),
      },
    ],
  },
] as RouteConfig[];
