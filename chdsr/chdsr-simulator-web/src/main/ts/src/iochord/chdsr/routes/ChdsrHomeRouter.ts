// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/enums/index';

// Guards
import { ChdsrHomeGuard } from '@/iochord/chdsr/guards/ChdsrHomeGuard';

// Route config
export default [
  {
    path: '',
    beforeEnter: ChdsrHomeGuard.outputToConsole,
    name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-home`,
    component: () => import(/* webpackChunkName: "chdsr-view-[request]" */ '@/iochord/chdsr/views/ChdsrHomeView.vue'),
  },
] as RouteConfig[] | undefined;
