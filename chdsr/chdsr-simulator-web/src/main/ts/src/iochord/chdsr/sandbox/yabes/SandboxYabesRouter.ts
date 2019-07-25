// Vue
import { RouteConfig, Route } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

import SandboxListVuexRouterRoutes from './SandboxListVuexRouter/routes/SandboxListVuexRouterRoutes';
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: '',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-view`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-view" */ '@/iochord/chdsr/sandbox/yabes/SandboxTest/views/SandboxTestView.vue'),
  },
  {
    path: 'parent-child-view',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-parent-child-view`,
    component: () => import(/* webpackChunkName: "chdsr-parent-child-sandbox-view" */ '@/iochord/chdsr/sandbox/yabes/SandboxParentChild/views/SandboxParentChildView.vue'),
  },
  {
    path: 'sandbox-editor-test',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-editor-test`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-editor-test-view" */ '@/iochord/chdsr/sandbox/yabes/SandboxEditorTest/views/SandboxEditorTest.vue'),
  },
  {
    path: 'hello-world',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-hello-world`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-hello-world" */ '@/iochord/chdsr/sandbox/yabes/SandboxHelloWorld/views/SandboxHelloWorld.vue'),
  },
  {
    path: 'list-vuex-router',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-list-vuex-router`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/sandbox/list-vuex-router/list/1`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-list-vuex-router" */ '@/iochord/chdsr/sandbox/yabes/SandboxListVuexRouter/views/SandboxListVuexRouterIndex.vue'),
    children: [
      ...SandboxListVuexRouterRoutes as RouteConfig[],
    ],
  },
  {
    path: 'graph-store',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-graph-store`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-graph-store" */ '@/iochord/chdsr/sandbox/yabes/SandboxGraphStore/views/SandboxGraphStoreView.vue'),
  },
  {
    path: 'joint-js-tutorial',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-joint-js-tutorial`,
    component: () => import(/* webpackChunkName: "chdsr-sandbox-joint-js-tutorial" */ '@/iochord/chdsr/sandbox/yabes/SandboxJointJs/views/SandboxJointJsView.vue'),
  },
] as RouteConfig[];
