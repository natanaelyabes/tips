// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum} from '@/iochord/chdsr/common/enums/index';

/**
 *
 * @package chdsr
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `data-connection`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/data-connection`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/data/connection/views/DataConnectionList.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-data-connection`,
        component: () => import(/* webpackChunkName: "chdsr-data-connection-list" */ '@/iochord/chdsr/data/connection/views/DataConnectionList.vue'),
      },
    ],
  },
  {
    path: `data-connection-new`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/data-connection-new`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/data/connection/views/DataConnectionNew.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-data-connection-new`,
        component: () => import(/* webpackChunkName: "chdsr-data-connection-list" */ '@/iochord/chdsr/data/connection/views/DataConnectionNew.vue'),
      },
    ],
  },
  {
    path: `data-connection-upload`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/data-connection-upload`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/data/connection/views/DataConnectionUpload.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-data-connection-upload`,
        component: () => import(/* webpackChunkName: "chdsr-data-connection-list" */ '@/iochord/chdsr/data/connection/views/DataConnectionUpload.vue'),
      },
    ],
  },
] as RouteConfig[];
