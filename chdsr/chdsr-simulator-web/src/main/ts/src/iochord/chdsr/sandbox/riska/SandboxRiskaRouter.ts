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
    path: 'sandbox-data-connection',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-connection`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxDataConnection/views/SandboxDataConnection.vue'),
  },
  {
    path: 'sandbox-data-management',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-management`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxDataManagement/views/SandboxDataManagement.vue'),
  },
] as RouteConfig[];
