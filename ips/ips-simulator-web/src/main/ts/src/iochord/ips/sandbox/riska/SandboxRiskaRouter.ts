// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: 'sandbox-data-connection-list',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-connection-list`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxDataConnection/views/SandboxDataConnectionList.vue'),
  },
  {
    path: 'sandbox-data-connection-new',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-connection-new`,
    component: () => import(/* webpackChunkName: "ips-sandbox-new-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxDataConnection/views/SandboxDataConnectionNew.vue'),
  },
  {
    path: 'sandbox-data-connection-upload',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-connection-upload`,
    component: () => import(/* webpackChunkName: "ips-sandbox-uploadfile-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxDataConnection/views/SandboxDataConnectionUpload.vue'),
  },
  {
    path: 'sandbox-data-management-filter',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-management-filter`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxDataManagement/views/SandboxDataManagementFilter.vue'),
  },
  {
    path: 'sandbox-data-management-history',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-management-history`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxDataManagement/views/SandboxDataManagementHistory.vue'),
  },
  {
    path: 'sandbox-data-management-mapping',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-management-mapping`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxDataManagement/views/SandboxDataManagementMapping.vue'),
  },
  {
    path: 'sandbox-analysis-pmd',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-pmd`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxAnalysisPMD/views/SandboxAnalysisPMD.vue'),
  },
  {
    path: 'sandbox-analysis-branch-overall',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-branch-overall`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxAnalysisBranch/views/SandboxAnalysisBranchOverall.vue'),
  },
  {
    path: 'sandbox-analysis-branch-Settings',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-branch-settings`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxAnalysisBranch/views/SandboxAnalysisBranchSettings.vue'),
  },
  {
    path: 'sandbox-analysis-resources-overall',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-resources-overall`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxAnalysisResources/views/SandboxAnalysisResourcesOverall.vue'),
  },
  {
    path: 'sandbox-analysis-resources-settings',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-resources-settings`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxAnalysisResources/views/SandboxAnalysisResourcesSettings.vue'),
  },
  {
    path: 'sandbox-analysis-dist-fitting-settings',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-dist-fitting-settings`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxAnalysisDistFitting/views/SandboxAnalysisDistFittingSettings.vue'),
  },
  {
    path: 'sandbox-analysis-dist-fitting-overall',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-analysis-dist-fitting-overall`,
    component: () => import(/* webpackChunkName: "ips-sandbox-data-connection-view" */ '@/iochord/ips/sandbox/riska/SandboxAnalysisDistFitting/views/SandboxAnalysisDistFittingOverall.vue'),
  },
] as RouteConfig[];
