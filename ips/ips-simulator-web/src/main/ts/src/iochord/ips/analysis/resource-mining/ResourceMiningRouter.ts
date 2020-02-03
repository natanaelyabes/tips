import { RouteConfig } from 'vue-router';

/**
 * Router registry for Resource Mining module.
 *
 * @package ips
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `resource`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/layouts/BaseLayout.vue'),
    children: [
      {
        path: `mining`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-resource-mining" */ './views/AnalysisResourceMiningOverall.vue'),
      },
      {
        path: `settings`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-resource-settings" */ './views/AnalysisResourceMiningSettings.vue'),
      },
    ],
  },
] as RouteConfig[];
