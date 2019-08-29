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
    path: `analysis-resource-mining`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/analysis-resource-mining`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/analytics/resource-mining/views/AnalysisResourceMiningSettings.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-analysis-resource-mining`,
        component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/analytics/resource-mining/views/AnalysisResourceMiningSettings.vue'),
      },
     ],
  },
  {
    path: `analysis-resource-mining-overall`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/analysis-resource-mining-overall`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/analytics/resource-mining/views/AnalysisResourceMiningOverall.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-analysis-resource-mining-overall`,
        component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/analytics/resource-mining/views/AnalysisResourceMiningOverall.vue'),
      },
     ],
  },
] as RouteConfig[];
