import { RouteConfig, Route } from 'vue-router';

import EditorRouter from './editor/EditorRouter';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `model`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/layouts/IpsLayout.vue'),
    children: [
      ...EditorRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
