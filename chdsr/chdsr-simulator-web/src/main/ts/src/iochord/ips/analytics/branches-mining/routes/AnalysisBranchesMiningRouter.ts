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
    path: `analysis-branches-mining`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/analysis-branches-mining`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/branches-mining/views/AnalysisBranchSettings.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-analysis-branches-mining`,
        component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/branches-mining/views/AnalysisBranchSettings.vue'),
      },
     ],
  },
  {
    path: `analysis-branches-mining-overall`,
    redirect: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}/analysis-branches-mining-overall`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/branches-mining/views/AnalysisBranchOverall.vue'),
    children: [
      {
        path: '',
        name: `${BaseUrlEnum.IOCHORD}-${ApplicationEnum.NAME}-analysis-branches-mining-overall`,
        component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/analytics/branches-mining/views/AnalysisBranchOverall.vue'),
      },
     ],
  },
] as RouteConfig[];
