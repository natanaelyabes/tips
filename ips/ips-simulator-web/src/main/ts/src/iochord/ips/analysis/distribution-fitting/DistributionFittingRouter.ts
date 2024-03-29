import { RouteConfig } from 'vue-router';

/**
 * Router registry for Distribution Fitting module.
 *
 * @package ts
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `distribution`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: `fitting`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-dist-fitting" */ './views/AnalysisDistFittingOverall.vue'),
      },
      {
        path: `settings`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-dist-settings" */ './views/AnalysisDistFittingSettings.vue'),
      },
    ],
  },
] as RouteConfig[];
