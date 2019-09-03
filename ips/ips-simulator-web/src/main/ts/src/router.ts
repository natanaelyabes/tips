// Vue
import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Router
import IpsRouter from '@/iochord/ips/IpsRouter';
import AuthenticationRouter from '@/iochord/ips/user/authentication/routes/AuthenticationRouter';

Vue.use(Router);

export default new Router({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/auth/login`,
    },
    {
      path: `/${BaseUrlEnum.IOCHORD}/`,
      redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    },
    ...AuthenticationRouter as RouteConfig[],
    ...IpsRouter as RouteConfig[],
  ],
});
