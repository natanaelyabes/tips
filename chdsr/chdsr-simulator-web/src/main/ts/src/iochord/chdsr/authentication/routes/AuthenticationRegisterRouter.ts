// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Guards
import { AuthenticationRegisterGuard } from '@/iochord/chdsr/authentication/guards/AuthenticationRegisterGuard';

// Route config
export default [
  {
    path: 'register',
    beforeEnter: AuthenticationRegisterGuard.outputToConsole,
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-register`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/authentication/views/AuthenticationRegisterView.vue'),
  },
] as RouteConfig[];
