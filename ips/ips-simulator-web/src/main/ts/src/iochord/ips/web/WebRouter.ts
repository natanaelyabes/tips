import { RouteConfig, Route } from 'vue-router';

import HomeRouter from './home/HomeRouter';
import UserRouter from './user/UserRouter';

/**
 * Router registry for web page.
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default [
  ...HomeRouter as RouteConfig[],
  ...UserRouter as RouteConfig[],
] as RouteConfig[];
