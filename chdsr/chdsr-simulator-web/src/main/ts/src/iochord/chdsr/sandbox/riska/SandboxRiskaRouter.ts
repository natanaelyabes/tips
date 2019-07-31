// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: 'sandbox-data-connection',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-connection`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxDataConnection/views/SandboxDataConnection.vue'),
  },
  {
    path: 'sandbox-data-management',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-management`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxDataManagement/views/SandboxDataManagement.vue'),
  },
  {
    path: 'sandbox-analysis-pmd',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-pmd`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxAnalysisPMD/views/SandboxAnalysisPMD.vue'),
  },
  {
    path: 'sandbox-analysis-branch',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-branch`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxAnalysisBranch/views/SandboxAnalysisBranch.vue'),
  },
  {
    path: 'sandbox-analysis-resources',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-resources`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxAnalysisResources/views/SandboxAnalysisResources.vue'),
  },
  {
    path: 'sandbox-analysis-dist-fitting',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-dist-fitting`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/riska/SandboxAnalysisDistFitting/views/SandboxAnalysisDistFitting.vue'),
  },
] as RouteConfig[];
