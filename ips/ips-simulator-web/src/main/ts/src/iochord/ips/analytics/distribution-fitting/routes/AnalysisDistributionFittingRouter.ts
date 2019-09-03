// Vue
import { RouteConfig } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum} from '@/iochord/ips/common/enums/index';

/**
 *
 * @package ips
 * @author Riska A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 *
 */
export default [
  {
    path: `analysis-distribution-fitting`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/analysis-distribution-fitting`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/distribution-fitting/views/AnalysisDistFittingSettings.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-analysis-distribution-fitting`,
        component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/distribution-fitting/views/AnalysisDistFittingSettings.vue'),
      },
     ],
  },
  {
    path: `analysis-distribution-fitting-overall`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/analysis-distribution-fitting-overall`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/distribution-fitting/views/AnalysisDistFittingOverall.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-analysis-distribution-fitting-overall`,
        component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/distribution-fitting/views/AnalysisDistFittingOverall.vue'),
      },
     ],
  },
] as RouteConfig[];
