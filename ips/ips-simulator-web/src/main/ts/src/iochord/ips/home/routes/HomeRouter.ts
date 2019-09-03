// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Guards
import { IpsHomeGuard } from '@/iochord/ips/home/guards/IpsHomeGuard';


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
    // beforeEnter: IpsHomeGuard.outputToConsole,
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-home`,
    component: () => import(/* webpackChunkName: "ips-home-view" */ '@/iochord/ips/home/views/HomeView.vue'),
  },
] as RouteConfig[] | undefined;
