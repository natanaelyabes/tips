import { RouteConfig } from 'vue-router';

/**
 * Router registry for user authentication.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default [
  {
    path: `user`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: `login`,
        name: 'iochord-ips-login',
        component: () => import(/* webpackChunkName: "ips-simulator-web-user-login" */ './views/AuthenticationLoginView.vue'),
      },
    ],
  },
] as RouteConfig[] | undefined;
