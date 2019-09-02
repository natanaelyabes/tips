// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';
import SandboxRiskaRouter from './riska/SandboxRiskaRouter';
import SandboxYabesRouter from './yabes/SandboxYabesRouter';
import SandboxIqRouter from './iq/SandboxIqRouter';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `sandbox`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/sandbox`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/sandbox/SandboxIndexView.vue'),
    children: [
      ...SandboxIqRouter as RouteConfig[],
      ...SandboxRiskaRouter as RouteConfig[],
      ...SandboxYabesRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
