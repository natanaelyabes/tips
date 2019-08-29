// Vue
import { RouteConfig, Route } from 'vue-router';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Router
import ChdsrHomeRouter from '@/iochord/chdsr/home/routes/HomeRouter';
import SimulationEditorRouter from '@/iochord/chdsr/simulation/editor/routes/SimulationEditorRouter';
import SandboxTestRouter from '@/iochord/chdsr/sandbox/SandboxTestRouter';
import DataConnectionRouter from '@/iochord/chdsr/data/connection/routes/DataConnectionRouter';
import DataFilterRouter from '@/iochord/chdsr/data/filter/routes/DataFilterRouter';
import DataMappingRouter from '@/iochord/chdsr/data/mapping/routes/DataMappingRouter';
import DataHistoryRouter from '@/iochord/chdsr/data/history/routes/DataHistoryRouter';
import AnalysisPMDRouter from './analytics/process-model/routes/AnalysisPMDRouter';
import AnalysisDistributionFittingRouter from './analytics/distribution-fitting/routes/AnalysisDistributionFittingRouter';
import AnalysisBranchesMiningRouter from './analytics/branches-mining/routes/AnalysisBranchesMiningRouter';
import AnalysisResourceMiningRouter from './analytics/resource-mining/routes/AnalysisResourceMiningRouter';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  {
    path: `/${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME}`,
    component: () => import(/* webpackChunkName: "chdsr-view" */ '@/iochord/chdsr/ChdsrIndexView.vue'),
    children: [
      ...ChdsrHomeRouter as RouteConfig[],
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
