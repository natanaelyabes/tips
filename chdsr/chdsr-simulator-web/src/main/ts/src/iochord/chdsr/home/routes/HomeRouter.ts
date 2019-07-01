// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Guards
import { ChdsrHomeGuard } from '@/iochord/chdsr/home/guards/ChdsrHomeGuard';

// Route config
export default [
  {
    path: '',
    // beforeEnter: ChdsrHomeGuard.outputToConsole,
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-home`,
    component: () => import(/* webpackChunkName: "chdsr-home-view" */ '@/iochord/chdsr/home/views/HomeView.vue'),
  },
] as RouteConfig[] | undefined;
