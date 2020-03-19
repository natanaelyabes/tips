import { RouteConfig, Route } from 'vue-router';

import HomeRouter from './home/HomeRouter';
import UserRouter from './user/UserRouter';

/**
 * Router registry for the web page.
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default [
  ...HomeRouter as RouteConfig[],
  ...UserRouter as RouteConfig[],
] as RouteConfig[];
