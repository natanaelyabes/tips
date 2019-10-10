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
    path: `home`,
    component: () => import(/* webpackChunkName: "ips-common-layout-ips" */ '@/iochord/ips/common/ui/layouts/IpsLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-simulator-web-home" */ './views/HomeView.vue'),
      },
    ],
  },
] as RouteConfig[] | undefined;
