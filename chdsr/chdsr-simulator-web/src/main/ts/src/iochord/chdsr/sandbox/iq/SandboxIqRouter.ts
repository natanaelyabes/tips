// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';


/**
 *
 * @package chdsr
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
export default [
  {
    path: 'sandbox-common-service',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-common-service`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-common-service" */ '@/iochord/chdsr/sandbox/iq/SandboxCommonService/views/SandboxCommonService.vue'),
  },
] as RouteConfig[];
