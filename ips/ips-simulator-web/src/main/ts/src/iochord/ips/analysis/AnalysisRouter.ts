import { RouteConfig, Route } from 'vue-router';
import BranchMiningRouter from './branch-mining/BranchMiningRouter';
import DistributionFittingRouter from './distribution-fitting/DistributionFittingRouter';
import ProcessDiscoveryRouter from './process-discovery/ProcessDiscoveryRouter';
import ResourceMiningRouter from './resource-mining/ResourceMiningRouter';

/**
 * Router registry for analytics package.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default [
  {
    path: `analytics`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/IpsLayout.vue'),
    children: [
      ...BranchMiningRouter as RouteConfig[],
      ...DistributionFittingRouter as RouteConfig[],
      ...ProcessDiscoveryRouter as RouteConfig[],
      ...ResourceMiningRouter as RouteConfig[],
    ],
  },
] as RouteConfig[];
