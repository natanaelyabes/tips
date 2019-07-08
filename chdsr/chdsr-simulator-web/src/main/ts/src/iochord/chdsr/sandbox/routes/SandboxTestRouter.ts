// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Router


// Route config
export default [
  {
    path: `sandbox`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/sandbox`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/sandbox/views/SandboxIndexView.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-sandbox-view`,
        component: () => import(/* webpackChunkName: "chdsr-sandbox-view" */ '@/iochord/chdsr/sandbox/views/SandboxTestView.vue'),
      },
      {
        path: 'parent-child-view',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-parent-child-view-sandbox`,
        component: () => import(/* webpackChunkName: "chdsr-parent-child-sandbox-view" */ '@/iochord/chdsr/sandbox/views/SandboxParentChildView.vue'),
      },
    ],
  },
] as RouteConfig[];
