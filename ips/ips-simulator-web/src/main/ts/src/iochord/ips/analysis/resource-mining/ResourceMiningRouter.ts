import { RouteConfig } from 'vue-router';

/**
 * Router registry for Resource Mining module.
 *
 * @package ips
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
export default [
  {
    path: `resource`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: `mining/:datasetId?`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-resource-mining" */ './views/AnalysisResourceMiningOverall.vue'),
      },
      {
        path: `settings/:datasetId?`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-resource-settings" */ './views/AnalysisResourceMiningSettings.vue'),
      },
    ],
  },
] as RouteConfig[];
