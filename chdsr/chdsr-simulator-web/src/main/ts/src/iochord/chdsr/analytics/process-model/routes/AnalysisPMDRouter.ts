// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum} from '@/iochord/chdsr/common/enums/index';

/**
 *
 * @package chdsr
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `analysis-process-model`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/analysis-process-model`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/analytics/process-model/views/AnalysisPMD.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-analysis-process-model`,
        component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/analytics/process-model/views/AnalysisPMD.vue'),
      },
     ],
  },
] as RouteConfig[];
