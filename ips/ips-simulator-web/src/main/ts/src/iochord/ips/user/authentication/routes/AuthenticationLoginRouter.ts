// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Guards
import { AuthenticationLoginGuard } from '@/iochord/ips/user/authentication/guards/AuthenticationLoginGuard';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: 'login',
    // beforeEnter: AuthenticationLoginGuard.outputToConsole,
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-login`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/user/authentication/views/AuthenticationLoginView.vue'),
  },
] as RouteConfig[] | undefined;
