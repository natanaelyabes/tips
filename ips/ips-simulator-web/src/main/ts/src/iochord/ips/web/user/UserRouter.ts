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
        name: 'iochord-ips-login',
        component: () => import(/* webpackChunkName: "ips-simulator-web-user-login" */ './views/AuthenticationLoginView.vue'),
      },
    ],
  },
] as RouteConfig[] | undefined;
