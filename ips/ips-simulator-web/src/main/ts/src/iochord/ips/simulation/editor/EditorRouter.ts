import { RouteConfig } from 'vue-router';
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/ips/common/enums/index';

/**
 * Router registry for the editor.
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default [
  {
    path: `editor`,
    component: () => import(/* webpackChunkName: "ips-common-layout-base" */ '@/iochord/ips/common/ui/components/layout/BaseLayout.vue'),
    children: [
      {
        path: ``,
        component: () => import(/* webpackChunkName: "ips-simulator-editor" */ './views/SimulationEditorView.vue'),
      },
    ],
  },
] as RouteConfig[];
