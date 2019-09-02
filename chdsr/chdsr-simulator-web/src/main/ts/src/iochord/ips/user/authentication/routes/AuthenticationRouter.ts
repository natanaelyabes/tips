// Vue
import VueRouter, { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Router
import AuthenticationLoginRouter from './AuthenticationLoginRouter';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth/login`,
    component: () => import(/* webpackChunkName: "ips-login-view" */ '@/iochord/ips/user/authentication/views/AuthenticationIndexView.vue'),
    children: [
      ...AuthenticationLoginRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];

