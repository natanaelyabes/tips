import { RouteConfig } from 'vue-router';

/**
 * Router registry for Branch Mining module
 *
 * @package ips
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `branch`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/layouts/BaseLayout.vue'),
    children: [
      {
        path: `mining`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-branch-mining" */ './views/AnalysisBranchOverall.vue'),
      },
      {
        path: `settings`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-branch-settings" */ './views/AnalysisBranchSettings.vue'),
      },
    ],
  },
] as RouteConfig[];
