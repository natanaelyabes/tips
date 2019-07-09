// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Guards
import { AuthenticationLoginGuard } from '@/iochord/chdsr/authentication/guards/AuthenticationLoginGuard';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: 'login',
    // beforeEnter: AuthenticationLoginGuard.outputToConsole,
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-login`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/authentication/views/AuthenticationLoginView.vue'),
  },
] as RouteConfig[] | undefined;
