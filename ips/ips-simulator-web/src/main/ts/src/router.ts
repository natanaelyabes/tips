import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

import IpsRouter from '@/iochord/ips/IpsRouter';

Vue.use(Router);

/**
 * Main entry for vue router application.
 * The router mode is set to hash mode which
 * uses the URL hash to simulate a full URL.
 * In return, the page will not be reloaded
 * when the URL changes.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
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
