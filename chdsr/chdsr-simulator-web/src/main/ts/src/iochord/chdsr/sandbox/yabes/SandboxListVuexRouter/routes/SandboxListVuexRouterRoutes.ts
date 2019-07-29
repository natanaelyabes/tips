// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: 'models/:model_id',
    component: () => import(/* webpackChunkName: "chdsr-sandbox-view" */ '@/iochord/chdsr/sandbox/yabes/SandboxListVuexRouter/views/SandboxListVuexRouterIndex.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-list-vuex-router-list`,
        component: () => import(/* webpackChunkName: "chdsr-sandbox-view" */ '@/iochord/chdsr/sandbox/yabes/SandboxListVuexRouter/views/SandboxListVuexRouterMaster.vue'),
      },
      {
        path: ':detail_id',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-list-vuex-router-detail`,
        component: () => import(/* webpackChunkName: "chdsr-sandbox-view" */ '@/iochord/chdsr/sandbox/yabes/SandboxListVuexRouter/views/SandboxListVuexRouterDetail.vue'),
      },
    ],
  },
] as RouteConfig[];
