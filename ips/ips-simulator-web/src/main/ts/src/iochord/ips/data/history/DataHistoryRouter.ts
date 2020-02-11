import { RouteConfig } from 'vue-router';

/**
 * Router properties for data history.
 *
 * @package ips
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default [
  {
    path: `history`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-model-data-filter" */ './views/DataHistory.vue'),
      },
    ],
  },
] as RouteConfig[];
