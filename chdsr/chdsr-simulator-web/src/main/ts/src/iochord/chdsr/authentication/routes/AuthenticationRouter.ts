// Vue
import VueRouter, { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Router
import AuthenticationLoginRouter from './AuthenticationLoginRouter';

// Route config
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth/login`,
    component: () => import(/* webpackChunkName: "chdsr-login-view" */ '@/iochord/chdsr/authentication/views/AuthenticationIndexView.vue'),
    children: [
      ...AuthenticationLoginRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];

