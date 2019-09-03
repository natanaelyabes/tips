// Vue
import { RouteConfig, Route } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

// Router
import IpsHomeRouter from '@/iochord/ips/home/routes/HomeRouter';
import SimulationEditorRouter from '@/iochord/ips/simulation/editor/routes/SimulationEditorRouter';
import SandboxTestRouter from '@/iochord/ips/sandbox/SandboxTestRouter';
import DataConnectionRouter from '@/iochord/ips/data/connection/routes/DataConnectionRouter';
import DataFilterRouter from '@/iochord/ips/data/filter/routes/DataFilterRouter';
import DataMappingRouter from '@/iochord/ips/data/mapping/routes/DataMappingRouter';
import DataHistoryRouter from '@/iochord/ips/data/history/routes/DataHistoryRouter';
import AnalysisPMDRouter from './analytics/process-model/routes/AnalysisPMDRouter';
import AnalysisDistributionFittingRouter from './analytics/distribution-fitting/routes/AnalysisDistributionFittingRouter';
import AnalysisBranchesMiningRouter from './analytics/branches-mining/routes/AnalysisBranchesMiningRouter';
import AnalysisResourceMiningRouter from './analytics/resource-mining/routes/AnalysisResourceMiningRouter';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    component: () => import(/* webpackChunkName: "ips-view" */ '@/iochord/ips/IpsIndexView.vue'),
    children: [
      ...IpsHomeRouter as RouteConfig[],
      ...SimulationEditorRouter as RouteConfig[],
      ...SandboxTestRouter as RouteConfig[],
      ...DataConnectionRouter as RouteConfig[],
      ...DataFilterRouter as RouteConfig[],
      ...DataMappingRouter as RouteConfig[],
      ...DataHistoryRouter as RouteConfig[],
      ...AnalysisPMDRouter as RouteConfig[],
      ...AnalysisDistributionFittingRouter as RouteConfig[],
      ...AnalysisBranchesMiningRouter as RouteConfig[],
      ...AnalysisResourceMiningRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
