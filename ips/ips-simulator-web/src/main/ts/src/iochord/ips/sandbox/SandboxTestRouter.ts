// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';
import SandboxYabesRouter from './yabes/SandboxYabesRouter';


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
      ...SandboxYabesRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
