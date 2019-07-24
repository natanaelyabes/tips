// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';
import SandboxRiskaRouter from './riska/SandboxRiskaRouter';
import SandboxYabesRouter from './yabes/SandboxYabesRouter';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `sandbox`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/sandbox`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/sandbox/SandboxIndexView.vue'),
    children: [
      ...SandboxRiskaRouter as RouteConfig[],
      ...SandboxYabesRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
