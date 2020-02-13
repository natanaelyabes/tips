import { RouteConfig, Route } from 'vue-router';

import DataConnectionRouter from './connection/DataConnectionRouter';
import DataFilterRouter from './filter/DataFilterRouter';
import DataMappingRouter from './mapping/DataMappingRouter';
import DataHistoryRouter from './history/DataHistoryRouter';


/**
 * The router properties for data connection module.
 *
 * @package ips
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default [
  {
    path: `data`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/IpsLayout.vue'),
    children: [
      ...DataConnectionRouter,
      ...DataFilterRouter,
      ...DataMappingRouter,
      ...DataHistoryRouter,
    ],
  },
] as RouteConfig[];
