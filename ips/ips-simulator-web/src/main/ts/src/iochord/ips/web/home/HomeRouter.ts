import { RouteConfig } from 'vue-router';

/**
 * Router registry for the home page.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default [
  {
    path: `home`,
    component: () => import(/* webpackChunkName: "ips-common-layout-ips" */ '@/iochord/ips/common/ui/components/layout/IpsLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-simulator-web-home" */ './views/HomeView.vue'),
      },
    ],
  },
] as RouteConfig[] | undefined;
