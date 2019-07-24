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
    path: `sandbox`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/sandbox`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/sandbox/views/SandboxIndexView.vue'),
    children: [
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
        path: 'sandbox-data-connection',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-data-connection`,
        component: () => import(/* webpackChunkName: "chdsr-sandbox-data-connection-view" */ '@/iochord/chdsr/sandbox/views/SandboxDataConnection.vue'),
      },
    ],
  },
] as RouteConfig[];
