import { RouteConfig } from 'vue-router';

/**
 * Router registry for Process Discovery pages.
 *
 * @package ts
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `process`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: `discovery`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-process-discovery" */ './views/AnalysisPMD.vue'),
      },
      {
        path: `discovery/:datasetId`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-process-discovery" */ './views/AnalysisPMD.vue'),
        props: true,
      },
      {
        path: `discovery/:datasetId/:replayId`,
        component: () => import(/* webpackChunkName: "ips-model-analysis-analytics-process-discovery" */ './views/AnalysisPMD.vue'),
        props: true,
      },
    ],
  },
] as RouteConfig[];
