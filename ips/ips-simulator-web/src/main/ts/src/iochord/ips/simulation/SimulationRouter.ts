import { RouteConfig, Route } from 'vue-router';

import EditorRouter from './editor/EditorRouter';

/**
 * Router registry for the simulation router.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default [
  {
    path: `simulation`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/IpsLayout.vue'),
    children: [
      ...EditorRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
