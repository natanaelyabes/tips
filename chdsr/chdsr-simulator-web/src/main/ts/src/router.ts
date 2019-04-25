// Vue
import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/enums/index';

// Router
import ChdsrRouter from '@/iochord/chdsr/routes/ChdsrRouter';
import AuthenticationRouter from '@/iochord/chdsr/authentication/routes/AuthenticationRouter';

Vue.use(Router);

export default new Router({
  mode: 'history',
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
    ...ChdsrRouter as RouteConfig[],
  ],
});
