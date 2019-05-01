import { Route } from 'vue-router';
import Vue from 'vue';

export class SimulationEditorReportGuard {
  public static outputToConsole(to: Route, from: Route, next: (to?: string | false | void | Location | ((vm: Vue) => any) | undefined) => void) {
    console.log(to, from);
    next();
  }
}