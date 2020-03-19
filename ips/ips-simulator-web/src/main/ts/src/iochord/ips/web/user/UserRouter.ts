import { RouteConfig } from 'vue-router';

/**
 * Router registry for user authentication.
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
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
