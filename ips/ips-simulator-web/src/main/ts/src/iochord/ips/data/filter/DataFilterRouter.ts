import { RouteConfig } from 'vue-router';

/**
 * Router properties for the data filter module.
 *
 * @package ts
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default [
  {
    path: `filter`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-model-data-filter" */ './views/DataFilter.vue'),
      },
    ],
  },
] as RouteConfig[];
