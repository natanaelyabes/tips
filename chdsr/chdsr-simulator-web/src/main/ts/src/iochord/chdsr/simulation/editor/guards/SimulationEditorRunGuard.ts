import { Route } from 'vue-router';
import Vue from 'vue';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class SimulationEditorRunGuard {
  public static outputToConsole(to: Route, from: Route, next: (to?: string | false | void | Location | ((vm: Vue) => any) | undefined) => void) {
    console.log(to, from);
    next();
  }
}
