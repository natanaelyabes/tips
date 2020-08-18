import { RouteConfig, Route } from 'vue-router';

import HomeRouter from './home/HomeRouter';
import UserRouter from './user/UserRouter';

/**
 * Router registry for the web page.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default [
  ...HomeRouter as RouteConfig[],
  ...UserRouter as RouteConfig[],
] as RouteConfig[];
