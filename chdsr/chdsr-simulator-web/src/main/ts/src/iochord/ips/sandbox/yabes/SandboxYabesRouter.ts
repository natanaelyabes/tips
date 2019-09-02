// Vue
import { RouteConfig, Route } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

import SandboxListVuexRouterRoutes from './SandboxListVuexRouter/routes/SandboxListVuexRouterRoutes';
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: '',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-view`,
    component: () => import(/* webpackChunkName: "ips-sandbox-view" */ '@/iochord/ips/sandbox/yabes/SandboxTest/views/SandboxTestView.vue'),
  },
  {
    path: 'parent-child-view',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-parent-child-view`,
    component: () => import(/* webpackChunkName: "ips-parent-child-sandbox-view" */ '@/iochord/ips/sandbox/yabes/SandboxParentChild/views/SandboxParentChildView.vue'),
  },
  {
    path: 'sandbox-editor-test',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-editor-test`,
    component: () => import(/* webpackChunkName: "ips-sandbox-editor-test-view" */ '@/iochord/ips/sandbox/yabes/SandboxEditorTest/views/SandboxEditorTest.vue'),
  },
  {
    path: 'hello-world',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-hello-world`,
    component: () => import(/* webpackChunkName: "ips-sandbox-hello-world" */ '@/iochord/ips/sandbox/yabes/SandboxHelloWorld/views/SandboxHelloWorld.vue'),
  },
  {
    path: 'list-vuex-router',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-list-vuex-router`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/sandbox/list-vuex-router/list/1`,
    component: () => import(/* webpackChunkName: "ips-sandbox-list-vuex-router" */ '@/iochord/ips/sandbox/yabes/SandboxListVuexRouter/views/SandboxListVuexRouterIndex.vue'),
    children: [
      ...SandboxListVuexRouterRoutes as RouteConfig[],
    ],
  },
  {
    path: 'graph-store',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-graph-store`,
    component: () => import(/* webpackChunkName: "ips-sandbox-graph-store" */ '@/iochord/ips/sandbox/yabes/SandboxGraphStore/views/SandboxGraphStoreView.vue'),
  },
  {
    path: 'joint-js-tutorial',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-joint-js-tutorial`,
    component: () => import(/* webpackChunkName: "ips-sandbox-joint-js-tutorial" */ '@/iochord/ips/sandbox/yabes/SandboxJointJs/views/SandboxJointJsView.vue'),
  },
  {
    path: 'vue-rx',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-vue-rx`,
    component: () => import(/* webpackChunkName: "ips-sandbox-vue-rx" */ '@/iochord/ips/sandbox/yabes/SandboxRxJs/views/SandboxRxJsView.vue'),
  },
  {
    path: 'api-test',
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-api-test`,
    component: () => import(/* webpackChunkName: "ips-sandbox-api-test" */ '@/iochord/ips/sandbox/yabes/SandboxApiTest/views/SandboxApiTestView.vue'),
  },
] as RouteConfig[];
