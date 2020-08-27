import { RouteConfig } from 'vue-router';

/**
 * Router registry for Branch Mining module
 *
 * @package ts
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `branch`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: `mining/:datasetId?`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-branch-mining" */ './views/AnalysisBranchOverall.vue'),
        props: true,
      },
      {
        path: `settings/:datasetId?`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-branch-settings" */ './views/AnalysisBranchSettings.vue'),
        props: true,
      },
    ],
  },
] as RouteConfig[];
