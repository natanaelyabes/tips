// Vue
import VueRouter, { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Router
import AuthenticationLoginRouter from './AuthenticationLoginRouter';
import AuthenticationRegisterRouter from './AuthenticationRegisterRouter';

// Route config
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth/login`,
    component: () => import(/* webpackChunkName: "chdsr-view-[request]" */ '@/iochord/chdsr/authentication/views/AuthenticationIndexView.vue'),
    children: [
      ...AuthenticationLoginRouter as RouteConfig[],
      ...AuthenticationRegisterRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];

