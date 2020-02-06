import { RouteConfig, Route } from 'vue-router';

import DataConnectionRouter from './connection/DataConnectionRouter';
import DataFilterRouter from './filter/DataFilterRouter';
import DataMappingRouter from './mapping/DataMappingRouter';
import DataHistoryRouter from './history/DataHistoryRouter';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `data`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/IpsLayout.vue'),
    children: [
      ...DataConnectionRouter as RouteConfig[],
      ...DataFilterRouter as RouteConfig[],
      ...DataMappingRouter as RouteConfig[],
      ...DataHistoryRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
