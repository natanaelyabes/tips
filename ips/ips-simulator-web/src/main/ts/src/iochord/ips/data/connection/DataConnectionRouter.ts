import { RouteConfig } from 'vue-router';

/**
 * Router properties for Data Connection.
 *
 * @package ts
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default [
  {
    path: `connection`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-model-data-connection-new" */ './views/DataConnectionList.vue'),
      },
      {
        path: `new`,
        component: () => import(/* webpackChunkName: "ips-model-data-connection-new" */ './views/DataConnectionNew.vue'),
      },
      {
        path: `upload`,
        component: () => import(/* webpackChunkName: "ips-model-data-connection-upload" */ './views/DataConnectionUpload.vue'),
      },
    ],
  },
] as RouteConfig[];
