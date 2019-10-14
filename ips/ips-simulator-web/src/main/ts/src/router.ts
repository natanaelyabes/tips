import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

import IpsRouter from '@/iochord/ips/IpsRouter';

Vue.use(Router);

export default new Router({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '',
      redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    },
    ...IpsRouter as RouteConfig[],
  ],
});
