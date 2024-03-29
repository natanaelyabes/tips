import { RouteConfig } from 'vue-router';

/**
 * The routing properties for data mapping module.
 *
 * @package ts
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default [
  {
    path: `mapping`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-model-data-filter" */ './views/DataMapping.vue'),
      },
      {
        path: `:datasetId`,
        component: () => import(/* webpackChunkName: "ips-model-data-filter" */ './views/DataMapping.vue'),
        props: true,
      },
    ],
  },
] as RouteConfig[];
