// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';


/**
 *
 * @package ips
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
export default [
  {
    path: 'sandbox-common-service',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-common-service`,
    component: () => import(/* webpackChunkName: "ips-sandbox-common-service" */ '@/iochord/ips/sandbox/iq/SandboxCommonService/views/SandboxCommonService.vue'),
  },
] as RouteConfig[];
