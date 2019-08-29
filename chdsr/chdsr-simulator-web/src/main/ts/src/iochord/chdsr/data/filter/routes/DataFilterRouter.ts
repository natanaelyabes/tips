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
    path: `data-filter`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/data-filter`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/data/filter/views/DataFilter.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-data-filter`,
        component: () => import(/* webpackChunkName: "chdsr-data-connection-list" */ '@/iochord/chdsr/data/filter/views/DataFilter.vue'),
      },
     ],
  },
] as RouteConfig[];

