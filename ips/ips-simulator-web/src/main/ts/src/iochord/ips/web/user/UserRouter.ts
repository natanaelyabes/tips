import { RouteConfig } from 'vue-router';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `user`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/layouts/BaseLayout.vue'),
    children: [
      {
        path: `login`,
        component: () => import(/* webpackChunkName: "ips-simulator-web-user-login" */ './views/AuthenticationLoginView.vue'),
      },
    ],
  },
/*
  {
    path: `auth`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth/login`,
    component: () => import(/* webpackChunkName: "ips-login-view"  './views/AuthenticationIndexView.vue'),
    children: [
      ...AuthenticationLoginRouter as RouteConfig[],
    ],
  },
*/
] as RouteConfig[] | undefined;
