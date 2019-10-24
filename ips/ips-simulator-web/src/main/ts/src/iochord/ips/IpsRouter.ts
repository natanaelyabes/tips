import { RouteConfig, Route } from 'vue-router';
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

import DataRouter from './data/DataRouter';
import ModelRouter from './model/ModelRouter';
import AnalysisRouter from './analysis/AnalysisRouter';
import WebRouter from './web/WebRouter';



/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/layouts/BaseLayout.vue'),
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/user/login`,
    children: [

      ...DataRouter as RouteConfig[],
      ...ModelRouter as RouteConfig[],
      ...AnalysisRouter as RouteConfig[],
      ...WebRouter as RouteConfig[],

    ],
  },
//  ...SandboxTestRouter as RouteConfig[],
] as RouteConfig[];
