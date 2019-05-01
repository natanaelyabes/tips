// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Guards
import { AuthenticationLoginGuard } from '@/iochord/chdsr/authentication/guards/AuthenticationLoginGuard';

// Route config
export default [
  {
    path: 'login',
    beforeEnter: AuthenticationLoginGuard.outputToConsole,
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-login`,
    component: () => import(/* webpackChunkName: "chdsr-view-[request]" */ '@/iochord/chdsr/authentication/views/AuthenticationLoginView.vue'),
  },
] as RouteConfig[] | undefined;
