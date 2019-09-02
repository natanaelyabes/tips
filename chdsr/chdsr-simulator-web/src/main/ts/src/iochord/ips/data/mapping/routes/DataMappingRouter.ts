// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum} from '@/iochord/ips/common/enums/index';

/**
 *
 * @package ips
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `data-mapping`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/data-mapping`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/data/mapping/views/DataMapping.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-data-mapping`,
        component: () => import(/* webpackChunkName: "ips-data-connection-list" */ '@/iochord/ips/data/mapping/views/DataMapping.vue'),
      },
     ],
  },
] as RouteConfig[];

